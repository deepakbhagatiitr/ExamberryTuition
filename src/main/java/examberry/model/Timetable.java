package examberry.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Timetable {
    private List<Lesson> lessons;

    public Timetable() {
        lessons = new ArrayList<>();
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public List<Lesson> getLessons() {
        return new ArrayList<>(lessons);
    }

    public List<Lesson> getLessonsByDate(LocalDate date) {
        return lessons.stream()
                .filter(l -> l.getDateTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    public List<Lesson> getLessonsBySubject(Subject subject) {
        return lessons.stream()
                .filter(l -> l.getSubject() == subject)
                .collect(Collectors.toList());
    }
}