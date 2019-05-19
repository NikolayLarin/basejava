package ru.javawebinar.basejava;

public class TestSingleton {
    private static TestSingleton instance;

    public static TestSingleton getInstance() {
        if (instance == null) {
            instance = new TestSingleton();
        }
        return instance;
    }

    private TestSingleton() {
    }

    public static void main(String[] args) {
        TestSingleton.getInstance().toString();
        Singleton instance = Singleton.valueOf("INSTANCE");
        System.out.println(instance.name() + "  " + instance.toString()
                + "  " + instance + "  ordinal: " + instance.ordinal());
    }

    public enum Singleton {
        INSTANCE
    }
}
