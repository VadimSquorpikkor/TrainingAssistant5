package com.squorpikkor.trainingassistant5;

import android.text.format.DateFormat;
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

    /**Сортирует по весу: наибольший вес — вначале. Если вес одинаковый — сортируется по количеству подходов*/
    private static class ExerciseComparator implements Comparator<WorkoutSet> {
        @Override
        public int compare(WorkoutSet w1, WorkoutSet w2) {
            if (w1.getWeight() == w2.getWeight()) {
                //noinspection UseCompareMethod
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
    public static final int BEST = 3;
    /**новый вес*/
    public static final int BEST_NEW_WEIGHT = 4;

    /**
     * Сравнивает две тренировки.
     * Важно: перед применением сравнения подходы в тренировке должны быть отсортированы
     * в {@link ExerciseComparator}
     * Правило такое: если у текущей тренировки самый большой вес > самого большого веса лучшей
     * тренировки, то это автоматом BEST_NEW_WEIGHT, дальше можно не сравнивать. Далее сравниваются
     * сумма всех подходов для следующего веса (веса отсортированы от большего к меньшему).
     * Выигрывает то упражнение, сумма подходов у которой для текущего веса больше. Если для этого
     * веса сумма совпадает, переходим к следующему весу и т.д. В случае, когда все суммы подходов
     * для всех весов у обоих тренировок совпадают, получается ничья
     *
     * @param current текущее упражнение
     * @param best упражнение для сравнения (прошлое либо лучшее)
     * @return WORSE || SAME || BETTER || BEST_NEW_WEIGHT
     */
    private static int compareResult(ArrayList<WorkoutSet> current, ArrayList<WorkoutSet> best){
        current = countCounts(current);//todo проверить не будет ли меняться входящий список снаружи
        best = countCounts(best);

        if (best.size()==0 || current.get(0).getWeight()>best.get(0).getWeight()) return BEST_NEW_WEIGHT;

        //Math.min(current.size(), best.size()) — т.е. если в лучшем упражнении 5 подходов, а в
        // текущем 3, то сравниваем только 3 подхода
        for (int i = 0; i < Math.min(current.size(), best.size()); i++) {
            if (current.get(i).getWeight()>best.get(i).getWeight()) return BETTER;
            if (current.get(i).getWeight()<best.get(i).getWeight()) return WORSE;
            if (current.get(i).getWeight() == best.get(i).getWeight()) {
                if (current.get(i).getCount()>best.get(i).getCount()) return BETTER;
                if (current.get(i).getCount()<best.get(i).getCount()) return WORSE;
                //если каунты равны — продолжаем (берём следующую пару)
            }
        }
        //если дошли до этой точки, а подходы в "лучшем" ещё остались, значит выиграла "лучшая"
        // тренировка, иначе — "текущая". Если кол-во подходов было одинаковым, значит "ничья",
        // т.е. веса и повторы в подходах оказались идентичными
        if (best.size()>current.size()) return WORSE;
        if (current.size()>best.size()) return BETTER;
        else return SAME;
    }

    /**Подходы с одинаковым весом объединяются в один подход, при этом повторы складываются.
     * Например: 60*10 60*8 70*8 80*5 -> 60*18 70*8 80*5*/
    private static ArrayList<WorkoutSet> countCounts(ArrayList<WorkoutSet> list) {
        ArrayList<WorkoutSet> res = new ArrayList<>();

        WorkoutSet countedSet = null;
        for (WorkoutSet set:list) {
            float w = set.getWeight();
            int c = set.getCount();
            if (countedSet==null || countedSet.getWeight()>w){
                countedSet = new WorkoutSet(w, c);
            } else {
                w += countedSet.getWeight();
                c += countedSet.getCount();
                countedSet = new WorkoutSet(w, c);
            }
            res.add(new WorkoutSet(countedSet.getWeight(), countedSet.getCount()));
        }
        return res;
    }

    /**Сравнивает текущую тренировку с прошлой и лучшей и выносит вердикт:
     *
     * @param current текущая тренировка
     * @param last прошлая
     * @param best лучшая
     * @return
     * <ul><li>WORSE — хуже чем в прошлый раз;
     *     <li>SAME — как в прошлый раз;
     *     <li>BETTER — лучше, чем в прошлый, но не рекорд;
     *     <li>BEST — новый рекорд, но с прежним весом;
     *     <li>BEST_NEW_WEIGHT — новый вес;
     * </ul>
     */
    public static int compareWorkouts(ArrayList<WorkoutSet> current, ArrayList<WorkoutSet> last, ArrayList<WorkoutSet> best){
        Collections.sort(best, new ExerciseComparator());
        Collections.sort(last, new ExerciseComparator());
        Collections.sort(current, new ExerciseComparator());

        if (compareResult(current, best)==BEST_NEW_WEIGHT) return BEST_NEW_WEIGHT;
        if (compareResult(current, best)==BETTER) return BEST;
        else {
            if (compareResult(current, last)==BEST_NEW_WEIGHT||
                    compareResult(current, last)==BETTER) return BETTER;
            else return compareResult(current, last);
        }
    }
}
