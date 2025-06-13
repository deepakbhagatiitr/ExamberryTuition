package examberry.util;

import examberry.model.Lesson;
import examberry.model.Subject;
import examberry.model.Timetable;
import examberry.model.TimeSlot;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TimetableGenerator {
    public static Timetable generateTimetable(LocalDate startDate, int weeks) {
        Timetable timetable = new Timetable();

        for (int i = 0; i < weeks * 2; i++) {
            LocalDate date = startDate.plusDays(i * 7);
            for (Subject subject : Subject.values()) {
                timetable.addLesson(LessonFactory.createLesson(
                        subject,
                        LocalDateTime.of(date, java.time.LocalTime.of(9, 0)),
                        TimeSlot.MORNING));
                timetable.addLesson(LessonFactory.createLesson(
                        subject,
                        LocalDateTime.of(date, java.time.LocalTime.of(13, 0)),
                        TimeSlot.AFTERNOON));
            }
        }
        return timetable;
    }
}