package ru.javawebinar.basejava.storage;

public class ArrayStorageTest extends AbstractArrayStorageTest {
    private static final Storage ARRAY_STORAGE = new ArrayStorage();

    public ArrayStorageTest() {
        super(ARRAY_STORAGE);
    }
}