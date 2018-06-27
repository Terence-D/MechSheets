/*
 * Copyright (c) 2018
 * Terry Doerksen
 * https://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package ca.coffeeshopstudio.meksheets.models;

/**
 *
 */
public enum Locations {
    head(0),
    centerTorso(1),
    leftTorso(2),
    rightTorso(3),
    leftArm(4),
    rightArm(5),
    leftLeg(6),
    rightLeg(7);

    private final int id;
    Locations(int id) { this.id = id; }
    public int getValue() { return id; }
}
