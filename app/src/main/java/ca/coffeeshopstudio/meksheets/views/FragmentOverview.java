/*
 * Copyright (c) 2018
 * Terry Doerksen
 * https://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package ca.coffeeshopstudio.meksheets.views;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import ca.coffeeshopstudio.meksheets.R;
import ca.coffeeshopstudio.meksheets.models.Locations;
import ca.coffeeshopstudio.meksheets.models.Mek;

import static ca.coffeeshopstudio.meksheets.models.Mek.MTF_JUMP_COMPONENT;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentOverview.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentOverview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentOverview extends BaseFragment implements SeekBar.OnSeekBarChangeListener, View.OnClickListener, View.OnLongClickListener {

    private LinearLayout equipmentList;
    private LinearLayout ammoList;

    public FragmentOverview() {
        // Required empty public constructor
    }

    public static FragmentOverview newInstance() {
        return new FragmentOverview();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_overview, container, false);

        equipmentList = root.findViewById(R.id.listEquipment);
        ammoList = root.findViewById(R.id.listAmmo);

        root.findViewById(R.id.btnPilotSkill).setOnClickListener(this);
        root.findViewById(R.id.btnGunnerySkill).setOnClickListener(this);
        root.findViewById(R.id.btnPilotSkill).setLongClickable(true);
        root.findViewById(R.id.btnGunnerySkill).setLongClickable(true);
        root.findViewById(R.id.btnPilotSkill).setOnLongClickListener(this);
        root.findViewById(R.id.btnGunnerySkill).setOnLongClickListener(this);
        root.findViewById(R.id.btnSave).setOnClickListener(this);
        root.findViewById(R.id.btnDelete).setOnClickListener(this);
        root.findViewById(R.id.btnTwistRight).setOnClickListener(this);
        root.findViewById(R.id.btnTwistLeft).setOnClickListener(this);
        root.findViewById(R.id.btnTwistFront).setOnClickListener(this);
        loadDialog(R.string.help_overview, "overview");


        return root;
    }

    @Override
    void updateView () {
        String name = getString(R.string.overview_name, mek.getName());
        String tons = getString(R.string.overview_tons, mek.getTons());
        ((TextView) root.findViewById(R.id.txtName)).setText(name);
        ((TextView) root.findViewById(R.id.txtTons)).setText(tons);
        ((TextView) root.findViewById(R.id.txtDescription)).setText(mek.getDescription());
        updateSpeeds();
        updateEquipmentList();
        updateAmmoList();
        updateHeat();
        updateTwist();
        updatePilot();
    }

    private void updateTwist() {
        ((ToggleButton) root.findViewById(R.id.btnTwistRight)).setChecked(false);
        ((ToggleButton) root.findViewById(R.id.btnTwistLeft)).setChecked(false);
        ((ToggleButton) root.findViewById(R.id.btnTwistFront)).setChecked(false);
        ((ToggleButton) root.findViewById(R.id.btnTwistRight)).setTextColor(getResources().getColor(R.color.textPrimary));
        ((ToggleButton) root.findViewById(R.id.btnTwistLeft)).setTextColor(getResources().getColor(R.color.textPrimary));
        ((ToggleButton) root.findViewById(R.id.btnTwistFront)).setTextColor(getResources().getColor(R.color.textPrimary));

        switch (mek.getTorsoTwist()) {
            case 1:
                ((ToggleButton) root.findViewById(R.id.btnTwistRight)).setChecked(true);
                ((ToggleButton) root.findViewById(R.id.btnTwistRight)).setTextColor(getResources().getColor(R.color.accent));
                break;
            case -1:
                ((ToggleButton) root.findViewById(R.id.btnTwistLeft)).setChecked(true);
                ((ToggleButton) root.findViewById(R.id.btnTwistLeft)).setTextColor(getResources().getColor(R.color.accent));
                break;
            default:
                ((ToggleButton) root.findViewById(R.id.btnTwistFront)).setChecked(true);
                ((ToggleButton) root.findViewById(R.id.btnTwistFront)).setTextColor(getResources().getColor(R.color.accent));
                break;
        }
    }

    private void updatePilot() {
        String piloting = getString(R.string.overview_pilot_skill, mek.getPilot().getPiloting());
        String gunnery = getString(R.string.overview_gunnery_skill, mek.getPilot().getGunnery());
        String hits = getString(R.string.overview_pilots_hits, mek.getPilot().getHits());
        ((TextView) root.findViewById(R.id.btnPilotSkill)).setText(piloting);
        ((TextView) root.findViewById(R.id.btnGunnerySkill)).setText(gunnery);
        ((TextView) root.findViewById(R.id.txtPilotHits)).setText(hits);
        if (mek.getPilot().getHits() > 0)
            ((TextView) root.findViewById(R.id.txtPilotHits)).setTextColor(getResources().getColor(R.color.statusBad));
        else
            ((TextView) root.findViewById(R.id.txtPilotHits)).setTextColor(getResources().getColor(R.color.statusGood));
        ((SeekBar) root.findViewById(R.id.seekPilotHits)).setOnSeekBarChangeListener(null);
        ((SeekBar) root.findViewById(R.id.seekPilotHits)).setProgress(mek.getPilot().getHits());
        ((SeekBar) root.findViewById(R.id.seekPilotHits)).setOnSeekBarChangeListener(this);
    }

    private void updateHeat() {
        String heat = getString(R.string.overview_heatsinks, mek.getCurrentHeatSinks(), mek.getMaxHeatSinks());
        String heatLevel = getString(R.string.overview_heat_scale, mek.getHeatLevel());
        ((TextView) root.findViewById(R.id.txtHeatsinks)).setText(heat);
        if (mek.getCurrentHeatSinks() < mek.getMaxHeatSinks())
            ((TextView) root.findViewById(R.id.txtHeatsinks)).setTextColor(getResources().getColor(R.color.statusBad));
        else
            ((TextView) root.findViewById(R.id.txtHeatsinks)).setTextColor(getResources().getColor(R.color.statusGood));

        ((TextView) root.findViewById(R.id.txtHeatScale)).setText(heatLevel);
        ((SeekBar) root.findViewById(R.id.seekHeat)).setOnSeekBarChangeListener(null);
        ((SeekBar) root.findViewById(R.id.seekHeat)).setProgress(mek.getHeatLevel());
        ((SeekBar) root.findViewById(R.id.seekHeat)).setOnSeekBarChangeListener(this);
    }

    private void updateSpeeds() {
        //get the basics
        double walkSpeed = mek.getWalk();
        int jumpSpeed = mek.getJump();
        boolean reducedSpeed = false;
        boolean reducedJumpRange = false;
        boolean legsBlownOff = false;

        //calculate damage penalities
        //for walking / running - hips cut us in half per destroyed hip.  actuators do not matter
        if (mek.getInternalCurrent(Locations.leftLeg) == 0 || mek.getInternalCurrent(Locations.rightLeg) == 0) {
            walkSpeed = 1;
            reducedSpeed = true;
            legsBlownOff = true;
        } else {
            int hipsDestroyed = 0;
            if (!mek.getComponents(Locations.leftLeg)[0].isFunctioning())
                hipsDestroyed++;
            if (!mek.getComponents(Locations.rightLeg)[0].isFunctioning())
                hipsDestroyed++;
            if (hipsDestroyed == 1) {
                walkSpeed = walkSpeed / 2;
                reducedSpeed = true;
            } else if (hipsDestroyed == 2) {
                walkSpeed = 0;
                reducedSpeed = true;
            } else { //hips are fine, check the actuators
                for (int i = 1; i < 4; i++) {
                    if (!mek.getComponents(Locations.leftLeg)[i].isFunctioning()) {
                        reducedSpeed = true;
                        walkSpeed--;
                    }
                    if (!mek.getComponents(Locations.rightLeg)[i].isFunctioning()) {
                        walkSpeed--;
                        reducedSpeed = true;
                    }
                }
            }
        }

        //for jumpjets look for broken ones, subtract by 1 per
        for (int i = 0; i < 12; i++) {
            if (!mek.getComponents(Locations.leftTorso)[i].isFunctioning() &&
                    mek.getComponents(Locations.leftTorso)
                            [i].getName().replace(" ", "").contains(MTF_JUMP_COMPONENT)) {
                jumpSpeed--;
                reducedJumpRange = true;
            }
            if (!mek.getComponents(Locations.rightTorso)[i].isFunctioning() &&
                    mek.getComponents(Locations.rightTorso)
                            [i].getName().replace(" ", "").contains(MTF_JUMP_COMPONENT)) {
                jumpSpeed--;
                reducedJumpRange = true;
            }
            if (!mek.getComponents(Locations.centerTorso)[i].isFunctioning() &&
                    mek.getComponents(Locations.centerTorso)
                            [i].getName().replace(" ", "").contains(MTF_JUMP_COMPONENT)) {
                jumpSpeed--;
                reducedJumpRange = true;
            }
        }
        for (int i = 3; i < 6; i++) {
            if (!mek.getComponents(Locations.leftLeg)[i].isFunctioning() &&
                    mek.getComponents(Locations.leftLeg)
                            [i].getName().replace(" ", "").contains(MTF_JUMP_COMPONENT)) {
                jumpSpeed--;
                reducedJumpRange = true;
            }
            if (!mek.getComponents(Locations.rightLeg)[i].isFunctioning() &&
                    mek.getComponents(Locations.rightLeg)
                            [i].getName().replace(" ", "").contains(MTF_JUMP_COMPONENT)) {
                jumpSpeed--;
                reducedJumpRange = true;
            }
        }

        //adjust for calculations and rounding
        long actualWalkSpeed = Math.round(walkSpeed);
        //first set to zero for run calculation, then reset to 1 - if necessary
        if (actualWalkSpeed < 0)
            actualWalkSpeed = 0;
        double runSpeed = actualWalkSpeed * 1.5;
        if (legsBlownOff)
            runSpeed = 1;
        if (actualWalkSpeed < 1)
            actualWalkSpeed = 1;
        String walk = getString(R.string.overview_walking, Math.round(actualWalkSpeed));
        String run = getString(R.string.overview_running, Math.round(runSpeed));
        String jump = getString(R.string.overview_jumping, jumpSpeed);
        ((TextView) root.findViewById(R.id.txtWalking)).setText(walk);
        ((TextView) root.findViewById(R.id.txtRunning)).setText(run);
        ((TextView) root.findViewById(R.id.txtJumping)).setText(jump);

        if (reducedJumpRange)
            ((TextView) root.findViewById(R.id.txtJumping)).setTextColor(getResources().getColor(R.color.statusBad));
        else
            ((TextView) root.findViewById(R.id.txtJumping)).setTextColor(getResources().getColor(R.color.statusGood));
        if (reducedSpeed) {
            ((TextView) root.findViewById(R.id.txtWalking)).setTextColor(getResources().getColor(R.color.statusBad));
            ((TextView) root.findViewById(R.id.txtRunning)).setTextColor(getResources().getColor(R.color.statusBad));
        } else {
            ((TextView) root.findViewById(R.id.txtRunning)).setTextColor(getResources().getColor(R.color.statusGood));
            ((TextView) root.findViewById(R.id.txtWalking)).setTextColor(getResources().getColor(R.color.statusGood));
        }
    }

    private void updateAmmoList() {
        //first let's get a list of destroyed gear.
        List<String> destroyedGear = getDestroyedGear();

        ammoList.removeAllViews();

        for (int i = 0; i < mek.getAmmo().size(); i++) {
            if (!destroyedGear.contains(mek.getAmmo().get(i).getName())) {
                addAmmoButton(i, mek.getAmmo().get(i), false);
            } else {
                addAmmoButton(i, mek.getAmmo().get(i), true);
                destroyedGear.remove(mek.getAmmo().get(i).getName());
            }
        }

    }

    private void addAmmoButton(int id, Mek.Ammo item, boolean disabled) {
        Button button = new Button(getActivity());
        String ammo = getString(R.string.overview_ammo, item.getName(), item.getShotsFired());
        button.setText(ammo);
        button.setEnabled(!disabled);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 8, 0, 8);
        button.setLayoutParams(params);
        button.setPadding(4, 4, 4, 4);
        if (!disabled) {
            button.setBackgroundColor(getResources().getColor(R.color.accent));
            button.setTextColor(getResources().getColor(R.color.textIcons));
        }
        button.setTag(id);
        button.setLongClickable(true);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adjustAmmoUse(view, 1);
            }
        });

        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                adjustAmmoUse(view, -1);
                return true;
            }
        });

        ammoList.addView(button);
    }

    private void adjustAmmoUse(View view, int increment) {
        int id = (Integer) view.getTag();
        Mek.Ammo item = mek.getAmmo().get(id);
        mek.setAmmoIncrememntShotsFired(id, increment);
        String ammo = getString(R.string.overview_ammo, item.getName(), item.getShotsFired());
        ((TextView) view).setText(ammo);
    }

    private void updateEquipmentList() {
        //first let's get a list of destroyed gear.
        List<String> destroyedGear = getDestroyedGear();

        equipmentList.removeAllViews();
        addEquipmentCheckbox(getString(R.string.all), false, false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < equipmentList.getChildCount(); i++) {
                    if (equipmentList.getChildAt(i) instanceof CheckBox) {
                        ((CheckBox) equipmentList.getChildAt(i)).setChecked(((CheckBox) view).isChecked());
                        for (final Mek.Equipment equipment : mek.getEquipment()) {
                            equipment.setChecked(((CheckBox) equipmentList.getChildAt(i)).isChecked());
                        }
                    }
                }
            }
        });
        for (final Mek.Equipment equipment : mek.getEquipment()) {
            boolean foundGear = false;
            for (int i = 0; i < destroyedGear.size(); i++) {
                String destroyedItem = sanitizeString(destroyedGear.get(i));
                String itemSearchingFor = sanitizeString(equipment.getName());
                if (destroyedItem.contains(itemSearchingFor) || itemSearchingFor.contains(destroyedItem)) {
                    foundGear = true;
                    destroyedGear.remove(i);
                }
            }
            if (!foundGear) {
                addEquipmentCheckbox(equipment.getName(), equipment.isChecked(), false, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        equipment.setChecked(!equipment.isChecked());
                    }
                });
            } else {
                addEquipmentCheckbox(equipment.getName(), equipment.isChecked(), true, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        equipment.setChecked(!equipment.isChecked());
                    }
                });
            }
        }

    }

    private void addEquipmentCheckbox(String item, boolean checked, boolean disabled, View.OnClickListener action) {
        CheckBox checkbox = new CheckBox(getActivity());
        checkbox.setText(item);
        checkbox.setEnabled(!disabled);
        checkbox.setChecked(checked);
        if (action != null)
            checkbox.setOnClickListener(action);
        equipmentList.addView(checkbox);
    }

    private List<String> getDestroyedGear() {
        List<String> rv = new ArrayList<>();
        for (Mek.Component component : mek.getComponents(Locations.head) ) {
            if (!component.isFunctioning()) {
                String componentName = component.getName().replace(" (R)", "");
                rv.add(componentName + ", " + "Head");
            }
        }        for (Mek.Component component : mek.getComponents(Locations.centerTorso) ) {
            if (!component.isFunctioning()) {
                String componentName = component.getName().replace(" (R)", "");
                rv.add(componentName + ", " + "Center Torso");
            }
        }
        for (Mek.Component component : mek.getComponents(Locations.leftTorso) ) {
            if (!component.isFunctioning()) {
                String componentName = component.getName().replace(" (R)", "");
                rv.add(componentName + ", " + "Left Torso");
            }
        }
        for (Mek.Component component : mek.getComponents(Locations.rightTorso) ) {
            if (!component.isFunctioning()) {
                String componentName = component.getName().replace(" (R)", "");
                rv.add(componentName + ", " + "Right Torso");
            }
        }
        for (Mek.Component component : mek.getComponents(Locations.leftLeg) ) {
            if (!component.isFunctioning()) {
                String componentName = component.getName().replace(" (R)", "");
                rv.add(componentName + ", " + "Left Leg");
            }
        }
        for (Mek.Component component : mek.getComponents(Locations.rightLeg) ) {
            if (!component.isFunctioning()) {
                String componentName = component.getName().replace(" (R)", "");
                rv.add(componentName + ", " + "Right Leg");
            }
        }
        for (Mek.Component component : mek.getComponents(Locations.leftArm) ) {
            if (!component.isFunctioning()) {
                String componentName = component.getName().replace(" (R)", "");
                rv.add(componentName + ", " + "Left Arm");
            }
        }
        for (Mek.Component component : mek.getComponents(Locations.rightArm) ) {
            if (!component.isFunctioning()) {
                String componentName = component.getName().replace(" (R)", "");
                rv.add(componentName + ", " + "Right Arm");
            }
        }

        return rv;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.seekHeat:
                mek.setHeatLevel(i);
                updateHeat();
                break;
            case R.id.seekPilotHits:
                mek.getPilot().setHits(i);
                updatePilot();
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                String description;
                description = String.valueOf(((TextView)root.findViewById(R.id.txtDescription)).getText());
                mek.setDescription(description);
                ((ActivityMain)getActivity()).save();
                break;
            case R.id.btnDelete:
                showDeleteRequest();
            case R.id.btnTwistFront:
                mek.setTorsoTwist(0);
                updateTwist();
                break;
            case R.id.btnTwistLeft:
                mek.setTorsoTwist(-1);
                updateTwist();
                break;
            case R.id.btnTwistRight:
                mek.setTorsoTwist(1);
                updateTwist();
                break;
            default:
                adjustSkill(view, 1);
            break;
        }
    }

    private void showDeleteRequest() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.app_name);
        builder.setMessage(getString(R.string.overview_confirm_delete, mek.getName()));
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                ((ActivityMain)getActivity()).delete();
            }
        });
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onLongClick(View view) {
        adjustSkill(view, -1);
        return true;
    }

    private void adjustSkill(View view, int adjustBy) {
        switch (view.getId()) {
            case R.id.btnPilotSkill:
                mek.getPilot().setPiloting(mek.getPilot().getPiloting() + adjustBy);
                break;
            case R.id.btnGunnerySkill:
                mek.getPilot().setGunnery(mek.getPilot().getGunnery() + adjustBy);
                break;
        }
        updatePilot();
    }
}
