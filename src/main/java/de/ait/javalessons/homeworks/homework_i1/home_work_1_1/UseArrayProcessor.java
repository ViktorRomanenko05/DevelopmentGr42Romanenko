package de.ait.javalessons.homeworks.homework_i1.home_work_1_1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UseArrayProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(UseArrayProcessor.class);

    public static void main(String[] args) throws InterruptedException {
        ArrayProcessor_1_1 arrayProcessor_1 = new ArrayProcessor_1_1();

        //Создаем потоки
        ExecutorService executor = Executors.newFixedThreadPool(2);

        //Запускаем потоки и однопоточную обработку

        executor.submit(arrayProcessor_1::firstHalfProcessing);
        executor.submit(arrayProcessor_1::lastHalfProcessing);
        arrayProcessor_1.oneThreadProcessing();

        //Ожидаем завершения работы потоков
        executor.shutdown();

        //Логируем и сравниваем результаты
        LOGGER.info("One thread result: " + arrayProcessor_1.getResultOneThread());
        LOGGER.info("Two threads result: " + arrayProcessor_1.getMultiThreadResult());
    }
}

//Вывод: в данной задаче и при текущей реализации однопоточная обработка оказалась эффективнее.
