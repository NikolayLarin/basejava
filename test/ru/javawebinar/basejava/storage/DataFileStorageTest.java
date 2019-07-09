package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serializer.DataStreamStrategy;

public class DataFileStorageTest extends AbstractStorageTest {

    public DataFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new DataStreamStrategy()));
    }
}