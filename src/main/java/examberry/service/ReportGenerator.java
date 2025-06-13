package examberry.service;

import examberry.model.Lesson;
import examberry.model.Subject;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportGenerator {
    private BookingService bookingService;
    
    public ReportGenerator(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    
    public String generatePerLessonReport(LocalDate date) {
        List<Lesson> lessons = bookingService.getLessons().stream()
            .filter(l -> l.getDateTime().toLocalDate().equals(date))
            .collect(Collectors.toList());
        
        StringBuilder report = new StringBuilder("Per-Lesson Report for " + date + "\n");
        for (Lesson lesson : lessons) {
            report.append(String.format(
                "Lesson: %s, %s, Students: %d, Avg Rating: %.2f\n",
                lesson.getSubject(), lesson.getSlot(),
                lesson.getStudentCount(), lesson.getAverageRating()
            ));
        }
        return report.toString();
    }
    
    public String generateIncomeReport() {
        Map<Subject, Double> incomeBySubject = bookingService.getLessons().stream()
            .collect(Collectors.groupingBy(
                Lesson::getSubject,
                Collectors.summingDouble(l -> l.getStudentCount() * l.getPrice())
            ));
        
        Subject topSubject = incomeBySubject.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
        
        StringBuilder report = new StringBuilder("Income Report\n");
        for (Map.Entry<Subject, Double> entry : incomeBySubject.entrySet()) {
            report.append(String.format(
                "%s: £%.2f\n", entry.getKey(), entry.getValue()
            ));
        }
        if (topSubject != null) {
            report.append("Highest Income: ").append(topSubject).append("\n");
        }
        return report.toString();
    }
}