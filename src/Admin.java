package src;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Admin {
    private final String adminId;
    private final String name;
    private final String email;
    private final String role;

    public Admin(String adminId, String name, String email, String role) {
        this.adminId = adminId;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Getters
    public String getAdminId() { return adminId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }

    // Approve an appointment
    public boolean approveAppointment(Appointment appointment) {
        if (isValidAppointment(appointment, AppointmentStatus.SCHEDULED)) {
            appointment.setStatus(AppointmentStatus.APPROVED);
            logAction("approved", appointment);
            return true;
        }
        return false;
    }

    // Cancel an appointment
    public boolean cancelAppointment(Appointment appointment) {
        if (isValidAppointment(appointment, AppointmentStatus.CANCELLED)) {
            appointment.setStatus(AppointmentStatus.CANCELLED);
            logAction("cancelled", appointment);
            return true;
        }
        return false;
    }

    // Reschedule an appointment
    public boolean rescheduleAppointment(Appointment appointment, LocalDateTime newDateTime) {
        if (isValidAppointment(appointment, AppointmentStatus.CANCELLED)) {
            appointment.setDateTime(newDateTime);
            logAction("rescheduled", appointment);
            return true;
        }
        return false;
    }

    // Filter appointments by category
    public List<Appointment> getAppointmentsByCategory(List<Appointment> allAppointments, Category category) {
        List<Appointment> filteredAppointments = new ArrayList<>();
        for (Appointment appointment : allAppointments) {
            if (appointment.getCategory() == category) {
                filteredAppointments.add(appointment);
            }
        }
        return filteredAppointments;
    }

    // Generate appointment statistics
    public String getAppointmentStatistics(List<Appointment> allAppointments) {
        int total = allAppointments.size();
        int scheduled = 0, approved = 0, completed = 0, cancelled = 0;

        for (Appointment appointment : allAppointments) {
            switch (appointment.getStatus()) {
                case SCHEDULED -> scheduled++;
                case APPROVED -> approved++;
                case COMPLETED -> completed++;
                case CANCELLED -> cancelled++;
            }
        }

        return String.format("""
            Appointment Statistics:
            Total: %d | Scheduled: %d | Approved: %d | Completed: %d | Cancelled: %d
            """, total, scheduled, approved, completed, cancelled);
    }

    // Helper: Check if an appointment is valid for an action
    private boolean isValidAppointment(Appointment appointment, AppointmentStatus invalidStatus) {
        return appointment != null && appointment.getStatus() != invalidStatus;
    }

    // Helper: Log admin actions
    private void logAction(String action, Appointment appointment) {
        System.out.printf("Appointment %d %s by admin %s%n", 
                          appointment.getAppointmentId(), action, name);
    }

    @Override
    public String toString() {
        return String.format("Admin{id='%s', name='%s', email='%s', role='%s'}", 
                              adminId, name, email, role);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof Admin admin && adminId.equals(admin.adminId));
    }

    @Override
    public int hashCode() {
        return adminId.hashCode();
    }
}