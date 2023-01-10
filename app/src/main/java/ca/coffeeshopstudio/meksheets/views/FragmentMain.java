/*
 * Copyright (c) 2018
 * Terry Doerksen
 * https://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package ca.coffeeshopstudio.meksheets.views;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import ca.coffeeshopstudio.meksheets.utils.DownloadProgressCallback;
import ca.coffeeshopstudio.meksheets.utils.Downloader;
import ca.coffeeshopstudio.meksheets.utils.FileOperations;
import ca.coffeeshopstudio.meksheets.R;
import okio.Path;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMain extends BaseFragment implements View.OnClickListener, DownloadProgressCallback {
    private static final int READ_REQUEST_CODE = 42;
    private ProgressBar progressBar;
    private TextView raw;

    public FragmentMain() {
        // Required empty public constructor
    }

    public static FragmentMain newInstance() {
        return new FragmentMain();
    }

    public static String getFragmentTag() {
        return "MAIN";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main, container, false);
        root.findViewById(R.id.btnLoad).setOnClickListener(this);
        root.findViewById(R.id.btnBulkAdd).setOnClickListener(this);
        //progressBar = root.findViewById(R.id.progressBar);
        raw = root.findViewById(R.id.txtAddMultiple);

        int unitCount = ((ActivityMain) requireActivity()).getMekCount();
        ((TextView) root.findViewById(R.id.txtUnitCount)).setText(getString(R.string.main_units_loaded, unitCount));

        loadDialog(R.string.help_main, "main");

        return root;
    }

    @Override
    void updateView () {
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLoad) {
            performFileSearch();
        } else if (view.getId() == R.id.btnBulkAdd) {
            Downloader downloader = new Downloader(this);
            try {
                File cacheDir = new File(requireContext().getCacheDir(), getString(R.string.dest_megamek));
//                downloader.Start(getString(R.string.mega_mek_path), cacheDir);
                downloader.Start("https://127.0.0.1/test", cacheDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            Uri path = Uri.parse(getString(R.string.mega_mek_path));
//            OnlineToolkit.downloadFromUrl(path, getContext(), "megamek.zip");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            if (resultData != null) {
                Uri uri = resultData.getData();
                ((ActivityMain) requireActivity()).onFragmentInteraction(uri);
            }
        }
    }

    /**
     * Fires an intent to spin up the "file chooser" UI and select an image.
     */
    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        //intent.setType("*/*");
        //below will show only files that are not already part of the mime type list of the SA Framework
        //found by Geont from the bt forums
        intent.setType("application/octet-stream");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onProgressChanged(long rawProgressAmount, int percent) {
        progressBar.setProgress(percent);
    }

    @Override
    public void onFail() {
        requireActivity().runOnUiThread(() -> raw.setText("failed"));
        File downloadedFile = new File(requireActivity().getCacheDir() + Path.DIRECTORY_SEPARATOR + getString(R.string.dest_megamek));
        String toLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();

        boolean extractResult = FileOperations.unzip(downloadedFile, getString(R.string.mechs_file), new File(toLocation));
//        boolean extractResult = FileOperations.unzip(downloadedFile, getString(R.string.mechs_file), requireActivity().getFilesDir());
    }

    @Override
    public void onComplete(File downloadedFile) {
        requireActivity().runOnUiThread(() -> raw.setText("success"));
        boolean extractResult = FileOperations.unzip(downloadedFile, getString(R.string.mechs_file), requireActivity().getFilesDir());
    }
}
