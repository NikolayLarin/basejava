package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume();
        System.out.println(r);
        System.out.println(r.getClass());
        System.out.println(r.getClass().getDeclaredFields()[0] + "\n--------------------");
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid1");
        System.out.println(r + "\n--------------------");

        /*
         * First implementation toString() method via invoke reflection at Resume class
         */
        field.set(r, "new_uuid2");
        int totalMethodsNumbers = r.getClass().getDeclaredMethods().length;
        int toStringKey = -1;
        for (int i = 0; i < totalMethodsNumbers; i++) {
            String str = r.getClass().getDeclaredMethods()[i].toString();
            if (str.contains("toString()")) {
                toStringKey = i;
            }
        }
        if (toStringKey == -1) {
            System.out.println("Error: toString() method not found in Resume class");
        } else {
            System.out.println("toString() method found in Resume class with index =" + " " + toStringKey);
        }
        System.out.println(r.getClass().getDeclaredMethods()[toStringKey].invoke(r) + "\n--------------------");

        /*
         * Second implementation toString() method via invoke reflection at Resume class
         */
        Method toString = r.getClass().getDeclaredMethod("toString");
        System.out.println(toString.invoke(r) + "\n--------------------");

        /*
         * Implementation toString() method via invoke reflection at Resume class by G.Kislin
         */
        Class<? extends Resume> resumeClass = r.getClass();
        Method method = resumeClass.getMethod("toString");
        Object result = method.invoke(r);
        System.out.println(result + "\n--------------------");
    }
}
