package de.ait.javalessons.homeworks.homework_i2;

import lombok.Getter;

@Getter
public enum Role {
    PILOT("pilot"),
    STEWART("stewart"),
    TECHNIKER("techniker"),
    ADMINISTRATOR("administrator"),
    SALES_AGENT("sales agent");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    //Приведение значения из String в Role
    public static Role fromStringRole(String role) {
        if (role.trim().equalsIgnoreCase("pilot")) {
            return Role.PILOT;
        } else if (role.equals("stewart")) {
            return Role.STEWART;
        } else if (role.equals("techniker")) {
            return Role.TECHNIKER;
        } else if (role.equals("administrator")) {
            return Role.ADMINISTRATOR;
        } else {
            return Role.SALES_AGENT;
        }
    }

}
