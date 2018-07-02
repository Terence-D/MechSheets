/*
 * Copyright (c) 2018
 * Terry Doerksen
 * https://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package ca.coffeeshopstudio.meksheets.views;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import ca.coffeeshopstudio.meksheets.R;
import ca.coffeeshopstudio.meksheets.models.Locations;
import ca.coffeeshopstudio.meksheets.models.Mek;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentComponents.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentComponents#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentComponents extends BaseFragment implements View.OnClickListener {

    public FragmentComponents() {
        // Required empty public constructor
    }

    public static FragmentComponents newInstance() {
        FragmentComponents fragment = new FragmentComponents();
        return fragment;
    }

    public static String getFragmentTag() {
        return "COMPONENTS";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_components, container, false);

        LinearLayout ll = root.findViewById(R.id.components);

        buttonize(ll);
        loadDialog(R.string.help_components, "components");

        return root;
    }

    private void buttonize(LinearLayout ll) {
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if(child instanceof Button)
                child.setOnClickListener(this);
            if (child instanceof LinearLayout)
                buttonize((LinearLayout) child);
        }
    }

    @Override
    void updateView() {
        updateComponentList("LA", mek.getComponents(Locations.leftArm));
        updateComponentList("RA", mek.getComponents(Locations.rightArm));
        updateComponentList("H", mek.getComponents(Locations.head));
        updateComponentList("CT", mek.getComponents(Locations.centerTorso));
        updateComponentList("LT", mek.getComponents(Locations.leftTorso));
        updateComponentList("RT", mek.getComponents(Locations.rightTorso));
        updateComponentList("LL", mek.getComponents(Locations.leftLeg));
        updateComponentList("RL", mek.getComponents(Locations.rightLeg));
    }

    void updateComponentList(String name, Mek.Component[] list) {
        for (int i = 0; i < list.length; i++) {
            int viewNumber = i+1;
            String viewName = "btn" + name + viewNumber;
            int id = getResources().getIdentifier(viewName, "id", getActivity().getPackageName());
            String componentName = list[i].getName();
            componentName = componentName.replace("-", " ");
            ((Button)root.findViewById(id)).setText(viewNumber + ") " + componentName);
            if (list[i].isFunctioning()) {
                root.findViewById(id).setBackgroundColor(getActivity().getResources().getColor(R.color.statusGood));
                root.findViewById(id).setEnabled(true);
            } else {
                root.findViewById(id).setBackgroundColor(getActivity().getResources().getColor(R.color.statusBad));
                root.findViewById(id).setEnabled(true);
            }
            CheckForNonTargetableComponent(list[i], id);
        }
    }

    private void CheckForNonTargetableComponent(Mek.Component component, int id) {
        String sanitizedName = sanitizeString(component.getName());

        String sanitizedEmpty = sanitizeString(Mek.MTF_EMPTY);
        String sanitizedEndo = sanitizeString(Mek.MTF_ENDO);
        String sanitizedCase = sanitizeString(Mek.MTF_CASE.toString());
        String sanitizedFerro = sanitizeString(Mek.MTF_FERRO);

        if (sanitizedName.contains(sanitizedEmpty) ||
                sanitizedName.contains(sanitizedEndo) ||
                sanitizedName.contains(sanitizedCase) ||
                sanitizedName.contains(sanitizedFerro)) {
            root.findViewById(id).setBackgroundColor(getActivity().getResources().getColor(R.color.textSecondary));
            root.findViewById(id).setEnabled(false);
        }
    }

    @Override
    public void onClick(View view) {
        String viewName = getResources().getResourceEntryName(view.getId());

        viewName = viewName.substring(3); //get rid of "btn"
        int componentNumber = Mek.extractNumbers(viewName) - 1;
        if (viewName.startsWith("H")) {
            mek.toggleComponent(Locations.head, componentNumber);
        } else
        if (viewName.startsWith("CT")) {
            mek.toggleComponent(Locations.centerTorso, componentNumber);
        } else
        if (viewName.startsWith("LA")) {
            mek.toggleComponent(Locations.leftArm, componentNumber);
        } else
        if (viewName.startsWith("RA")) {
            mek.toggleComponent(Locations.rightArm, componentNumber);
        } else
        if (viewName.startsWith("LT")) {
            mek.toggleComponent(Locations.leftTorso, componentNumber);
        } else
        if (viewName.startsWith("RT")) {
            mek.toggleComponent(Locations.rightTorso, componentNumber);
        } else
        if (viewName.startsWith("LL")) {
            mek.toggleComponent(Locations.leftLeg, componentNumber);
        } else
        if (viewName.startsWith("RL")) {
            mek.toggleComponent(Locations.rightLeg, componentNumber);
        }
        updateView();
    }
}
