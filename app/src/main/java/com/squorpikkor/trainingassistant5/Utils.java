package com.squorpikkor.trainingassistant5;

import android.text.format.DateFormat;

import com.squorpikkor.trainingassistant5.entity.Event;
import com.squorpikkor.trainingassistant5.entity.WorkoutSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Utils {

    /**Из String вида " 1692964666247 " возвращает строку "25/08/2023"*/
    public static String getDateStringFromLongString(String longStr) {
        long millisecond = Long.parseLong(longStr.trim());
        return DateFormat.format("dd/MM/yyyy", new Date(millisecond)).toString();
    }

    private static class MarksComparator implements Comparator<WorkoutSet> {
        @Override
        public int compare(WorkoutSet w1, WorkoutSet w2) {
            if (w1.getWeight() == w2.getWeight()) {
                if (w1.getCount() == w2.getCount()) return 0;
                else if (w1.getCount() > w2.getCount()) return 1;
                else return -1;
            } else if (w1.getWeight() > w2.getWeight())
                return 1;
            else
                return -1;
        }
    }


    /**хуже чем в прошлый раз*/
    public static final int WORSE = 0;
    /**как в прошлый раз*/
    public static final int SAME = 1;
    /**лучше, чем в прошлый, но не рекорд*/
    public static final int BETTER = 2;
    /**новый рекорд, но с прежним весом*/
    public static final int BEST = 4;
    /**новый вес*/
    public static final int BEST_NEW_WEIGHT = 6;

    private static ArrayList<WorkoutSet> sortWorkout(ArrayList<WorkoutSet> workout) {
        ArrayList<WorkoutSet> res = new ArrayList<>(workout);
        Collections.sort(res, new MarksComparator());
        return res;
    }

//    int compareTwoWorkout(WorkoutSet w1, WorkoutSet w2) {
//        if (w1.getWeight() == w2.getWeight()) {
//            if (w1.getCount() == w2.getCount()) return 0;
//            else if (w1.getCount() > w2.getCount()) return 1;
//            else return -1;
//        } else if (w1.getWeight() > w2.getWeight())
//            return 1;
//        else
//            return -1;
//    }

    /**возвращает WORSE || SAME || BETTER || BEST_NEW_WEIGHT */
    static int compareResult(ArrayList<WorkoutSet> w1, ArrayList<WorkoutSet> w2){
        //todo
        return 0;
    }

    public static int compareWorkouts(ArrayList<WorkoutSet> best, ArrayList<WorkoutSet> last, ArrayList<WorkoutSet> current){
        Collections.sort(best, new MarksComparator());
        Collections.sort(last, new MarksComparator());
        Collections.sort(current, new MarksComparator());

        if (compareResult(current, best)==BEST_NEW_WEIGHT) return BEST_NEW_WEIGHT;
        if (compareResult(current, best)==BETTER) return BEST;
        else {
            if (compareResult(current, last)==BEST_NEW_WEIGHT||
                    compareResult(current, last)==BETTER) return BETTER;
            else return compareResult(current, last);
        }
    }

//    public static int compareWorkouts(ArrayList<WorkoutSet> best, ArrayList<WorkoutSet> last, ArrayList<WorkoutSet> current) {
//        Collections.sort(best, new MarksComparator());
//        Collections.sort(last, new MarksComparator());
//        Collections.sort(current, new MarksComparator());
//
//        for (int i = 0; i < current.size(); i++) {
//            WorkoutSet b = best.get(i);
//            WorkoutSet c = current.get(i);
//            WorkoutSet l = last.get(i);
//            if (i==0 && c.getWeight()>b.getWeight()) return BEST_NEW_WEIGHT;
//            if (c.getWeight()>b.getWeight()) return BEST;
//            if (c.getWeight() == b.getWeight()) {
//                if (c.getCount()>b.getCount()) return BEST;
//                if (c.getCount() < b.getCount()) {
//                    //c last
//                    if (c.getWeight())
//                }
//            } else if (c.getWeight() < b.getWeight()) {
//                //c last
//            }
//
//        }
//    }

}
