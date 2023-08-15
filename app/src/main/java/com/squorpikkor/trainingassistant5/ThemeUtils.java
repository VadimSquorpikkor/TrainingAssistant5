package com.squorpikkor.trainingassistant5;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Класс для управлением темами приложения: смена и сохранение
 *
 * <p>Version 1.06
 *
 * <p>
 * <ul>
 *     <li>v-1: исправлен getPrefInt
 *     <li>v-2: исправлен onActivityCreateSetTheme (не было sTheme = theme, при старте приложения getTheme всегда выдавал 0)
 *     <li>1.02: добавлен changeTheme
 *     <li>1.03: исправлено: была перепутана тема по умолчанию
 *     <li>1.04: переделана логика с условной (if else) на безусловную. Стало сильно проще: новые темы добавляются простым добавлением идентификатора в массив; на разных приложениях различие будет только в массиве идентификаторов
 *     <li>1.05: для getPrefInt сделан механизм получения переменной сохраненной как в int, так и в string
 *     <li>1.06: DEF_THEME
 * <ul/>
 *
 * <p>Не забыть добавить в манифест: (application) android:theme="@style/Theme.ATDRSmart"
 * */
@SuppressWarnings("unused")
public class ThemeUtils {
    private static int sTheme;
    public static final String PREF_THEME = "theme";

    static final int DEF_THEME = 0;

    static int[] themes = new int[]{
            R.style.Theme_TrainingAssistant5_Dark
    };

    /**Для варианта смены темы из активити (по тапу по кнопке, например)*/
    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        savePref(PREF_THEME, sTheme);
        activity.recreate();
    }

    /**Для варианта смены темы из настроек*/
    public static void changeToThemePref(Activity activity, int theme) {
        sTheme = theme;
        activity.recreate();
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        sTheme = getPrefInt(PREF_THEME, DEF_THEME);
        if (sTheme>=themes.length) sTheme=0;
        activity.setTheme(themes[sTheme]);
    }

    public static int getTheme() {
        return sTheme;
    }

    /**Изменить тему: светлую, если сейчас тёмная, и наоборот (если тем много смена идет по кругу)*/
    public static void changeTheme(Activity activity) {
        sTheme++;
        if (sTheme>=themes.length) sTheme=0;
        changeToTheme(activity, sTheme);
    }

//--------------------------------------------------------------------------------------------------

    static SharedPreferences mPrefManager = PreferenceManager.getDefaultSharedPreferences(App.getContext());

    public static void savePref(String key, int param) {
        mPrefManager.edit().putInt(key, param).apply();
    }

    public static int getPrefInt(String key, int defValue) {
        if (mPrefManager.contains(key)) {
            try {
                return mPrefManager.getInt(key, defValue);
            } catch (ClassCastException e) {
                return Integer.parseInt(mPrefManager.getString(key, String.valueOf(defValue)));
            }
        }
        return defValue;
    }

}

