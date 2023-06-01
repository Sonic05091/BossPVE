package me.bubbles.bosspve.util;

public enum Messages {
    PREFIX("&a[&e&lGeoFind&a]"),
    PRIMARY_COLOR("&a"),
    SECONDARY_COLOR("&e"),
    NO_PERMS("&cYou do not have permission to do that! &4%node%");

    private final String value;
    Messages(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }

}
