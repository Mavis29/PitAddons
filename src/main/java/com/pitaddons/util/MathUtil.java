package com.pitaddons.util;

public class MathUtil {
    public static float approximate(float num, float app) {
        return Math.round(num / app) * app;
    }
}
