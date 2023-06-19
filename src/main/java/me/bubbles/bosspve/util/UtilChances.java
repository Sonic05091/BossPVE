package me.bubbles.bosspve.util;

public class UtilChances {

    public static boolean rollTheDice(int min, int max, int below) {
        return Math.random() * (max - min)<=below;
    }

}
