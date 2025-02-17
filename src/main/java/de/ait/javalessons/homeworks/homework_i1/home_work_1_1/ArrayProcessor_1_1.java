package de.ait.javalessons.homeworks.homework_i1.home_work_1_1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ArrayProcessor_1_1 {

    //Logger позволит подробнее отследить порядок и время вызова и выполнения методов
    private static final Logger LOGGER = LoggerFactory.getLogger(ArrayProcessor_1_1.class);

    private final int[] numbersArray = arrayFilling();
    private int resultOneThread;
    private int resultFirstHalf;
    private int resultLastHalf;

    //Для лучшей видимости разницы во времени выполнения расширим массив до 200M элементов
    //Метод для инициализации и заполнения массива
    private int[] arrayFilling() {
        int[] numbers = new int[20000000];
        numbers = Arrays.stream(numbers).map(element -> (int) (Math.random() * 10000001)).toArray();
        LOGGER.info("Array is filled");
        return numbers;
    }

    //Счетчик элементов массива, соответствующих условию в задании
    private int numbersCounter(int[] numbers) {
        return Arrays.stream(numbers).filter(element -> element % 21 == 0 && containsThreeCheck(element)).toArray().length;
    }

    //Метод для обработки массива одним потоком
    public void oneThreadProcessing() {
        resultOneThread = numbersCounter(numbersArray);
        LOGGER.info("One thread processing finished");
    }

    //Метод обработки первой половины массива
    public void firstHalfProcessing() {
        int[] firstNumbers = Arrays.stream(numbersArray).limit(numbersArray.length / 2).toArray();
        resultFirstHalf = numbersCounter(firstNumbers);
        LOGGER.info("first half processing finished");
    }

    //Метод обработки второй половины массива
    public void lastHalfProcessing() {
        int[] lastNumbers = Arrays.stream(numbersArray).skip(numbersArray.length / 2).toArray();
        resultLastHalf = numbersCounter(lastNumbers);
        LOGGER.info("last half processing finished");
    }

    //Метод для определения наличия цифры 3 в числе
    private boolean containsThreeCheck(int number) {
        while (number > 0) {
            if (number % 10 == 3) {
                return true;
            }
            number = number / 10;
        }
        return false;
    }

    //Геттеры для доступа к результату
    public int getResultOneThread() {
        return resultOneThread;
    }

    public int getMultiThreadResult() {
        return resultFirstHalf + resultLastHalf;
    }
}

