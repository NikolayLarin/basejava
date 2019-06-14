package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    private static int dirCounter;
    private static int fileCounter;

    public static void main(String[] args) {
        File testFilePath = new File("D:\\Java_Projects\\TopJava.ru\\basejava\\.gitignore");
        String filePath = ".\\.gitignore";
        File file = new File(filePath);
        try {
            System.out.println(testFilePath.getCanonicalPath() + "  " + file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        File dir = new File("./src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("--------------------");

        dirWalker("D:/Java_Projects/TopJava.ru/basejava");
        System.out.println("\nTotal directory passed: " + dirCounter +
                "\nTotal files listed: " + fileCounter);
    }

    private static void dirWalker(String filePath) {
        File dir = new File(filePath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println("Directory: " + file.getName());
                    dirCounter++;
                } else if (file.isFile()) {
                    System.out.println("   file: " + file.getName());
                    fileCounter++;
                }
                dirWalker(file.getAbsolutePath());
            }
        }
    }
}


