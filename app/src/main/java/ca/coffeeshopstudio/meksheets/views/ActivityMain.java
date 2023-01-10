/*
 * Copyright (c) 2018
 * Terry Doerksen
 * https://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package ca.coffeeshopstudio.meksheets.views;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ca.coffeeshopstudio.meksheets.utils.FileOperations;
import ca.coffeeshopstudio.meksheets.R;
import ca.coffeeshopstudio.meksheets.models.Mech;

public class ActivityMain extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String BUNDLE_CURRENT_FRAGMENT = "CURRENT_FRAG";
    private static final String TAG = "MekSheets";
    private static final String BUNDLE_CURRENT_MEK = "CURRENT_MEK";

    private List<Mech> meks = new ArrayList<>();
    private final List<String> spinnerValues = new ArrayList<>();

    private int currentMek = -1;
    private Fragment fragment;

    private Spinner spinner;
    private  BottomNavigationView navigation;

    private String tag;
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                save();
                if (item.getTitle().toString().equals(getString(R.string.nav_overview)) ||
                        item.getTitle().toString().equals(getString(R.string.nav_components))) {
                    initFragment(FragmentOverview.newInstance(), FragmentOverview.getFragmentTag());
                    return true;
                } else if (item.getTitle().toString().equals(getString(R.string.nav_armor))) {
                    initFragment(FragmentArmor.newInstance(), FragmentArmor.getFragmentTag());
                    return true;
                }
                return false;
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_sheet);

        findViewById(R.id.btnNext).setOnClickListener(this);
        findViewById(R.id.btnPrv).setOnClickListener(this);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        spinner = findViewById(R.id.spinner);

        getActiveMeks();

        if (savedInstanceState != null) {
            //config change
            String current = savedInstanceState.getString(BUNDLE_CURRENT_MEK);
            if (!current.equals("")) {
                for (int i = 0; i < meks.size(); i++) {
                    if (meks.get(i).getFileName().equals(current))
                        currentMek = i;
                }
            }

            String currentFragment = savedInstanceState.getString(BUNDLE_CURRENT_FRAGMENT);
            assert currentFragment != null;
            if (currentFragment.equals(FragmentOverview.class.getSimpleName())) {
                initFragment(FragmentOverview.newInstance(), FragmentOverview.getFragmentTag());
            } else if (currentFragment.equals(FragmentMain.class.getSimpleName())) {
                currentMek = -1;
                initFragment(FragmentMain.newInstance(), FragmentMain.getFragmentTag());
            } else if (currentFragment.equals(FragmentArmor.class.getSimpleName())) {
                initFragment(FragmentArmor.newInstance(), FragmentArmor.getFragmentTag());
            } else if (currentFragment.equals(FragmentComponents.class.getSimpleName())) {
                initFragment(FragmentComponents.newInstance(), FragmentComponents.getFragmentTag());
            }
        } else {
            if (currentMek == -1)
                initFragment(FragmentMain.newInstance(), FragmentMain.getFragmentTag());
            else
                initFragment(FragmentOverview.newInstance(), FragmentOverview.getFragmentTag());
        }
        updateTopBar();
        updateSpinner();
        //changeSelection();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().toString().equals(getString(R.string.menu_about))) {
            startActivity(new Intent(this, ActivityAbout.class));
            return true;
        } else if (item.getTitle().toString().equals(getString(R.string.menu_help))) {
            getHelpMsg();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getHelpMsg() {
        int helpMsg = R.string.help_main;
        if (tag.equals(FragmentArmor.getFragmentTag())) {
            helpMsg = R.string.help_armor;
        }
        if (tag.equals(FragmentComponents.getFragmentTag())) {
            helpMsg = R.string.help_components;
        }
        if (tag.equals(FragmentOverview.getFragmentTag())) {
            helpMsg = R.string.help_overview;
        }

        displayMessage(helpMsg);
    }

    public void displayMessage(int helpMsg) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.alert_dialog, null);

        TextView msg = view.findViewById(R.id.textMsg);
        msg.setText(Html.fromHtml(getString(helpMsg)));
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        //alertDialog.setTitle(R.string.help_title);

        alertDialog.setView(view);
        alertDialog.setPositiveButton(android.R.string.ok, null);
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    public void displayMessage(String helpMsg) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.alert_dialog, null);

        TextView msg = view.findViewById(R.id.textMsg);
        msg.setText(Html.fromHtml(helpMsg));
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setView(view);
        alertDialog.setPositiveButton(android.R.string.ok, null);
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    private void getActiveMeks() {
        meks = FileOperations.getJsonFiles(getApplicationContext());
    }

    public Mech getMek() {
        if (meks.size() == 0 || currentMek == -1)
            return null;
        return meks.get(currentMek);
    }

    private void updateSpinner() {
        spinner.setOnItemSelectedListener(null);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerValues);
        spinnerValues.clear();
        spinnerValues.add(getString(R.string.spinner_value_none));
        for (Mech mek : meks) {
            String value = mek.getName();
            if (value == null)
                value = "null";
            if (mek.getDescription() != null) {
                int len = Math.min(mek.getDescription().length(), 4);
                value += ": " + mek.getDescription().substring(0, len);
            }
            spinnerValues.add(value);
        }
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setSelection(currentMek + 1);
        spinner.setOnItemSelectedListener(this);
    }

    private void initFragment(Fragment fragment, String tag) {
        this.fragment = fragment;
        this.tag = tag;
        if (fragment instanceof FragmentMain )
            navigation.setVisibility(View.GONE);
        else
            navigation.setVisibility(View.VISIBLE);

        getSupportFragmentManager().beginTransaction().
                replace(R.id.main, fragment, tag).commit();
        getFragmentManager().executePendingTransactions();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        try {
            readTextFromUri(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readTextFromUri(Uri uri) throws IOException {
        addMek(uri);
    }

    private void addMek(Uri uri) throws FileNotFoundException {
        InputStream inputStream = getContentResolver().openInputStream(uri);

        assert inputStream != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        Mech mek = new Mech();
        try {
            mek.readMTF(reader);
            FileOperations.writeFile(getApplicationContext(), mek);

            addToList(mek);
            currentMek = meks.size() - 1;
            initFragment(FragmentOverview.newInstance(), FragmentOverview.getFragmentTag());
        } catch (IOException ioe) {
            Log.d(TAG, "readTextFromUri: " + ioe.getLocalizedMessage());
            displayMessage(R.string.invalid_file_format);
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnPrv) {
            changeSelection(-1);
        } else if (view.getId() == R.id.btnNext) {
            changeSelection(1);
        }
        updateTopBar();
    }

    public void save() {
        if (currentMek >= 0 && meks.size() > 0)
            FileOperations.writeFile(getApplicationContext(), meks.get(currentMek));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        save();
        if (currentMek == -1) {
            outState.putString(BUNDLE_CURRENT_MEK, "");
        } else {
            outState.putString(BUNDLE_CURRENT_MEK, meks.get(currentMek).getFileName());
        }
        outState.putString(BUNDLE_CURRENT_FRAGMENT, fragment.getClass().getSimpleName());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) {
            currentMek = -1;
            initFragment(FragmentMain.newInstance(), FragmentMain.getFragmentTag());
        } else {
            currentMek = i - 1;
            if (fragment instanceof FragmentMain) {
                initFragment(FragmentOverview.newInstance(), FragmentOverview.getFragmentTag());
            } else {
                fragment.onResume();
            }
        }
        updateTopBar();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void addToList(Mech mek) {
        meks.add(mek);
        currentMek++;

        updateSpinner();
    }

    private void changeSelection(int incrementBy) {
        currentMek = currentMek + incrementBy;

        if (currentMek <= -1) {
            currentMek = -1; //never go below -1
            initFragment(FragmentMain.newInstance(), FragmentMain.getFragmentTag());
        } else if (fragment instanceof FragmentMain) {
            initFragment(FragmentOverview.newInstance(), FragmentOverview.getFragmentTag());
        } else {
            save();
            fragment.onResume();
        }
        spinner.setOnItemSelectedListener(null);
        spinner.setSelection(currentMek + 1);
        spinner.setOnItemSelectedListener(this);
    }

    private void updateTopBar () {
        if (currentMek == -1)
            findViewById(R.id.btnPrv).setVisibility(View.INVISIBLE);
        else
            findViewById(R.id.btnPrv).setVisibility(View.VISIBLE);
        if (currentMek == meks.size() - 1)
            findViewById(R.id.btnNext).setVisibility(View.INVISIBLE);
        else
            findViewById(R.id.btnNext).setVisibility(View.VISIBLE);

    }

    public void delete() {
        FileOperations.deleteFile(getApplicationContext(), meks.get(currentMek).getFileName());
        getActiveMeks();
        if (currentMek >= meks.size())
            currentMek = meks.size()-1;
        updateTopBar();
        updateSpinner();
    }

    public int getMekCount() {
        return meks.size();
    }
}