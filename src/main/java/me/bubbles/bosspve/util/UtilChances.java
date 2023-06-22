package me.bubbles.bosspve.util;

public class UtilChances {

    public static boolean rollTheDice(double min, double max, double below) {
        return Math.random() * (max - min)<=below;
    }

}
