package by.itechartgroup.shirochina.anastasiya;

public enum Categories {
    ACTION("Action"),
    ROLE_PLAYING ("Role-Playing"),
    STRATEGY ("Strategy"),
    ADVENTURE ("Adventure"),
    SIMULATION ("Simulation"),
    SPORTS_AND_RACING ("Sports & Racing");
    private final String value;
    Categories(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
