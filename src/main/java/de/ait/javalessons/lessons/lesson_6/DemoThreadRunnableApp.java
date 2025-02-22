package de.ait.javalessons.lessons.lesson_6;

public class DemoThreadRunnableApp {
    public static void main(String[] args) {
        DemoThreadRunnable demoThreadRunnable = new DemoThreadRunnable("Demo Thread - 1");
        demoThreadRunnable.start();

        DemoThreadRunnable demoThreadRunnable2 = new DemoThreadRunnable("Demo Thread - 2");
        demoThreadRunnable2.start();
    }
}
