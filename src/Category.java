package src;

public enum Category {
    MENTAL_HEALTH("MENTAL-HEALTH"),
    ACADEMIC_SUPPORT("ACADEMIC-SUPPORT"),
    FINANCIAL_AID("FINANCIAL-AID");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    // Method to get the display name (e.g., "MENTAL-HEALTH")
    public String getDisplayName() {
        return displayName;
    }

    // Optional: Override toString() to return the display name by default
    // This makes it easier to print the desired format directly.
    @Override
    public String toString() {
        return displayName;
    }
} 