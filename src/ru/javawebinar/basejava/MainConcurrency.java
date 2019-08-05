package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    private static final int THREADS_NUMBER = 10000;
    private static int counter;
    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                super.run();
                System.out.println(getName() + ", " + getState());
                throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(() -> System.out.println(Thread.currentThread().getName() +
                ", " + Thread.currentThread().getState())).start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
//                    inc();
                }
            });
            thread.start();
            threads.add(thread);
//            thread.join();
        }

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

//        Thread.sleep(500);
        System.out.println(counter);
        System.out.println(MainConcurrency.counter);
    }

    private synchronized void inc() {
        counter++;
    }

//    private synchronized void inc() {
//        synchronized (this /*means lock to new MainConcurrency() in main*/)
//        counter++;
//                wait();
//                readFile
//                notify(); notifyAll();
//    }

//        private static void inc() {
//        double a = Math.sin(17.5);
//        synchronized (LOCK) {
//            counter++;
//        }
//    }

//    private static /*this synchronized*/ synchronized void inc() {
//        /*means*/ synchronized (MainConcurrency.class) {
//            double a = Math.sin(17.5);
//            counter++;
//        }
//    }
}