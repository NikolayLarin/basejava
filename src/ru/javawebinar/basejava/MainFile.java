package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        File filePath = new File("D:\\Java_Projects\\TopJava.ru\\basejava\\.gitignore");

        File file = new File(".\\.gitignore");
        try {
            System.out.println(filePath.getCanonicalPath() + "  " + file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        File dir = new File ("./src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null){
            for (String name : list) {
                System.out.println(name);
            }
        }
    }
}
