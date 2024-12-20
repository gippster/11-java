package task2;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class PoolExample {

    public static void main(String[] args) throws InterruptedException
    {

        // создаем пул для выполнения наших задач
        //   максимальное количество созданных задач - 3
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                // не изменяйте эти параметры
                3, 3, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3));

        // сколько задач выполнилось
        AtomicInteger count = new AtomicInteger(0);

        // сколько задач выполняется
        AtomicInteger inProgress = new AtomicInteger(0);

        // отправляем задачи на выполнение
        for (int i = 0; i < 30;)
        {
            if (inProgress.get() < executor.getMaximumPoolSize())
            {
                inProgress.incrementAndGet();
                final int number = i++;
                Thread.sleep(10);

                System.out.println("creating #" + number);
                executor.submit(() -> {
                    System.out.println("start #" + number + ", in progress: " + executor.getActiveCount());
                    try {
                        // тут какая-то полезная работа
                        Thread.sleep(Math.round(1000 + Math.random() * 2000));
                    } catch (InterruptedException e) {
                        // ignore
                    }
                    System.out.println("end #" + number + ", in progress: " + executor.getActiveCount() + ", done tasks: " + count.incrementAndGet());
                    return null;
                });
            }
        }
        executor.shutdown();
    }
}