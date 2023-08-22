package com.squorpikkor.trainingassistant5.entity;

/**Упражнение как абстрактное понятие (становая, бег, жим и т.д.):
 *
 * <p>Predefined: можно выбрать список выбранных упражнений для тренировки. Списков может быть
 * несколько (для разных тренировок). Из этого списка потом получаем список {@link Event}
 * Для получения списка по умолчанию в {@link Exercise} хранится int значение, которое есть сумма
 * номеров списков (каждый список — степень 2): 1 — это первый, 2 — второй, 4 — третий,
 * 6 — третий и второй, 7 — 1-й, 2-й и 3-й и т.д.
 * */
public class Exercise {
//    Exercise - id, trainingId, name, bestEventId, lastEventId

    private final String id;
    private final String name;
    private final String bestEventId;
    private final String lastEventId;

    private boolean isChecked;
    private int predefinedTo;

    public Exercise(String id, String name, String bestEventId, String lastEventId, int predefined) {
        this.id = id;
        this.name = name;
        this.bestEventId = bestEventId;
        this.lastEventId = lastEventId;
        this.predefinedTo = predefined;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBestEventId() {
        return bestEventId;
    }

    public String getLastEventId() {
        return lastEventId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getPredefinedTo() {
        return predefinedTo;
    }

    public void setPredefinedTo(int predefinedTo) {
        this.predefinedTo = predefinedTo;
    }

}
