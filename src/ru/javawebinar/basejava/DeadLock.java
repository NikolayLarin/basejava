package ru.javawebinar.basejava;

/**
 * deadlock realization
 */
public class DeadLock {
    private static int counter;
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> deadLock(LOCK1, LOCK2));
        t1.start();

        Thread t2 = new Thread(() -> deadLock(LOCK2, LOCK1));
        t2.start();
    }

    private static void deadLock(Object lock1, Object lock2) {
        synchronized (lock1) {
            inc();
            System.out.println("Always print this, counter changed to  " + counter +
                    " in Thread: " + Thread.currentThread().getName());
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock2) {
                inc();
                System.out.println("Never print this");
            }
        }
    }

    private static void inc() {
        counter++;
    }
}