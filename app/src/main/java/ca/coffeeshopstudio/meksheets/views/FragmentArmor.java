/*
 * Copyright (c) 2018
 * Terry Doerksen
 * https://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package ca.coffeeshopstudio.meksheets.views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import ca.coffeeshopstudio.meksheets.R;
import ca.coffeeshopstudio.meksheets.models.Locations;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentArmor.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentArmor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentArmor extends BaseFragment implements View.OnClickListener, View.OnLongClickListener {

    public FragmentArmor() {
        // Required empty public constructor
    }

    public static FragmentArmor newInstance() {
        FragmentArmor fragment = new FragmentArmor();
        return fragment;
    }

    public static String getFragmentTag() {
        return "ARMOR";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_armor, container, false);

        ConstraintLayout cl = root.findViewById(R.id.armor);

        for (int i = 0; i < cl.getChildCount(); i++) {
            View child = cl.getChildAt(i);
            if(child instanceof Button) {
                child.setOnClickListener(this);
                child.setLongClickable(true);
                child.setOnLongClickListener(this);
            }
        }

        loadDialog(R.string.help_armor, "armor");

        return root;
    }

    @Override
    void updateView() {
        String ah = getString(R.string.armor_h, mek.getArmorCurrent(Locations.head), mek.getArmorMax(Locations.head));
        String ala = getString(R.string.armor_la, mek.getArmorCurrent(Locations.leftArm), mek.getArmorMax(Locations.leftArm));
        String ara = getString(R.string.armor_ra, mek.getArmorCurrent(Locations.rightArm), mek.getArmorMax(Locations.rightArm));
        String act = getString(R.string.armor_ct, mek.getArmorCurrent(Locations.centerTorso), mek.getArmorMax(Locations.centerTorso));
        String alt = getString(R.string.armor_lt, mek.getArmorCurrent(Locations.leftTorso), mek.getArmorMax(Locations.leftTorso));
        String art = getString(R.string.armor_rt, mek.getArmorCurrent(Locations.rightTorso), mek.getArmorMax(Locations.rightTorso));
        String all = getString(R.string.armor_ll, mek.getArmorCurrent(Locations.leftLeg), mek.getArmorMax(Locations.leftLeg));
        String arl = getString(R.string.armor_rl, mek.getArmorCurrent(Locations.rightLeg), mek.getArmorMax(Locations.rightLeg));

        String arct = getString(R.string.armor_ct, mek.getArmorRearCurrent(Locations.centerTorso), mek.getArmorRearMax(Locations.centerTorso));
        String arlt = getString(R.string.armor_lt, mek.getArmorRearCurrent(Locations.leftTorso), mek.getArmorRearMax(Locations.leftTorso));
        String arrt = getString(R.string.armor_rt, mek.getArmorRearCurrent(Locations.rightTorso), mek.getArmorRearMax(Locations.rightTorso));

        String ih = getString(R.string.armor_h, mek.getInternalCurrent(Locations.head), mek.getInternalMax(Locations.head));
        String ila = getString(R.string.armor_la, mek.getInternalCurrent(Locations.leftArm), mek.getInternalMax(Locations.leftArm));
        String ira = getString(R.string.armor_ra, mek.getInternalCurrent(Locations.rightArm), mek.getInternalMax(Locations.rightArm));
        String ict = getString(R.string.armor_ct, mek.getInternalCurrent(Locations.centerTorso), mek.getInternalMax(Locations.centerTorso));
        String ilt = getString(R.string.armor_lt, mek.getInternalCurrent(Locations.leftTorso), mek.getInternalMax(Locations.leftTorso));
        String irt = getString(R.string.armor_rt, mek.getInternalCurrent(Locations.rightTorso), mek.getInternalMax(Locations.rightTorso));
        String ill = getString(R.string.armor_ll, mek.getInternalCurrent(Locations.leftLeg), mek.getInternalMax(Locations.leftLeg));
        String irl = getString(R.string.armor_rl, mek.getInternalCurrent(Locations.rightLeg), mek.getInternalMax(Locations.rightLeg));

        ((Button)root.findViewById(R.id.btnHead)).setText(ah);
        setButtonStyle(root.findViewById(R.id.btnHead), mek.getArmorCurrent(Locations.head), mek.getArmorMax(Locations.head));
        ((Button)root.findViewById(R.id.btnLeftArm)).setText(ala);
        setButtonStyle(root.findViewById(R.id.btnLeftArm), mek.getArmorCurrent(Locations.leftArm), mek.getArmorMax(Locations.leftArm));
        ((Button)root.findViewById(R.id.btnRightArm)).setText(ara);
        setButtonStyle(root.findViewById(R.id.btnRightArm), mek.getArmorCurrent(Locations.rightArm), mek.getArmorMax(Locations.rightArm));
        ((Button)root.findViewById(R.id.btnCenterTorso)).setText(act);
        setButtonStyle(root.findViewById(R.id.btnCenterTorso), mek.getArmorCurrent(Locations.centerTorso), mek.getArmorMax(Locations.centerTorso));
        ((Button)root.findViewById(R.id.btnLeftTorso)).setText(alt);
        setButtonStyle(root.findViewById(R.id.btnLeftTorso), mek.getArmorCurrent(Locations.leftTorso), mek.getArmorMax(Locations.leftTorso));
        ((Button)root.findViewById(R.id.btnRightTorso)).setText(art);
        setButtonStyle(root.findViewById(R.id.btnRightTorso), mek.getArmorCurrent(Locations.rightTorso), mek.getArmorMax(Locations.rightTorso));
        ((Button)root.findViewById(R.id.btnLeftLeg)).setText(all);
        setButtonStyle(root.findViewById(R.id.btnLeftLeg), mek.getArmorCurrent(Locations.leftLeg), mek.getArmorMax(Locations.leftLeg));
        ((Button)root.findViewById(R.id.btnRightLeg)).setText(arl);
        setButtonStyle(root.findViewById(R.id.btnRightLeg), mek.getArmorCurrent(Locations.rightLeg), mek.getArmorMax(Locations.rightLeg));

        ((Button)root.findViewById(R.id.btnRearCenter)).setText(arct);
        setButtonStyle(root.findViewById(R.id.btnRearCenter), mek.getArmorRearCurrent(Locations.centerTorso), mek.getArmorRearMax(Locations.centerTorso));
        ((Button)root.findViewById(R.id.btnRearLeft)).setText(arlt);
        setButtonStyle(root.findViewById(R.id.btnRearLeft), mek.getArmorRearCurrent(Locations.leftTorso), mek.getArmorRearMax(Locations.leftTorso));
        ((Button)root.findViewById(R.id.btnRearRight)).setText(arrt);
        setButtonStyle(root.findViewById(R.id.btnRearRight), mek.getArmorRearCurrent(Locations.rightTorso), mek.getArmorRearMax(Locations.rightTorso));

        ((Button)root.findViewById(R.id.btnInternalHead)).setText(ih);
        setButtonStyle(root.findViewById(R.id.btnInternalHead), mek.getInternalCurrent(Locations.head), mek.getInternalMax(Locations.head));
        ((Button)root.findViewById(R.id.btnInternalLeftArm)).setText(ila);
        setButtonStyle(root.findViewById(R.id.btnInternalLeftArm), mek.getInternalCurrent(Locations.leftArm), mek.getInternalMax(Locations.leftArm));
        ((Button)root.findViewById(R.id.btnInternalRightArm)).setText(ira);
        setButtonStyle(root.findViewById(R.id.btnInternalRightArm), mek.getInternalCurrent(Locations.rightArm), mek.getInternalMax(Locations.rightArm));
        ((Button)root.findViewById(R.id.btnInternalCenterTorso)).setText(ict);
        setButtonStyle(root.findViewById(R.id.btnInternalCenterTorso), mek.getInternalCurrent(Locations.centerTorso), mek.getInternalMax(Locations.centerTorso));
        ((Button)root.findViewById(R.id.btnInternalLeftTorso)).setText(ilt);
        setButtonStyle(root.findViewById(R.id.btnInternalLeftTorso), mek.getInternalCurrent(Locations.leftTorso), mek.getInternalMax(Locations.leftTorso));
        ((Button)root.findViewById(R.id.btnInternalRightTorso)).setText(irt);
        setButtonStyle(root.findViewById(R.id.btnInternalRightTorso), mek.getInternalCurrent(Locations.rightTorso), mek.getInternalMax(Locations.rightTorso));
        ((Button)root.findViewById(R.id.btnInternalLeftLeg)).setText(ill);
        setButtonStyle(root.findViewById(R.id.btnInternalLeftLeg), mek.getInternalCurrent(Locations.leftLeg), mek.getInternalMax(Locations.leftLeg));
        ((Button)root.findViewById(R.id.btnInternalRightLeg)).setText(irl);
        setButtonStyle(root.findViewById(R.id.btnInternalRightLeg), mek.getInternalCurrent(Locations.rightLeg), mek.getInternalMax(Locations.rightLeg));
    }

    private void setButtonStyle(View button, int armorCurrent, int armorMax) {
        if (armorCurrent == armorMax) {
            button.setBackgroundColor(getActivity().getResources().getColor(R.color.statusGood));
        } else if (armorCurrent > 0) {
            button.setBackgroundColor(getActivity().getResources().getColor(R.color.statusBad));
        } else {
            button.setBackgroundColor(getActivity().getResources().getColor(R.color.statusCritical));
        }
    }

    @Override
    public void onClick(View view) {
        adjustArmorPrepare(view, -1);
    }

    @Override
    public boolean onLongClick(View view) {
        adjustArmorPrepare(view, 1);
        return true;
    }

    private void adjustArmorPrepare(View view, int adjustBy) {
        Locations location;
        int locType;
        switch (view.getId()) {
            case R.id.btnHead:
                location = Locations.head;
                locType = 0;
                break;
            case R.id.btnCenterTorso:
                location = Locations.centerTorso;
                locType = 0;
                break;
            case R.id.btnLeftTorso:
                location = Locations.leftTorso;
                locType = 0;
                break;
            case R.id.btnRightTorso:
                location = Locations.rightTorso;
                locType = 0;
                break;
            case R.id.btnLeftArm:
                location = Locations.leftArm;
                locType = 0;
                break;
            case R.id.btnRightArm:
                location = Locations.rightArm;
                locType = 0;
                break;
            case R.id.btnLeftLeg:
                location = Locations.leftLeg;
                locType = 0;
                break;
            case R.id.btnRightLeg:
                location = Locations.rightLeg;
                locType = 0;
                break;
            case R.id.btnRearCenter:
                location = Locations.centerTorso;
                locType = 1;
                break;
            case R.id.btnRearLeft:
                location = Locations.leftTorso;
                locType = 1;
                break;
            case R.id.btnRearRight:
                location = Locations.rightTorso;
                locType = 1;
                break;
            case R.id.btnInternalHead:
                location = Locations.head;
                locType = -1;
                break;
            case R.id.btnInternalCenterTorso:
                location = Locations.centerTorso;
                locType = -1;
                break;
            case R.id.btnInternalLeftTorso:
                location = Locations.leftTorso;
                locType = -1;
                break;
            case R.id.btnInternalRightTorso:
                location = Locations.rightTorso;
                locType = -1;
                break;
            case R.id.btnInternalLeftArm:
                location = Locations.leftArm;
                locType = -1;
                break;
            case R.id.btnInternalRightArm:
                location = Locations.rightArm;
                locType = -1;
                break;
            case R.id.btnInternalLeftLeg:
                location = Locations.leftLeg;
                locType = -1;
                break;
            default: //case R.id.btnInternalRightLeg:
                location = Locations.rightLeg;
                locType = -1;
                break;
        }

        adjustArmor(location, locType, adjustBy);
    }

    private void adjustArmor(Locations location, int locType, int adjustBy) {
        switch (locType) {
            case 0:
                int rvA = validateStructureLevel(location, adjustBy, locType);
                mek.setArmorCurrent(location, mek.getArmorCurrent(location) + adjustBy);
                if (rvA == -1) { //location destroyed, display message and pass to IS
                    armorToastMessage(location, R.string.armor_destroyed);
                    adjustArmor(location, -1, adjustBy); //change to IS
                } else if (rvA == 1) { //rv must equal 1, so display an appropriate message only
                    armorToastMessage(location, R.string.location_maxed);
                }
                break;
            case 1:
                int rvRA = validateStructureLevel(location, adjustBy, locType);
                mek.setArmorRearCurrent(location, mek.getArmorRearCurrent(location) + adjustBy);
                if (rvRA == -1) { //location destroyed, display message and pass to IS
                    armorToastMessage(location, R.string.armor_rear_destroyed);
                    adjustArmor(location, -1, adjustBy); //change to IS
                } else if (rvRA == 1) { //rv must equal 1, so display an appropriate message only
                    armorToastMessage(location, R.string.location_maxed);
                }
                break;
            default: //case -1:
                int rvIS = validateStructureLevel(location, adjustBy, locType);
                mek.setInternalCurrent(location, mek.getInternalCurrent(location) + adjustBy);
                if (rvIS == -1) { //location destroyed, display popup message
                    ISMessage(location);
                } else if (rvIS == 1) { //rv must equal 1, so display an appropriate message only
                    armorToastMessage(location, R.string.location_maxed);
                }
                break;
        }

        updateView();
    }

    private void ISMessage(Locations location) {
        ((ActivityMain) getActivity()).displayMessage(getString(R.string.internal_destroyed));
    }

    private void armorToastMessage(Locations location, int message) {
        Toast.makeText(getActivity(), getString(message), Toast.LENGTH_LONG).show();
    }

    /**
     * Validates that the armor operation is valid
     *
     * @param location     location to check
     * @param adjustBy     how much we adjust it by
     * @param locationType 0 for armor, 1 for rear, -1 for internal
     * @return 0 - can proceed.  -1 means location is destroyed, 1 means location has max value already
     */
    private int validateStructureLevel(Locations location, int adjustBy, int locationType) {
        int currentValue = 0;
        int maxValue = 0;
        switch (locationType) {
            case 0:
                currentValue = mek.getArmorCurrent(location);
                maxValue = mek.getArmorMax(location);
                break;
            case 1:
                currentValue = mek.getArmorRearCurrent(location);
                maxValue = mek.getArmorRearMax(location);
                break;
            case -1:
                currentValue = mek.getInternalCurrent(location);
                maxValue = mek.getInternalMax(location);
                break;
        }

        if (adjustBy < 0 && currentValue == 0)
            return -1;
        else if (adjustBy > 0 && currentValue == maxValue)
            return 1;
        return 0;
    }
}
