package de.ait.javalessons.homeworks.homework_i2;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

abstract class Person {

    private final String name;
    private final String surname;
    private final LocalDate dateOfBirth;
    private final String id;

    Faker faker = new Faker();

    public Person() {
        this.id = faker.idNumber().valid();
        this.name = faker.name().firstName();
        this.surname = faker.name().lastName();
        this.dateOfBirth = convertToLocalDate(faker.date().birthday());
    }

    public Person(String name, String surname, LocalDate dateOfBirth) {
        this.id = faker.idNumber().valid();
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }

    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    abstract void registrationOnFlight();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age = " + getAge() +
                ", id='" + id + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public String getId() {
        return id;
    }
}
