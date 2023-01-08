/*
 * Copyright (c) 2018
 * Terry Doerksen
 * https://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package ca.coffeeshopstudio.meksheets.views;

import android.app.Fragment;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
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
        return new FragmentArmor();
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
        String ah = getString(R.string.armor_h, mech.getArmorCurrent(Locations.head), mech.getArmorMax(Locations.head));
        String ala = getString(R.string.armor_la, mech.getArmorCurrent(Locations.leftArm), mech.getArmorMax(Locations.leftArm));
        String ara = getString(R.string.armor_ra, mech.getArmorCurrent(Locations.rightArm), mech.getArmorMax(Locations.rightArm));
        String act = getString(R.string.armor_ct, mech.getArmorCurrent(Locations.centerTorso), mech.getArmorMax(Locations.centerTorso));
        String alt = getString(R.string.armor_lt, mech.getArmorCurrent(Locations.leftTorso), mech.getArmorMax(Locations.leftTorso));
        String art = getString(R.string.armor_rt, mech.getArmorCurrent(Locations.rightTorso), mech.getArmorMax(Locations.rightTorso));
        String all = getString(R.string.armor_ll, mech.getArmorCurrent(Locations.leftLeg), mech.getArmorMax(Locations.leftLeg));
        String arl = getString(R.string.armor_rl, mech.getArmorCurrent(Locations.rightLeg), mech.getArmorMax(Locations.rightLeg));

        String arct = getString(R.string.armor_ct, mech.getArmorRearCurrent(Locations.centerTorso), mech.getArmorRearMax(Locations.centerTorso));
        String arlt = getString(R.string.armor_lt, mech.getArmorRearCurrent(Locations.leftTorso), mech.getArmorRearMax(Locations.leftTorso));
        String arrt = getString(R.string.armor_rt, mech.getArmorRearCurrent(Locations.rightTorso), mech.getArmorRearMax(Locations.rightTorso));

        String ih = getString(R.string.armor_h, mech.getInternalCurrent(Locations.head), mech.getInternalMax(Locations.head));
        String ila = getString(R.string.armor_la, mech.getInternalCurrent(Locations.leftArm), mech.getInternalMax(Locations.leftArm));
        String ira = getString(R.string.armor_ra, mech.getInternalCurrent(Locations.rightArm), mech.getInternalMax(Locations.rightArm));
        String ict = getString(R.string.armor_ct, mech.getInternalCurrent(Locations.centerTorso), mech.getInternalMax(Locations.centerTorso));
        String ilt = getString(R.string.armor_lt, mech.getInternalCurrent(Locations.leftTorso), mech.getInternalMax(Locations.leftTorso));
        String irt = getString(R.string.armor_rt, mech.getInternalCurrent(Locations.rightTorso), mech.getInternalMax(Locations.rightTorso));
        String ill = getString(R.string.armor_ll, mech.getInternalCurrent(Locations.leftLeg), mech.getInternalMax(Locations.leftLeg));
        String irl = getString(R.string.armor_rl, mech.getInternalCurrent(Locations.rightLeg), mech.getInternalMax(Locations.rightLeg));

        ((Button)root.findViewById(R.id.btnHead)).setText(ah);
        setButtonStyle(root.findViewById(R.id.btnHead), mech.getArmorCurrent(Locations.head), mech.getArmorMax(Locations.head));
        ((Button)root.findViewById(R.id.btnLeftArm)).setText(ala);
        setButtonStyle(root.findViewById(R.id.btnLeftArm), mech.getArmorCurrent(Locations.leftArm), mech.getArmorMax(Locations.leftArm));
        ((Button)root.findViewById(R.id.btnRightArm)).setText(ara);
        setButtonStyle(root.findViewById(R.id.btnRightArm), mech.getArmorCurrent(Locations.rightArm), mech.getArmorMax(Locations.rightArm));
        ((Button)root.findViewById(R.id.btnCenterTorso)).setText(act);
        setButtonStyle(root.findViewById(R.id.btnCenterTorso), mech.getArmorCurrent(Locations.centerTorso), mech.getArmorMax(Locations.centerTorso));
        ((Button)root.findViewById(R.id.btnLeftTorso)).setText(alt);
        setButtonStyle(root.findViewById(R.id.btnLeftTorso), mech.getArmorCurrent(Locations.leftTorso), mech.getArmorMax(Locations.leftTorso));
        ((Button)root.findViewById(R.id.btnRightTorso)).setText(art);
        setButtonStyle(root.findViewById(R.id.btnRightTorso), mech.getArmorCurrent(Locations.rightTorso), mech.getArmorMax(Locations.rightTorso));
        ((Button)root.findViewById(R.id.btnLeftLeg)).setText(all);
        setButtonStyle(root.findViewById(R.id.btnLeftLeg), mech.getArmorCurrent(Locations.leftLeg), mech.getArmorMax(Locations.leftLeg));
        ((Button)root.findViewById(R.id.btnRightLeg)).setText(arl);
        setButtonStyle(root.findViewById(R.id.btnRightLeg), mech.getArmorCurrent(Locations.rightLeg), mech.getArmorMax(Locations.rightLeg));

        ((Button)root.findViewById(R.id.btnRearCenter)).setText(arct);
        setButtonStyle(root.findViewById(R.id.btnRearCenter), mech.getArmorRearCurrent(Locations.centerTorso), mech.getArmorRearMax(Locations.centerTorso));
        ((Button)root.findViewById(R.id.btnRearLeft)).setText(arlt);
        setButtonStyle(root.findViewById(R.id.btnRearLeft), mech.getArmorRearCurrent(Locations.leftTorso), mech.getArmorRearMax(Locations.leftTorso));
        ((Button)root.findViewById(R.id.btnRearRight)).setText(arrt);
        setButtonStyle(root.findViewById(R.id.btnRearRight), mech.getArmorRearCurrent(Locations.rightTorso), mech.getArmorRearMax(Locations.rightTorso));

        ((Button)root.findViewById(R.id.btnInternalHead)).setText(ih);
        setButtonStyle(root.findViewById(R.id.btnInternalHead), mech.getInternalCurrent(Locations.head), mech.getInternalMax(Locations.head));
        ((Button)root.findViewById(R.id.btnInternalLeftArm)).setText(ila);
        setButtonStyle(root.findViewById(R.id.btnInternalLeftArm), mech.getInternalCurrent(Locations.leftArm), mech.getInternalMax(Locations.leftArm));
        ((Button)root.findViewById(R.id.btnInternalRightArm)).setText(ira);
        setButtonStyle(root.findViewById(R.id.btnInternalRightArm), mech.getInternalCurrent(Locations.rightArm), mech.getInternalMax(Locations.rightArm));
        ((Button)root.findViewById(R.id.btnInternalCenterTorso)).setText(ict);
        setButtonStyle(root.findViewById(R.id.btnInternalCenterTorso), mech.getInternalCurrent(Locations.centerTorso), mech.getInternalMax(Locations.centerTorso));
        ((Button)root.findViewById(R.id.btnInternalLeftTorso)).setText(ilt);
        setButtonStyle(root.findViewById(R.id.btnInternalLeftTorso), mech.getInternalCurrent(Locations.leftTorso), mech.getInternalMax(Locations.leftTorso));
        ((Button)root.findViewById(R.id.btnInternalRightTorso)).setText(irt);
        setButtonStyle(root.findViewById(R.id.btnInternalRightTorso), mech.getInternalCurrent(Locations.rightTorso), mech.getInternalMax(Locations.rightTorso));
        ((Button)root.findViewById(R.id.btnInternalLeftLeg)).setText(ill);
        setButtonStyle(root.findViewById(R.id.btnInternalLeftLeg), mech.getInternalCurrent(Locations.leftLeg), mech.getInternalMax(Locations.leftLeg));
        ((Button)root.findViewById(R.id.btnInternalRightLeg)).setText(irl);
        setButtonStyle(root.findViewById(R.id.btnInternalRightLeg), mech.getInternalCurrent(Locations.rightLeg), mech.getInternalMax(Locations.rightLeg));
    }

    private void setButtonStyle(View button, int armorCurrent, int armorMax) {
        if (armorCurrent == armorMax) {
            button.setBackgroundColor(requireActivity().getResources().getColor(R.color.statusGood));
        } else if (armorCurrent > 0) {
            button.setBackgroundColor(requireActivity().getResources().getColor(R.color.statusBad));
        } else {
            button.setBackgroundColor(requireActivity().getResources().getColor(R.color.statusCritical));
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
        int id = view.getId();//case R.id.btnInternalRightLeg:
        if (id == R.id.btnHead) {
            location = Locations.head;
            locType = 0;
        } else if (id == R.id.btnCenterTorso) {
            location = Locations.centerTorso;
            locType = 0;
        } else if (id == R.id.btnLeftTorso) {
            location = Locations.leftTorso;
            locType = 0;
        } else if (id == R.id.btnRightTorso) {
            location = Locations.rightTorso;
            locType = 0;
        } else if (id == R.id.btnLeftArm) {
            location = Locations.leftArm;
            locType = 0;
        } else if (id == R.id.btnRightArm) {
            location = Locations.rightArm;
            locType = 0;
        } else if (id == R.id.btnLeftLeg) {
            location = Locations.leftLeg;
            locType = 0;
        } else if (id == R.id.btnRightLeg) {
            location = Locations.rightLeg;
            locType = 0;
        } else if (id == R.id.btnRearCenter) {
            location = Locations.centerTorso;
            locType = 1;
        } else if (id == R.id.btnRearLeft) {
            location = Locations.leftTorso;
            locType = 1;
        } else if (id == R.id.btnRearRight) {
            location = Locations.rightTorso;
            locType = 1;
        } else if (id == R.id.btnInternalHead) {
            location = Locations.head;
            locType = -1;
        } else if (id == R.id.btnInternalCenterTorso) {
            location = Locations.centerTorso;
            locType = -1;
        } else if (id == R.id.btnInternalLeftTorso) {
            location = Locations.leftTorso;
            locType = -1;
        } else if (id == R.id.btnInternalRightTorso) {
            location = Locations.rightTorso;
            locType = -1;
        } else if (id == R.id.btnInternalLeftArm) {
            location = Locations.leftArm;
            locType = -1;
        } else if (id == R.id.btnInternalRightArm) {
            location = Locations.rightArm;
            locType = -1;
        } else if (id == R.id.btnInternalLeftLeg) {
            location = Locations.leftLeg;
            locType = -1;
        } else {
            location = Locations.rightLeg;
            locType = -1;
        }

        adjustArmor(location, locType, adjustBy);
    }

    private void adjustArmor(Locations location, int locType, int adjustBy) {
        switch (locType) {
            case 0:
                int rvA = validateStructureLevel(location, adjustBy, locType);
                mech.setArmorCurrent(location, mech.getArmorCurrent(location) + adjustBy);
                if (rvA == -1) { //location destroyed, display message and pass to IS
                    armorToastMessage(R.string.armor_destroyed);
                    adjustArmor(location, -1, adjustBy); //change to IS
                } else if (rvA == 1) { //rv must equal 1, so display an appropriate message only
                    armorToastMessage(R.string.location_maxed);
                }
                break;
            case 1:
                int rvRA = validateStructureLevel(location, adjustBy, locType);
                mech.setArmorRearCurrent(location, mech.getArmorRearCurrent(location) + adjustBy);
                if (rvRA == -1) { //location destroyed, display message and pass to IS
                    armorToastMessage(R.string.armor_rear_destroyed);
                    adjustArmor(location, -1, adjustBy); //change to IS
                } else if (rvRA == 1) { //rv must equal 1, so display an appropriate message only
                    armorToastMessage(R.string.location_maxed);
                }
                break;
            default: //case -1:
                int rvIS = validateStructureLevel(location, adjustBy, locType);
                mech.setInternalCurrent(location, mech.getInternalCurrent(location) + adjustBy);
                if (rvIS == -1) { //location destroyed, display popup message
                    ISMessage();
                } else if (rvIS == 1) { //rv must equal 1, so display an appropriate message only
                    armorToastMessage(R.string.location_maxed);
                }
                break;
        }

        updateView();
    }

    private void ISMessage() {
        ((ActivityMain) requireActivity()).displayMessage(getString(R.string.internal_destroyed));
    }

    private void armorToastMessage(int message) {
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
                currentValue = mech.getArmorCurrent(location);
                maxValue = mech.getArmorMax(location);
                break;
            case 1:
                currentValue = mech.getArmorRearCurrent(location);
                maxValue = mech.getArmorRearMax(location);
                break;
            case -1:
                currentValue = mech.getInternalCurrent(location);
                maxValue = mech.getInternalMax(location);
                break;
        }

        if (adjustBy < 0 && currentValue == 0)
            return -1;
        else if (adjustBy > 0 && currentValue == maxValue)
            return 1;
        return 0;
    }
}
