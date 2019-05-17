package ru.javawebinar.basejava;

public class MainUtil {
    public static void main(String[] args) {
        System.out.println(Integer.valueOf(-1) == Integer.valueOf(-1)); // true
        System.out.println(Integer.valueOf(-1) == new Integer(-1)); // false
        int result = getInt();
        System.out.println(result);
    }

    private static Integer getInt() {
        return -1; // can'n be null
    }
}
