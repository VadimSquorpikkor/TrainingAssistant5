package com.squorpikkor.trainingassistant5;

import android.text.format.DateFormat;

import com.squorpikkor.trainingassistant5.entity.WorkoutSet;

import java.util.ArrayList;
import java.util.Date;

public class Utils {

    /**Из String вида " 1692964666247 " возвращает строку "25/08/2023"*/
    public static String getDateStringFromLongString(String longStr) {
        long millisecond = Long.parseLong(longStr.trim());
        return DateFormat.format("dd/MM/yyyy", new Date(millisecond)).toString();
    }


}
