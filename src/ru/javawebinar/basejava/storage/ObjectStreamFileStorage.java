package ru.javawebinar.basejava.storage;

import java.io.File;

public class ObjectStreamFileStorage extends FileStorage {

    public ObjectStreamFileStorage(File directory) {
        super(directory);
    }
}