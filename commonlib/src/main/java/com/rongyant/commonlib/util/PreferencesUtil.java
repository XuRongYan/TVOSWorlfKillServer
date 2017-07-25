package com.rongyant.commonlib.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public final class PreferencesUtil {

    /**
     * clean all data
     */
    public static void reset(final Context ctx) {
        Editor edit = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
        edit.clear();
        edit.apply();
    }

    public static void remove(Context ctx, String... keys) {
        if (keys != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
            Editor editor = sharedPreferences.edit();
            for (String key : keys) {
                editor.remove(key);
            }
            editor.apply();
        }
    }

    public static boolean hasString(Context ctx, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        return sharedPreferences.contains(key);
    }

    public static void put(Context ctx, String key, boolean value) {
        putBoolean(ctx, key, value);
    }

    public static void put(Context ctx, String key, int value) {
        putInt(ctx, key, value);
    }

    public static void put(Context ctx, String key, long value) {
        putLong(ctx, key, value);
    }

    public static void put(Context ctx, String key, float value) {
        putFloat(ctx, key, value);
    }

    public static void put(Context ctx, String key, String value) {
        putString(ctx, key, value);
    }

    public static void put(Context ctx, String key, Serializable value) {
        putObject(ctx, key, value);
    }


    private static void putFloat(Context ctx, String key, float value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    private static void putString(Context ctx, String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private static void putLong(Context ctx, String key, long value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    private static void putBoolean(Context ctx, String key, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private static void putInt(Context ctx, String key, int value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private static void putObject(Context ctx, String key, Serializable value) {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            String valueBase64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            Editor editor = sharedPreferences.edit();
            editor.putString(key, valueBase64);
            editor.apply();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static int getInt(Context ctx, String key, int defValue) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getInt(key, defValue);
    }

    public static boolean getBoolean(Context ctx, String key, boolean defValue) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(key, defValue);
    }

    public static long getLong(Context ctx, String key, long defValue) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getLong(key, defValue);
    }

    public static float getFloat(Context ctx, String key, float defValue) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getFloat(key, defValue);
    }

    public static String getString(Context ctx, String key, String defValue) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getString(key, defValue);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T getSerializable(Context ctx, String key) {
        String objBase64 = PreferenceManager.getDefaultSharedPreferences(ctx).getString(key, null);
        if (objBase64 != null && !objBase64.equals("")) {
            try {
                byte[] base64 = Base64.decode(objBase64, Base64.DEFAULT);
                ByteArrayInputStream bais = new ByteArrayInputStream(base64);
                ObjectInputStream ois = new ObjectInputStream(bais);
                Object obj = ois.readObject();
                return (T) obj;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static SharedPreferences getPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

}

