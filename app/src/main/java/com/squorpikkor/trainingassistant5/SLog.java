package com.squorpikkor.trainingassistant5;

import android.util.Log;

import java.util.Arrays;

/**Вывод логов.
 * Чтобы отключить отображение ВСЕХ логов во ВСЕХ классах установить SHOW значение false
 * v 1.07*/
public class SLog {

    public static final String TAG = "sTAG";
    public static final boolean SHOW = true;

    public static void e(String s) {
        if(SHOW)Log.e(TAG, s);
    }

    public static void e(int i) {
        if(SHOW)e(""+i);
    }

    public static void e(double i) {
        if(SHOW)e(""+i);
    }

    public static void e(short i) {
        if(SHOW)e(""+i);
    }

    public static void e(float i) {
        if(SHOW)e(""+i);
    }

    public static void e(byte i) {
        if(SHOW)e(""+i);
    }

    public static void e(byte[] array) {
        if(SHOW)e(""+ Arrays.toString(array));
    }

    public static void arrAsHex(byte[] array) {
        if(SHOW)e(""+ byteArrayToHexString(array));
    }

    public static void e(String tag, String s) {
        if(SHOW)Log.e(tag, s);
    }

    public static void e(String tag, String s, Throwable s2) {
        if(SHOW)Log.e(tag, s, s2);
    }

    public static void i(String s) {
        if(SHOW)Log.i(TAG, s);
    }

    public static void i(String tag, String s) {
        if(SHOW)Log.i(tag, s);
    }

    public static void d(String tag, String s) {
        if(SHOW)Log.i(tag, s);
    }

    public static void arr(String prefix, String[] s) {
        if(SHOW)Log.e("", prefix + Arrays.toString(s));
    }

    public static void arr(String prefix, int[] s) {
        if(SHOW)Log.e("", prefix + Arrays.toString(s));
    }

    public static void arr(String prefix, float[] s) {
        if(SHOW)Log.e("", prefix + Arrays.toString(s));
    }

    public static void arr(String prefix, double[] s) {
        if(SHOW)Log.e("", prefix + Arrays.toString(s));
    }

    public static void arr(String prefix, byte[] s) {
        if(SHOW)Log.e("", prefix + Arrays.toString(s));
    }

    public static void arr(String[] s) {
        if(SHOW)Log.e("", Arrays.toString(s));
    }

    public static void arr(int[] s) {
        if(SHOW)Log.e("", Arrays.toString(s));
    }

    public static void arr(float[] s) {
        if(SHOW)Log.e("", Arrays.toString(s));
    }

    public static void arr(byte[] b) {
        if(SHOW)Log.e("", Arrays.toString(b));
    }

    public static void arr2(byte[] b) {
        String s = "";
        for (byte by:b) s+=by+" ";
        if(SHOW)Log.e("", s);
    }

    public static void arr(double[] s) {
        if(SHOW)Log.e("", Arrays.toString(s));
    }

    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(String.format("%02x", aByte));
            result.append(" ");
            // upper case
            // result.append(String.format("%02X", aByte));
        }
        return result.toString();
    }

    /**2 -> 00000010*/
    public static void toBinary(int i) {
        if(SHOW)Log.e(TAG, String.format("%8s", Integer.toBinaryString(i & 0xFF)).replace(' ', '0'));
    }

    public static void toBinary(long i) {
        if(SHOW)Log.e(TAG, String.format("%8s", Long.toBinaryString(i & 0xFF)).replace(' ', '0'));
    }

    public static void line() {
        if(SHOW)e("—————————————————————————————————————————————");
    }
}


