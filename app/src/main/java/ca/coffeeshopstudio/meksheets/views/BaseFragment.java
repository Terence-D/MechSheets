/*
 * Copyright (c) 2018
 * Terry Doerksen
 * https://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package ca.coffeeshopstudio.meksheets.views;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import ca.coffeeshopstudio.meksheets.R;
import ca.coffeeshopstudio.meksheets.models.Mek;

public abstract class BaseFragment extends Fragment {
    public OnFragmentInteractionListener mListener;

    protected Mek mek;
    protected View root;


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        mek = ((ActivityMain) getActivity()).getMek();
        updateView();
    }

    protected void loadDialog(int helpMsg, String helpMainPref) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean sawWindow = prefs.getBoolean(helpMainPref, false);
        if (!sawWindow) {
            displayMessage(helpMsg);
            prefs.edit().putBoolean(helpMainPref, true).apply();
        }
    }

    private void displayMessage(int helpMsg) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.alert_dialog, null);

        TextView msg = view.findViewById(R.id.textmsg);
        msg.setText(helpMsg);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(R.string.help_title);

        alertDialog.setView(view);
        alertDialog.setPositiveButton(android.R.string.ok, null);
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    /**
     * This will take in a string and remove dashes and spaces and make it uppercase
     *
     * @param input string we are modifying
     * @return modified string
     */
    protected String sanitizeString(String input) {
        String sanitizedString = input;
        //strip out all dashes, spaces, and make everything uppercase
        sanitizedString = sanitizedString.replace("-", "");
        sanitizedString = sanitizedString.replace(" ", "");
        sanitizedString = sanitizedString.replace("(omnipod)", "");
        sanitizedString = sanitizedString.toUpperCase();
        return sanitizedString;
    }

    abstract void updateView();

}
