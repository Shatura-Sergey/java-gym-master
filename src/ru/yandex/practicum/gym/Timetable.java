package ru.yandex.practicum.gym;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.NavigableSet;



public class Timetable {

    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();
    protected HashMap<Coach, Integer> coachesCounter = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> trainingsForDay = timetable.get(dayOfWeek);
        if (trainingsForDay == null) {
            trainingsForDay = new TreeMap<>();
            timetable.put(dayOfWeek, trainingsForDay);
        }

        List<TrainingSession> trainingSessions = trainingsForDay.get(timeOfDay);
        if (trainingSessions == null) {
            trainingSessions = new ArrayList<>();
            trainingsForDay.put(timeOfDay, trainingSessions);
        }

        trainingSessions.add(trainingSession);
        Coach currentCoach = trainingSession.getCoach();
        coachesCounter.put(currentCoach, coachesCounter.getOrDefault(currentCoach, 0) + 1);
    }

    public NavigableSet<TimeOfDay> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        if (timetable.containsKey(dayOfWeek)) {
            return timetable.get(dayOfWeek).navigableKeySet();
        } else {
            return new TreeMap<TimeOfDay, ArrayList<TrainingSession>>().navigableKeySet();
        }
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (timetable.containsKey(dayOfWeek)) {
            TreeMap<TimeOfDay, List<TrainingSession>> treeMap = timetable.get(dayOfWeek);
            if (treeMap.containsKey(timeOfDay)) {
                return treeMap.get(timeOfDay);
            }
        }
        return new ArrayList<>();
    }

    public List<Map.Entry<Coach, Integer>> getCountByCoaches() {
        List<Map.Entry<Coach, Integer>> result = new ArrayList<>(coachesCounter.entrySet());
        result.sort((a, b) -> b.getValue().compareTo(a.getValue())); // Сортировка по количеству тренировок в порядке убывания
        return result;
    }
}
