package src;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime; // Added for method stubs later

// Import Appointment now that it's defined
import src.Appointment;
import src.Category; // Added for method stubs later
import src.Feedback; // Added for method stubs later

public class Student {
    private String studentId;
    private String name;
    private List<Appointment> appointments;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.appointments = new ArrayList<>();
    }

    // Getters
    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public List<Appointment> getAppointments() {
        // Consider returning a copy to prevent external modification
        return new ArrayList<>(appointments);
    }

    // Method to add appointment (used by system/logic)
    public void addAppointment(Appointment appointment) {
        // Ensure appointment is not null and belongs to this student
        if (appointment != null && appointment.getStudent().equals(this)) {
            this.appointments.add(appointment);
        }
    }

    // --- Method Stubs for Student A features ---

    // a) Request Appointment (Logic might be better in CampusAssistSystem or ConsoleUI)
    // This method might just create an Appointment object or return necessary info
    // public Appointment requestAppointment(Category category, LocalDateTime dateTime) {
    //     // Actual creation and scheduling might happen elsewhere
    //     System.out.println("DEBUG: Student requesting appointment: " + category + " at " + dateTime);
    //     Appointment newAppt = new Appointment(this, category, dateTime);
    //     // It should be added to the main system list and then linked here
    //     // addAppointment(newAppt); // This might cause issues if system doesn't know
    //     return newAppt; // Return the potential appointment
    // }

    // b) View Appointments (Split into upcoming/past)
    public List<Appointment> getUpcomingAppointments() {
        List<Appointment> upcoming = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (Appointment appt : appointments) {
            if (appt.getDateTime().isAfter(now) && appt.getStatus() == AppointmentStatus.SCHEDULED) {
                upcoming.add(appt);
            }
        }
        // Optional: Sort by date
        upcoming.sort((a1, a2) -> a1.getDateTime().compareTo(a2.getDateTime()));
        return upcoming;
    }

    public List<Appointment> getPastAppointments() {
        List<Appointment> past = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (Appointment appt : appointments) {
            if (appt.getDateTime().isBefore(now) || appt.getStatus() == AppointmentStatus.COMPLETED || appt.getStatus() == AppointmentStatus.CANCELLED) {
                past.add(appt);
            }
        }
         // Optional: Sort by date descending
        past.sort((a1, a2) -> a2.getDateTime().compareTo(a1.getDateTime()));
        return past;
    }

    // c) Provide Feedback
    public Feedback provideFeedback(Appointment appointment, int rating, String comments) {
        if (appointment == null || !this.appointments.contains(appointment)) {
            System.out.println("Error: Cannot provide feedback for an unknown or invalid appointment.");
            return null;
        }
        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
             System.out.println("Error: Can only provide feedback for completed appointments.");
            return null;
        }
         if (appointment.getFeedback() != null) {
             System.out.println("Error: Feedback already provided for this appointment.");
            return null;
        }

        Feedback newFeedback = new Feedback(appointment, rating, comments);
        appointment.setFeedback(newFeedback); // Link feedback to appointment
        // Optional: Link appointment to feedback if needed
        // newFeedback.setAppointment(appointment); // Requires setter in Feedback
        System.out.println("Feedback submitted successfully for appointment " + appointment.getAppointmentId());
        return newFeedback;
    }


    @Override
    public String toString() {
        return "Student{" +
               "studentId='" + studentId + '\'' +
               ", name='" + name + '\'' +
               '}';
    }

    // equals and hashCode based on studentId for comparisons
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return java.util.Objects.equals(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(studentId);
    }
} 