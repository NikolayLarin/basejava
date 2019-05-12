package ru.javawebinar.basejava.storage;

import org.junit.Ignore;
import org.junit.Test;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Ignore
    @Test
    public void saveOverflow() {
    }

    @Ignore
    @Test
    public void getAll() {
    }
}