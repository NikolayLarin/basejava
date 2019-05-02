package ru.javawebinar.basejava.storage;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    private static final Storage SORTED_ARRAY_STORAGE = new SortedArrayStorage();

    public SortedArrayStorageTest() {
        super(SORTED_ARRAY_STORAGE);
    }
}