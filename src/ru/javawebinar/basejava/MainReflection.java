package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume("uuid_1", "name1");
        System.out.println(resume);
        System.out.println(resume.getClass());
        System.out.println(resume.getClass().getDeclaredFields()[0] + "\n--------------------");
        Field field = resume.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(resume));
        field.set(resume, "new_uuid1");
        System.out.println(resume + "\n--------------------");

        /*
         * First implementation toString() method via invoke reflection at Resume class
         */
        field.set(resume, "new_uuid2");
        int totalMethodsNumbers = resume.getClass().getDeclaredMethods().length;
        int toStringKey = -1;
        for (int i = 0; i < totalMethodsNumbers; i++) {
            String str = resume.getClass().getDeclaredMethods()[i].toString();
            if (str.contains("toString()")) {
                toStringKey = i;
            }
        }
        if (toStringKey == -1) {
            System.out.println("Error: toString() method not found in Resume class");
        } else {
            System.out.println("toString() method found in Resume class with index =" + " " + toStringKey);
        }
        System.out.println(resume.getClass().getDeclaredMethods()[toStringKey].invoke(resume) + "\n--------------------");

        /*
         * Second implementation toString() method via invoke reflection at Resume class
         */
        Method toString = resume.getClass().getDeclaredMethod("toString");
        System.out.println(toString.invoke(resume) + "\n--------------------");

        /*
         * Implementation toString() method via invoke reflection at Resume class by G.Kislin
         */
        Class<? extends Resume> resumeClass = resume.getClass();
        Method method = resumeClass.getMethod("toString");
        Object result = method.invoke(resume);
        System.out.println(result + "\n--------------------");
    }
}
