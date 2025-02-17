package de.ait.javalessons.homeworks.homework_i2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Plane {

    private static final Logger LOGGER = LoggerFactory.getLogger(Plane.class);

    private String modelOfPlane;
    private int pilotsQuantity;
    private int stewartsQuantity;
    private int passangersQuantity;

    public Plane(String modelOfPlane, int pilotsQuantity, int stewartsQuantity, int passangersQuantity) {
        this.modelOfPlane = modelOfPlane;
        this.pilotsQuantity = pilotsQuantity;
        this.stewartsQuantity = stewartsQuantity;
        this.passangersQuantity = passangersQuantity;
    }

    //Взлет
    public void takeoff() {
        LOGGER.info("The plane took off");
    }

    //Посадка
    public void landing() {
        LOGGER.info("The plane landed");
    }

    public String getModelOfPlane() {
        return modelOfPlane;
    }

    public int getPilotsQuantity() {
        return pilotsQuantity;
    }

    public int getStewartsQuantity() {
        return stewartsQuantity;
    }

    public int getPassangersQuantity() {
        return passangersQuantity;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "modelOfPlane='" + modelOfPlane + '\'' +
                ", pilotsQuantity=" + pilotsQuantity +
                ", stewartsQuantity=" + stewartsQuantity +
                ", passangersQuantity=" + passangersQuantity +
                '}';
    }
}
