/*
 * Copyright (c) 2018
 * Terry Doerksen
 * https://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package ca.coffeeshopstudio.meksheets.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ca.coffeeshopstudio.meksheets.models.Locations.centerTorso;
import static ca.coffeeshopstudio.meksheets.models.Locations.head;
import static ca.coffeeshopstudio.meksheets.models.Locations.leftArm;
import static ca.coffeeshopstudio.meksheets.models.Locations.leftLeg;
import static ca.coffeeshopstudio.meksheets.models.Locations.leftTorso;
import static ca.coffeeshopstudio.meksheets.models.Locations.rightArm;
import static ca.coffeeshopstudio.meksheets.models.Locations.rightLeg;
import static ca.coffeeshopstudio.meksheets.models.Locations.rightTorso;

public class Mek {

    private List<Equipment> equipment = new ArrayList<>();

    public class Component {
        private String name = MTF_EMPTY;
        private boolean status = true;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isFunctioning() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }

    private void addEquipment(String equipment) {
        Equipment e = new Equipment();
        e.setName(equipment);
        this.equipment.add(e);
    }

    private List<Ammo> ammo = new ArrayList<>();

    private static final String MTF_VERSION = "Version:1.";
    public static final String MTF_EMPTY = "-Empty-";
    public static final String MTF_ENDO = "Endo-Steel";
    public static final String MTF_FERRO = "Ferro-Fibrous";
    public static final CharSequence MTF_CASE = "CASE";
    private static final String MTF_TONS = "Mass:";
    private static final String MTF_HEAT_SINKS = "Heat Sinks:";
    private static final String MTF_SINGLE_HS = "Single";
    private static final String MTF_WALK = "Walk MP:";
    private static final String MTF_JUMP = "Jump MP:";
    private static final String MTF_ARMOR = "Armor:";
    private static final String MTF_ARMOR_LA = "LA Armor:";
    private static final String MTF_ARMOR_RA = "RA Armor:";
    private static final String MTF_ARMOR_LT = "LT Armor:";
    private static final String MTF_ARMOR_RT = "RT Armor:";
    private static final String MTF_ARMOR_CT = "CT Armor:";
    private static final String MTF_ARMOR_HD = "HD Armor:";
    private static final String MTF_ARMOR_LL = "LL Armor:";
    private static final String MTF_ARMOR_RL = "RL Armor:";
    private static final String MTF_ARMOR_LTR = "RTL Armor:";
    private static final String MTF_ARMOR_RTR = "RTR Armor:";
    private static final String MTF_ARMOR_CTR = "RTC Armor:";
    private static final String MTF_WEAPONS = "Weapons:";
    private static final String MTF_LA = "Left Arm:";
    private static final String MTF_RA = "Right Arm:";
    private static final String MTF_LT = "Left Torso:";
    private static final String MTF_RT = "Right Torso:";
    private static final String MTF_CT = "Center Torso:";
    private static final String MTF_H = "Head:";
    private static final String MTF_LL = "Left Leg";
    private static final String MTF_RL = "Right Leg";
    private static final String MTF_AMMO = "Ammo";
    public static final String MTF_HEAT_SINK = "HeatSink";
    public static final String MTF_JUMP_COMPONENT = "Jump";

    private String name;
    private int tons;
    private int heatSinksMax;
    //private int heatSinksCurrent;
    private int heatSinkType;
    private int heatLevel;
    private int walk;
    private int jump;
    private String description;
    private Pilot pilot = new Pilot();
    private String fileName = "";
    private int torsoTwist;

    private int [] armorMax = new int[Locations.values().length];
    private int [] armorCurrent = new int[Locations.values().length];
    private int [] armorRearMax = new int[3]; //ct,lt,rt
    private int [] armorRearCurrent = new int[3]; //ct,lt,rt
    private int [] internalMax = new int[Locations.values().length];
    private int [] internalCurrent = new int[Locations.values().length];
    private Component[] laComponents = new Component[12];
    private Component[] raComponents = new Component[12];
    private Component[] ctComponents = new Component[12];
    private Component[] hComponents = new Component[6];
    private Component[] ltComponents = new Component[12];
    private Component[] rtComponents = new Component[12];
    private Component[] llComponents = new Component[6];
    private Component[] rlComponents = new Component[6];

    public List<Equipment> getEquipment() {
        return equipment;
    }
    private int privateHeatSinksDestroyed = 0;

    public Mek() {
        for (int i = 0; i < laComponents.length; i++) {
            laComponents[i] = new Component();
        }
        for (int i = 0; i < raComponents.length; i++) {
            raComponents[i] = new Component();
        }
        for (int i = 0; i < hComponents.length; i++) {
            hComponents[i] = new Component();
        }
        for (int i = 0; i < ctComponents.length; i++) {
            ctComponents[i] = new Component();
        }
        for (int i = 0; i < ltComponents.length; i++) {
            ltComponents[i] = new Component();
        }
        for (int i = 0; i < rtComponents.length; i++) {
            rtComponents[i] = new Component();
        }
        for (int i = 0; i < llComponents.length; i++) {
            llComponents[i] = new Component();
        }
        for (int i = 0; i < rlComponents.length; i++) {
            rlComponents[i] = new Component();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTons() {
        return tons;
    }

    public void setTons(int tons) {
        this.tons = tons;
    }

    public int getCurrentHeatSinks() {
        privateHeatSinksDestroyed = getDestroyedHeatsinkCount();
        return heatSinksMax - privateHeatSinksDestroyed;
    }

    public int getMaxHeatSinks() {
        return heatSinksMax;
    }

    public void setMaxHeatSinks(int heatSinks) {
        this.heatSinksMax = heatSinks;
    }

    public int getHeatSinkType() {
        return heatSinkType;
    }

    public void setHeatSinkType(int heatSinkType) {
        this.heatSinkType = heatSinkType;
    }

    public int getWalk() {
        return walk;
    }

    public void setWalk(int walk) {
        this.walk = walk;
    }

    public int getJump() {
        return jump;
    }

    public void setJump(int jump) {
        this.jump = jump;
    }

    public int getArmorMax(Locations position) {
        return armorMax[position.getValue()];
    }

    public void setArmorMax(Locations position, int value) {
        this.armorMax[position.getValue()] = value;
    }

    public int getArmorCurrent(Locations position) {
        return armorCurrent[position.getValue()];
    }

    public void setArmorCurrent(Locations position, int value) {
        if (value >= 0)
            this.armorCurrent[position.getValue()] = value;
    }

    public int getArmorRearMax(Locations position) {
        switch (position) {
            case centerTorso:
                return armorRearMax[0];
            case leftTorso:
                return armorRearMax[1];
            case rightTorso:
                return armorRearMax[2];
        }
        return 0;
    }

    public void setArmorRearMax(Locations position, int value) {
        switch (position) {
            case centerTorso:
                this.armorRearMax[0] = value;
            case leftTorso:
                this.armorRearMax[1] = value;
            case rightTorso:
                this.armorRearMax[2] = value;
        }
            }

    public int getArmorRearCurrent(Locations position) {
        return armorRearCurrent[position.getValue() - 1];
    }

    public void setArmorRearCurrent(Locations position, int value) {
        if (value >= 0)
            this.armorRearCurrent[position.getValue() - 1] = value;
    }

    public int getInternalMax(Locations position) {
        return internalMax[position.getValue()];
    }

    public void setInternalMax(Locations position, int value) {
            this.internalMax[position.getValue()] = value;
    }

    public int getInternalCurrent(Locations position) {
        return internalCurrent[position.getValue()];
    }

    public void setInternalCurrent(Locations position, int value) {
        if (value >= 0)
            this.internalCurrent[position.getValue()] = value;
    }

    public Component[] getComponents(Locations location) {
        switch (location) {
            case leftArm:
                return laComponents;
            case rightArm:
                return raComponents;
            case centerTorso:
                return ctComponents;
            case head:
                return hComponents;
            case leftTorso:
                return ltComponents;
            case rightTorso:
                return rtComponents;
            case leftLeg:
                return llComponents;
            case rightLeg:
                return rlComponents;
        }
        return null;
    }

    public void setComponent(Locations location, int position, String newName, boolean status) {
        switch (location) {
            case leftArm:
                laComponents[position].status = status;
                laComponents[position].name = newName;
            break;
            case rightArm:
                raComponents[position].status = status;
                raComponents[position].name = newName;
            break;
            case centerTorso:
                ctComponents[position].status = status;
                ctComponents[position].name = newName;
            break;
            case head:
                hComponents[position].status = status;
                hComponents[position].name = newName;
            break;
            case leftTorso:
                ltComponents[position].status = status;
                ltComponents[position].name = newName;
            break;
            case rightTorso:
                rtComponents[position].status = status;
                rtComponents[position].name = newName;
            break;
            case leftLeg:
                llComponents[position].status = status;
                llComponents[position].name = newName;
            break;
            case rightLeg:
                rlComponents[position].status = status;
                rlComponents[position].name = newName;
            break;
        }
    }

    private void resetComponent(Component component) {
        component.setStatus(!component.isFunctioning());
    }

    public void toggleComponent(Locations location, int position) {
        switch (location) {
            case leftArm:
                resetComponent(laComponents[position]);
                break;
            case rightArm:
                resetComponent(raComponents[position]);
                break;
            case centerTorso:
                resetComponent(ctComponents[position]);
                break;
            case head:
                resetComponent(hComponents[position]);
                break;
            case leftTorso:
                resetComponent(ltComponents[position]);
                break;
            case rightTorso:
                resetComponent(rtComponents[position]);
                break;
            case leftLeg:
                resetComponent(llComponents[position]);
                break;
            case rightLeg:
                resetComponent(rlComponents[position]);
                break;
        }
    }

    public class Ammo {
        private String name;
        private Locations location;
        private int shotsFired;
        private boolean status = true;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Locations getLocation() {
            return location;
        }

        public void setLocation(Locations location) {
            this.location = location;
        }

        public int getShotsFired() {
            return shotsFired;
        }

        public void setShotsFired(int shotsFired) {
            this.shotsFired = shotsFired;
        }

        public boolean isActive() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }

    public class Equipment {
        private String name;
        private boolean checked = false;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

    }

    public List<Ammo> getAmmo() {
        return ammo;
    }

    public void setAmmoStatus(int id, boolean status) {
        ammo.get(id).setStatus(status);
    }

    public void setAmmoIncrememntShotsFired(int id, int increment) {
        ammo.get(id).setShotsFired(ammo.get(id).getShotsFired() + increment);
    }

    private void addAmmo(String component, Locations location) {
        Ammo ammo = new Ammo();
        ammo.setShotsFired(0);
        ammo.setStatus(true);
        ammo.setLocation(location);

        String itemName = component;
        switch (location) {
            case leftArm:
                itemName += ", " + "Left Arm";
                break;
            case rightArm:
                itemName += ", " + "Right Arm";
                break;
            case centerTorso:
                itemName += ", " + "Center Torso";
                break;
            case leftTorso:
                itemName += ", " + "Left Torso";
                break;
            case rightTorso:
                itemName += ", " + "Right Torso";
                break;
            case head:
                itemName += ", " + "Head";
                break;
            case leftLeg:
                itemName += ", " + "Left Leg";
                break;
            case rightLeg:
                itemName += ", " + "Right Leg";
                break;
        }
        ammo.setName(itemName);
        this.ammo.add(ammo);
    }

    private void extractComponents(Locations location, String component, BufferedReader br) throws IOException {
        int position = -1;
        do {
            position++;
            setComponent(location, position, component, true);
            if (component.contains(MTF_AMMO)) {
                addAmmo(component, location);
            }
            component = br.readLine();
        }
        while ((component != null) && !component.startsWith(" ") && !component.isEmpty() && position < getComponents(location).length - 1);
    }

    public void removeEquipment(String equipment) {
        this.equipment.remove(equipment);
    }

    public int getHeatLevel() {
        return heatLevel;
    }

    public void setHeatLevel(int heatLevel) {
        this.heatLevel = heatLevel;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getTorsoTwist() {
        return torsoTwist;
    }

    public void setTorsoTwist(int torsoTwist) {
        this.torsoTwist = torsoTwist;
    }

    public void readMTF(BufferedReader br) throws IOException {
//        File sdcard = Environment.getExternalStorageDirectory();
//        File file = new File(sdcard,"/download/Locust LCT-1V.mtf");
//
//        //Read text from file
//        StringBuilder text = new StringBuilder();

        String line;
        if (! validateMekFile(br)) {
            throw new IOException("invalid file format!");
        }
        while ((line = br.readLine()) != null) {
            extractMekInfo(line, br);
        }
        br.close();

        //if we got here, all is well!
    }

    private boolean validateMekFile(BufferedReader br) throws IOException {
        String line = br.readLine();
        //GET NAME
        if (line.startsWith(MTF_VERSION)) { //the information we want is on the next 2 lines
            String name = br.readLine();
            name += br.readLine();
            setName(name);
            return true;
        }
        return false;
    }

    private void extractMekInfo(String line, BufferedReader br) throws IOException {
        //GET NAME
        if (line.startsWith(MTF_VERSION)) { //the information we want is on the next 2 lines
            String name = br.readLine();
            name += br.readLine();
            setName(name);
        }
        //GET TONS
        if (line.startsWith(MTF_TONS)) {
            setTons(Integer.parseInt(line.substring(MTF_TONS.length())));
            //we can now calculate the internal structure
            setInternalStrutcture();
        }
        //GET HEAT SINKS
        if (line.startsWith(MTF_HEAT_SINKS)) {
            extractHeatSinks(line);
        }
        //GET WALKING SPEED
        if (line.startsWith(MTF_WALK)) {
            setWalk(Integer.parseInt(line.substring(MTF_WALK.length())));
        }
        //GET JUMP SPEED
        if (line.startsWith(MTF_JUMP)) {
            setJump(Integer.parseInt(line.substring(MTF_JUMP.length())));
        }
        //GET ARMOR
        if (line.startsWith(MTF_ARMOR)) {
            extractArmor(br.readLine(), br);
        }
        if (line.startsWith(MTF_WEAPONS)) {
            extractEquipment(br.readLine(), br);
        }
        if (line.startsWith(MTF_LA)) {
            extractComponents(leftArm, br.readLine(), br);
        }
        if (line.startsWith(MTF_RA)) {
            extractComponents(rightArm, br.readLine(), br);
        }
        if (line.startsWith(MTF_CT)) {
            extractComponents(centerTorso, br.readLine(), br);
        }
        if (line.startsWith(MTF_H)) {
            extractComponents(head, br.readLine(), br);
        }
        if (line.startsWith(MTF_LT)) {
            extractComponents(leftTorso, br.readLine(), br);
        }
        if (line.startsWith(MTF_RT)) {
            extractComponents(rightTorso, br.readLine(), br);
        }
        if (line.startsWith(MTF_LL)) {
            extractComponents(leftLeg, br.readLine(), br);
        }
        if (line.startsWith(MTF_RL)) {
            extractComponents(rightLeg, br.readLine(), br);
        }

        //now set the current values to match the max values
    }

    private void extractEquipment(String line, BufferedReader br) throws IOException {
        do {
            addEquipment(line);
            line = br.readLine();
        } while (!line.startsWith(" ") && !line.isEmpty());
    }

    private void copyArray(int[] source, int[] destination) {
        System.arraycopy(source, 0, destination, 0, source.length);
        //current = Arrays.copyOf(max, max.length);
    }

    private void setInternalStrutcture() {
        //array order - head, ct, lt, rt, la, ra, ll, rl
        switch (getTons()) {
            case 10:
                buildInternal(4,3, 1, 2);
                break;
            case 15:
                buildInternal(5,4,2,3);
                break;
            case 20:
                buildInternal(6,5,3,4);
                break;
            case 25:
                buildInternal(8,6,4,6);
                break;
            case 30:
                buildInternal(10,7,5,7);
                break;
            case 35:
                buildInternal(11,8,6,8);
                break;
            case 40:
                buildInternal(12,10,6,10);
                break;
            case 45:
                buildInternal(14,11,7,11);
                break;
            case 50:
                buildInternal(16,12,8,12);
                break;
            case 55:
                buildInternal(18,13,9,13);
                break;
            case 60:
                buildInternal(20,14,10,14);
                break;
            case 65:
                buildInternal(21,15,10,15);
                break;
            case 70:
                buildInternal(22,15,11,15);
                break;
            case 75:
                buildInternal(23,16,12,16);
                break;
            case 80:
                buildInternal(25,17,13,17);
                break;
            case 85:
                buildInternal(27,18,14,18);
                break;
            case 90:
                buildInternal(29,19,15,19);
                break;
            case 95:
                buildInternal(30,20,16,20);
                break;
            case 100:
                buildInternal(31,21,17,21);
                break;
        }
        copyArray(internalMax, internalCurrent);
    }

    private void buildInternal(int centerTorso, int sideTorso, int arms, int legs) {
        int[] internal = {3, centerTorso, sideTorso, sideTorso, arms, arms, legs, legs};
        copyArray(internal, internalMax);
    }

    //we will loop through here until we find a space
    private void extractArmor(String line, BufferedReader br) throws IOException {
        do {
            if (line.startsWith(MTF_ARMOR_LA))
                setArmorMax(leftArm, Integer.parseInt(line.substring(MTF_ARMOR_LA.length())));
            if (line.startsWith(MTF_ARMOR_RA))
                setArmorMax(Locations.rightArm, Integer.parseInt(line.substring(MTF_ARMOR_RA.length())));
            if (line.startsWith(MTF_ARMOR_LT))
                setArmorMax(Locations.leftTorso, Integer.parseInt(line.substring(MTF_ARMOR_LT.length())));
            if (line.startsWith(MTF_ARMOR_RT))
                setArmorMax(Locations.rightTorso, Integer.parseInt(line.substring(MTF_ARMOR_RT.length())));
            if (line.startsWith(MTF_ARMOR_CT))
                setArmorMax(Locations.centerTorso, Integer.parseInt(line.substring(MTF_ARMOR_CT.length())));
            if (line.startsWith(MTF_ARMOR_HD))
                setArmorMax(head, Integer.parseInt(line.substring(MTF_ARMOR_HD.length())));
            if (line.startsWith(MTF_ARMOR_LL))
                setArmorMax(leftLeg, Integer.parseInt(line.substring(MTF_ARMOR_LL.length())));
            if (line.startsWith(MTF_ARMOR_RL))
                setArmorMax(Locations.rightLeg, Integer.parseInt(line.substring(MTF_ARMOR_RA.length())));
            if (line.startsWith(MTF_ARMOR_CTR))
                setArmorRearMax(Locations.centerTorso, Integer.parseInt(line.substring(MTF_ARMOR_CTR.length())));
            if (line.startsWith(MTF_ARMOR_LTR))
                setArmorRearMax(Locations.leftTorso, Integer.parseInt(line.substring(MTF_ARMOR_LTR.length())));
            if (line.startsWith(MTF_ARMOR_RTR))
                setArmorRearMax(Locations.rightTorso, Integer.parseInt(line.substring(MTF_ARMOR_RTR.length())));
            line = br.readLine();
        } while (!line.startsWith(" ") && !line.isEmpty());
        copyArray(armorMax, armorCurrent);
        copyArray(armorRearMax, armorRearCurrent);
    }

    private void extractHeatSinks(String line) {
        String tons = line.substring(MTF_HEAT_SINKS.length());
        setMaxHeatSinks(extractNumbers(tons));
        //setCurrentHeatSinks(extractNumbers(tons));
        if (tons.contains(MTF_SINGLE_HS)) {
            setHeatSinkType(0);
        } else {
            setHeatSinkType(1);
        }
    }

    public static int extractNumbers(String s){
        String numbers = "";

        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(s);

        while(m.find()){
            numbers = numbers + Integer.parseInt(m.group());
        }
        return Integer.parseInt(numbers);
    }

    private int getDestroyedHeatsinkCount() {
        int heatSinkCount=0;
        for (Component component : laComponents) {
            if (component.getName().replace(" ", "").contains(MTF_HEAT_SINK) && !component.isFunctioning())
                heatSinkCount++;
        }
        for (Component component : raComponents) {
            if (component.getName().replace(" ", "").contains(MTF_HEAT_SINK) && !component.isFunctioning())
                heatSinkCount++;
        }
        for (Component component : ctComponents) {
            if (component.getName().replace(" ", "").contains(MTF_HEAT_SINK) && !component.isFunctioning())
                heatSinkCount++;
        }
        for (Component component :  hComponents) {
            if (component.getName().replace(" ", "").contains(MTF_HEAT_SINK) && !component.isFunctioning())
                heatSinkCount++;
        }
        for (Component component : ltComponents) {
            if (component.getName().replace(" ", "").contains(MTF_HEAT_SINK) && !component.isFunctioning())
                heatSinkCount++;
        }
        for (Component component : rtComponents) {
            if (component.getName().replace(" ", "").contains(MTF_HEAT_SINK) && !component.isFunctioning())
                heatSinkCount++;
        }
        for (Component component : llComponents) {
            if (component.getName().replace(" ", "").contains(MTF_HEAT_SINK) && !component.isFunctioning())
                heatSinkCount++;
        }
        for (Component component : rlComponents) {
            if (component.getName().replace(" ", "").contains(MTF_HEAT_SINK) && !component.isFunctioning())
                heatSinkCount++;
        }
        return heatSinkCount;
    }

}
