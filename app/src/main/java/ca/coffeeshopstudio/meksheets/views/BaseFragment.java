/*
 * Copyright (c) 2018
 * Terry Doerksen
 * https://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package ca.coffeeshopstudio.meksheets.views;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.view.View;

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

    abstract void updateView();

}
