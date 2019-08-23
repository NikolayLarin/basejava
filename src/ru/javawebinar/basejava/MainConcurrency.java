package ru.javawebinar.basejava;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MainConcurrency {
    private static final int THREADS_NUMBER = 10000;
    private static int counter;
    private final AtomicInteger atomicCounter = new AtomicInteger();

    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"));

//    private static final Object LOCK = new Object();
//    private static final Lock LOCK = new ReentrantLock();
//    private static final Lock LOCK = new ReentrantReadWriteLock.ReadLock();

    public static void main(String[] args) throws InterruptedException {
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
                    System.out.println(DATE_FORMAT.get().format(new Date()));
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
//        System.out.println(counter);
//        System.out.println(MainConcurrency.counter);
        System.out.println(mainConcurrency.atomicCounter.get());


        counter = 0;
        mainConcurrency.atomicCounter.set(0);
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
                latch.countDown();
            });
            thread.start();
        }
        latch.await(10, TimeUnit.SECONDS);
//        System.out.println(MainConcurrency.counter);
        System.out.println(mainConcurrency.atomicCounter.get());

        counter = 0;
        mainConcurrency.atomicCounter.set(0);
        CountDownLatch latch2 = new CountDownLatch(THREADS_NUMBER);
//        ExecutorService executorService = Executors.newCachedThreadPool();
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(availableProcessors);
        CompletionService completionService = new ExecutorCompletionService(executorService);
        System.out.println("Available processors numbers: " + availableProcessors);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() -> {

                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
                latch2.countDown();
                return 5;
            });
//            completionService.poll();
//            System.out.println(future.isDone());
//            System.out.println(future.get());
        }
        latch2.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
//        System.out.println(MainConcurrency.counter);
        System.out.println(mainConcurrency.atomicCounter.get());
    }

    private void inc() {
        atomicCounter.incrementAndGet();
    }

//    private void inc() {
//        LOCK.lock();
//        try {
//            counter++;
//        } finally {
//            LOCK.unlock();
//        }
//    }

//    private synchronized void inc() {
//        counter++;
//    }

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