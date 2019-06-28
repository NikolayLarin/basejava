package ru.javawebinar.basejava.storage;

import java.io.File;

public class ObjectStreamFileStorage extends FileStorage {

    protected ObjectStreamFileStorage(File directory) {
        super(directory, new ObjectStreamStorage());
    }
}