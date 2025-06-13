package examberry.util;

import examberry.model.Lesson;
import examberry.model.TimeSlot;
import examberry.model.Subject;

import java.time.LocalDateTime;

public class LessonFactory {
    private static final double[] PRICES = { 50.0, 50.0, 40.0, 40.0 }; // English, Maths, VR, NVR

    public static Lesson createLesson(Subject subject, LocalDateTime dateTime, TimeSlot slot) {
        if (subject == null || dateTime == null || slot == null) {
            throw new IllegalArgumentException("Invalid lesson parameters");
        }
        return new Lesson(subject, dateTime, slot, PRICES[subject.ordinal()]);
    }
}