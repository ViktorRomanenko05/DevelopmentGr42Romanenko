package de.ait.javalessons.homeworks.homework_i1.home_work_1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class UseArrayProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(UseArrayProcessor.class);

    public static void main(String[] args) throws InterruptedException {
        ArrayProcessor arrayProcessor = new ArrayProcessor();

        //Создаем потоки
        Thread thread1 = new Thread(arrayProcessor::firstHalfProcessing);
        Thread thread2 = new Thread(arrayProcessor::lastHalfProcessing);
        thread1.setName("1st Half Processing Thread");
        thread2.setName("2nd Half Processing Thread");

        //Запускаем потоки и однопоточную обработку
        thread1.start();
        thread2.start();
        arrayProcessor.oneThreadProcessing();

        //Ожидаем завершения работы потоков
        thread1.join();
        thread2.join();

        //Логируем и сравниваем результаты
        LOGGER.info("One thread result: {}", arrayProcessor.getResultOneThread());
        LOGGER.info("Two threads result: {}", arrayProcessor.getMultiThreadResult());
    }
}

//Вывод: в данной задаче и при текущей реализации однопоточная обработка оказалась эффективнее.
