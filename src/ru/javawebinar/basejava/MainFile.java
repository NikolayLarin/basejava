package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
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

        FilesTree filesTree = new FilesTree();
        filesTree.dirWalker("D:/Java_Projects/TopJava.ru/basejava");
        if (filesTree.getDirCounter()==305+1 && filesTree.getFileCounter()==1346) {
            System.out.println("\nThe file tree walking was successful. " +
                    "\nTotal directory listed :" + filesTree.getDirCounter() +
                    "\nTotal files listed: " + filesTree.getFileCounter());
        }

    }
}

class FilesTree {
    private int dirCounter;
    private int fileCounter;

    int getDirCounter() {
        return dirCounter;
    }

    int getFileCounter() {
        return fileCounter;
    }

    void dirWalker(String filePath) {
        File dir = new File(filePath);
        if (dir.isDirectory()) {
            dirCounter++;
            System.out.println("\n" + dir);
            String[] list = dir.list();
            if (list != null) {
                for (String name : list) {
                    System.out.println(name);
                }
            }
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    dirWalker(file.getPath());
                    if (file.isFile()){
                        fileCounter++;
                    }
                }
            }
        }

    }
}


