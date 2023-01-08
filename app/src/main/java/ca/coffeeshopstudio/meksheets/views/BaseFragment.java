/*
 * Copyright (c) 2018
 * Terry Doerksen
 * https://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package ca.coffeeshopstudio.meksheets.views;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.View;

import ca.coffeeshopstudio.meksheets.models.Mech;

public abstract class BaseFragment extends Fragment {
    public OnFragmentInteractionListener mListener;

    protected Mech mech;
    protected View root;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnFragmentInteractionListener");
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

        mech = ((ActivityMain) requireActivity()).getMek();
        updateView();
    }

    protected void loadDialog(int helpMsg, String helpMainPref) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean sawWindow = prefs.getBoolean(helpMainPref, false);
        if (!sawWindow) {
            ((ActivityMain) requireActivity()).displayMessage(helpMsg);
            prefs.edit().putBoolean(helpMainPref, true).apply();
        }
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
