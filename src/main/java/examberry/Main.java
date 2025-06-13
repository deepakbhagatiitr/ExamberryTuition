package examberry;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import examberry.model.Booking;
import examberry.model.Lesson;
import examberry.model.Status;
import examberry.model.Student;
import examberry.model.Subject;
import examberry.model.TimeSlot;
import examberry.model.Timetable;
import examberry.service.BookingService;
import examberry.service.ReportGenerator;
import examberry.util.TimetableGenerator;

public class Main {
    public static void main(String[] args) {
        BookingService bookingService = new BookingService();
        ReportGenerator reportGenerator = new ReportGenerator(bookingService);

        Timetable timetable = TimetableGenerator.generateTimetable(
                LocalDate.of(2025, 6, 7), 8);
        bookingService.setTimetable(timetable);

        preloadStudents(bookingService);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nExamberry Tuition Centre");
            System.out.println("1. Book Lesson");
            System.out.println("2. Reschedule Booking");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Check In");
            System.out.println("5. Submit Review");
            System.out.println("6. Generate Per-Lesson Report");
            System.out.println("7. Generate Income Report");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid option. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    handleBooking(scanner, bookingService);
                    break;
                case 2:
                    handleReschedule(scanner, bookingService);
                    break;
                case 3:
                    handleCancel(scanner, bookingService);
                    break;
                case 4:
                    handleCheckIn(scanner, bookingService);
                    break;
                case 5:
                    handleReview(scanner, bookingService);
                    break;
                case 6:
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    try {
                        LocalDate date = LocalDate.parse(scanner.nextLine());
                        System.out.println(reportGenerator.generatePerLessonReport(date));
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Use YYYY-MM-DD.");
                    }
                    break;
                case 7:
                    System.out.println(reportGenerator.generateIncomeReport());
                    break;
                case 8:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void preloadStudents(BookingService service) {
        service.addStudent(new Student(
                "Alice Smith", "F", LocalDate.of(2010, 5, 15),
                "123 High St", "Parent: 555-0101"));
        service.addStudent(new Student(
                "Bob Johnson", "M", LocalDate.of(2011, 3, 22),
                "456 Elm St", "Parent: 555-0102"));
        service.addStudent(new Student(
                "Clara Lee", "F", LocalDate.of(2010, 7, 10),
                "789 Oak St", "Parent: 555-0103"));
        service.addStudent(new Student(
                "David Brown", "M", LocalDate.of(2011, 1, 5),
                "321 Pine St", "Parent: 555-0104"));
        service.addStudent(new Student(
                "Emma Wilson", "F", LocalDate.of(2010, 9, 20),
                "654 Maple St", "Parent: 555-0105"));
        service.addStudent(new Student(
                "Frank Davis", "M", LocalDate.of(2011, 4, 15),
                "987 Cedar St", "Parent: 555-0106"));
        service.addStudent(new Student(
                "Grace Kim", "F", LocalDate.of(2010, 11, 30),
                "147 Birch St", "Parent: 555-0107"));
        service.addStudent(new Student(
                "Henry Patel", "M", LocalDate.of(2011, 2, 25),
                "258 Willow St", "Parent: 555-0108"));
        service.addStudent(new Student(
                "Isabel Garcia", "F", LocalDate.of(2010, 6, 12),
                "369 Spruce St", "Parent: 555-0109"));
        service.addStudent(new Student(
                "James Nguyen", "M", LocalDate.of(2011, 8, 8),
                "741 Ash St", "Parent: 555-0110"));
    }

    private static void handleBooking(Scanner scanner, BookingService service) {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter subject (ENGLISH, MATHS, VERBAL_REASONING, NON_VERBAL_REASONING): ");
        try {
            Subject subject = Subject.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Enter date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter slot (MORNING, AFTERNOON): ");
            TimeSlot slot = TimeSlot.valueOf(scanner.nextLine().toUpperCase());

            Student student = service.getStudentByName(name);
            Lesson lesson = service.getLesson(subject, date, slot);
            if (student != null && lesson != null) {
                Booking booking = service.bookLesson(student, lesson);
                System.out.println(booking != null ? "Booking successful!" : "Booking failed.");
            } else {
                System.out.println("Invalid student or lesson.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use YYYY-MM-DD.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid subject or slot.");
        }
    }

    private static void handleReschedule(Scanner scanner, BookingService service) {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter current lesson date (YYYY-MM-DD): ");
        try {
            LocalDate currentDate = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter current lesson subject: ");
            Subject subject = Subject.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Enter current slot (MORNING, AFTERNOON): ");
            TimeSlot currentSlot = TimeSlot.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Enter new lesson date (YYYY-MM-DD): ");
            LocalDate newDate = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter new slot (MORNING, AFTERNOON): ");
            TimeSlot newSlot = TimeSlot.valueOf(scanner.nextLine().toUpperCase());

            Student student = service.getStudentByName(name);
            Lesson currentLesson = service.getLesson(subject, currentDate, currentSlot);
            Lesson newLesson = service.getLesson(subject, newDate, newSlot);
            if (student != null && currentLesson != null && newLesson != null) {
                Booking booking = student.getBookings().stream()
                        .filter(b -> b.getLesson().equals(currentLesson) && b.getStatus() == Status.BOOKED)
                        .findFirst().orElse(null);
                if (booking != null && service.rescheduleBooking(booking, newLesson)) {
                    System.out.println("Reschedule successful!");
                } else {
                    System.out.println("Reschedule failed.");
                }
            } else {
                System.out.println("Invalid student or lesson.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use YYYY-MM-DD.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid subject or slot.");
        }
    }

    private static void handleCancel(Scanner scanner, BookingService service) {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter lesson date (YYYY-MM-DD): ");
        try {
            LocalDate date = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter lesson subject: ");
            Subject subject = Subject.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Enter slot (MORNING, AFTERNOON): ");
            TimeSlot slot = TimeSlot.valueOf(scanner.nextLine().toUpperCase());

            Student student = service.getStudentByName(name);
            Lesson lesson = service.getLesson(subject, date, slot);
            if (student != null && lesson != null) {
                Booking booking = student.getBookings().stream()
                        .filter(b -> b.getLesson().equals(lesson) && b.getStatus() == Status.BOOKED)
                        .findFirst().orElse(null);
                if (booking != null && service.cancelBooking(booking)) {
                    System.out.println("Cancellation successful!");
                } else {
                    System.out.println("Cancellation failed.");
                }
            } else {
                System.out.println("Invalid student or lesson.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use YYYY-MM-DD.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid subject or slot.");
        }
    }

    private static void handleCheckIn(Scanner scanner, BookingService service) {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter lesson date (YYYY-MM-DD): ");
        try {
            LocalDate date = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter lesson subject: ");
            Subject subject = Subject.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Enter slot (MORNING, AFTERNOON): ");
            TimeSlot slot = TimeSlot.valueOf(scanner.nextLine().toUpperCase());

            Student student = service.getStudentByName(name);
            Lesson lesson = service.getLesson(subject, date, slot);
            if (student != null && lesson != null && service.checkIn(student, lesson)) {
                System.out.println("Check-in successful!");
            } else {
                System.out.println("Check-in failed.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use YYYY-MM-DD.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid subject or slot.");
        }
    }

    private static void handleReview(Scanner scanner, BookingService service) {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter lesson date (YYYY-MM-DD): ");
        try {
            LocalDate date = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter lesson subject: ");
            Subject subject = Subject.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Enter slot (MORNING, AFTERNOON): ");
            TimeSlot slot = TimeSlot.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Enter review text: ");
            String text = scanner.nextLine();
            System.out.print("Enter rating (1-5): ");
            int rating;
            try {
                rating = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid rating. Enter a number between 1 and 5.");
                scanner.nextLine();
                return;
            }

            Student student = service.getStudentByName(name);
            Lesson lesson = service.getLesson(subject, date, slot);
            if (student != null && lesson != null) {
                try {
                    service.submitReview(student, lesson, text, rating);
                    System.out.println("Review submitted!");
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid rating: " + e.getMessage());
                }
            } else {
                System.out.println("Invalid student or lesson.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use YYYY-MM-DD.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid subject or slot.");
        }
    }
}
