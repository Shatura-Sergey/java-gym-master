package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;


public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        //Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());//Проверить, что за понедельник вернулось одно занятие// Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(thursdayChildTrainingSession.getTimeOfDay(), timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).getFirst());// Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        Assertions.assertEquals(thursdayAdultTrainingSession.getTimeOfDay(), timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).getLast());// Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());//Проверить, что за понедельник вернулось одно занятие
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size());//Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0)).size());//Проверить, что за понедельник в 13:00 вернулось одно занятие
    }

    @Test
    void testGetCountByCoachesNoLessons() {
        Timetable timetable = new Timetable();
        List<Map.Entry<String, Integer>> result = timetable.getCountByCoaches();
        Assertions.assertEquals(0, result.size());
    }

    @Test
    void testGetCountByCoachesAddCoach() {
        Timetable timetable = new Timetable();
        Group group = new Group("Футбол", Age.ADULT, 10);
        Coach coach = new Coach("Петров", "Иван", "Алексеевич");
        TrainingSession session = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(session);
        List<Map.Entry<String, Integer>> result = timetable.getCountByCoaches();
        Assertions.assertEquals("Петров Иван Алексеевич", result.get(0).getKey());
    }

    @Test
    void testGetCountByCoachesFourLessons() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Сидоров", "Вадим", "Геннадьевич");
        Coach coach2 = new Coach("Кругов", "Стас", "Владимирович");

        Group group1 = new Group("Футбол", Age.ADULT, 120);
        Group group2 = new Group("Плавание", Age.ADULT, 70);
        Group group3 = new Group("Хоккей", Age.ADULT, 120);
        Group group4 = new Group("Стрельба по тарелкам", Age.ADULT, 50);

        TrainingSession session1 = new TrainingSession(group1, coach1, DayOfWeek.MONDAY, new TimeOfDay(12, 0));
        TrainingSession session2 = new TrainingSession(group2, coach1, DayOfWeek.THURSDAY, new TimeOfDay(18, 0));
        TrainingSession session3 = new TrainingSession(group3, coach2, DayOfWeek.THURSDAY, new TimeOfDay(15, 0));
        TrainingSession session4 = new TrainingSession(group4, coach2, DayOfWeek.FRIDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);
        timetable.addNewTrainingSession(session4);

        List<TrainingSession> fridaySessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.FRIDAY, new TimeOfDay(20, 0));
        Assertions.assertTrue(fridaySessions.contains(session4));
    }
}

