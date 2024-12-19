package task1;

import java.util.concurrent.atomic.AtomicInteger;

public class Lucky {
    static AtomicInteger x = new AtomicInteger(0);
    static AtomicInteger count = new AtomicInteger(0);

    static class LuckyThread extends Thread {
        @Override
        public void run() {
            int currentX;
            while ((currentX = x.incrementAndGet()) <= 999999) {
                if ((currentX % 10) + (currentX / 10) % 10 + (currentX / 100) % 10 ==
                        (currentX / 1000) % 10 + (currentX / 10000) % 10 + (currentX / 100000) % 10) {
                    System.out.println(currentX);
                    count.incrementAndGet();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new LuckyThread();
        Thread t2 = new LuckyThread();
        Thread t3 = new LuckyThread();
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("Total: " + count);
    }
}
