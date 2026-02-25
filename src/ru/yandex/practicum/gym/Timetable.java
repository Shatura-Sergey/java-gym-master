package ru.yandex.practicum.gym;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.NavigableSet;


public class Timetable {

    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        if (timetable.containsKey(trainingSession.getDayOfWeek())) {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> oldTreeMap = timetable.get(trainingSession.getDayOfWeek());
            if (oldTreeMap.containsKey(trainingSession.getTimeOfDay())) {
                ArrayList<TrainingSession> trainingSessions = oldTreeMap.get(trainingSession.getTimeOfDay());
                trainingSessions.add(trainingSession);
                oldTreeMap.put(trainingSession.getTimeOfDay(), trainingSessions);
                timetable.put(trainingSession.getDayOfWeek(), oldTreeMap);
            } else {
                ArrayList<TrainingSession> newTrainingSessions = new ArrayList<>();
                newTrainingSessions.add(trainingSession);
                oldTreeMap.put(trainingSession.getTimeOfDay(), newTrainingSessions);
                timetable.put(trainingSession.getDayOfWeek(), oldTreeMap);
            }
        } else {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> newTreeMap = new TreeMap<>();
            ArrayList<TrainingSession> newTrainingSessions = new ArrayList<>();
            newTrainingSessions.add(trainingSession);
            newTreeMap.put(trainingSession.getTimeOfDay(), newTrainingSessions);
            timetable.put(trainingSession.getDayOfWeek(), newTreeMap);
        }
    }

    public NavigableSet<TimeOfDay> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        if (timetable.containsKey(dayOfWeek)) {
            return timetable.get(dayOfWeek).navigableKeySet();
        } else {
            return new TreeMap<TimeOfDay, ArrayList<TrainingSession>>().navigableKeySet();
        }
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (timetable.containsKey(dayOfWeek)) {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> treeMap = timetable.get(dayOfWeek);
            if (treeMap.containsKey(timeOfDay)) {
                return treeMap.get(timeOfDay);
            }
        }
        return new ArrayList<>();
    }

    public ArrayList<Map.Entry<String, Integer>> getCountByCoaches() {
        HashMap<String, Integer> coachers = new HashMap<>();

        for (TreeMap<TimeOfDay, ArrayList<TrainingSession>> dayMap : timetable.values()) {
            for (ArrayList<TrainingSession> sessions : dayMap.values()) {
                for (TrainingSession session : sessions) {
                    String coachName = session.getCoach().toString();
                    coachers.put(coachName, coachers.getOrDefault(coachName, 0) + 1);
                }
            }
        }
        ArrayList<Map.Entry<String, Integer>> lessons = new ArrayList<>(coachers.entrySet());
        lessons.sort((lesson1, lesson2) -> lesson2.getValue().compareTo(lesson1.getValue()));
        return lessons;
    }
}
