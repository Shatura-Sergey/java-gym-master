package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final HashMap <DayOfWeek, TreeMap <TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        if (timetable.containsKey(trainingSession.getDayOfWeek())) {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> oldTreeMap = timetable.get(trainingSession.getDayOfWeek());
            if (oldTreeMap.containsKey(trainingSession.getTimeOfDay())) {
                ArrayList<TrainingSession> trainingSessions = oldTreeMap.get(trainingSession.getTimeOfDay());
                trainingSessions.add(trainingSession);
                oldTreeMap.put(trainingSession.getTimeOfDay(), trainingSessions);
                timetable.put(trainingSession.getDayOfWeek(), oldTreeMap);
            } else {
                TreeMap<TimeOfDay, ArrayList<TrainingSession>> newTreeMap = new TreeMap<>();
                ArrayList<TrainingSession> newTrainingSessions = new ArrayList<>();
                newTrainingSessions.add(trainingSession);
                newTreeMap.put(trainingSession.getTimeOfDay(), newTrainingSessions);
                timetable.put(trainingSession.getDayOfWeek(), newTreeMap);
            }
        } else {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> newTreeMap = new TreeMap<>();
            ArrayList<TrainingSession> newTrainingSessions = new ArrayList<>();
            newTrainingSessions.add(trainingSession);
            newTreeMap.put(trainingSession.getTimeOfDay(), newTrainingSessions);
            timetable.put(trainingSession.getDayOfWeek(), newTreeMap);
        }
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek); //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        return timetable.get(dayOfWeek).get(timeOfDay); //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }
}
