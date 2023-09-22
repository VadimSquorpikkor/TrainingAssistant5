package com.squorpikkor.trainingassistant5.entity;

/**Упражнение как абстрактное понятие (становая, бег, жим и т.д.):
 *
 * <p>Predefined: можно выбрать список выбранных упражнений для тренировки. Списков может быть
 * несколько (для разных тренировок). Из этого списка потом получаем список {@link Event}
 * Для получения списка по умолчанию в {@link Exercise} хранится int значение, которое есть сумма
 * номеров списков (каждый список — степень 2): 1 — это первый, 2 — второй, 4 — третий,
 * 6 — третий и второй, 7 — 1-й, 2-й и 3-й и т.д.
 * */
public class Exercise extends BaseEntity {
//    Exercise - id, trainingId, name, bestEventId, lastEventId

    private String name;
    private String bestEventId;
    private String lastEventId;
    private String bestSet;//60*10 70*10 80*5 70*6
    private String lastSet;//60*10 70*10 80*3 70*6

    private boolean isChecked;
    private int predefinedTo;

    public Exercise(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBestEventId() {
        return bestEventId;
    }

    public void setBestEventId(String bestEventId) {
        this.bestEventId = bestEventId;
    }

    public String getLastEventId() {
        return lastEventId;
    }

    public void setLastEventId(String lastEventId) {
        this.lastEventId = lastEventId;
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

    public String getBestSet() {
        return bestSet;
    }

    public void setBestSet(String bestSet) {
        this.bestSet = bestSet;
    }

    public String getLastSet() {
        return lastSet;
    }

    public void setLastSet(String lastSet) {
        this.lastSet = lastSet;
    }
}
