package ru.javawebinar.basejava.storage;

import org.junit.Test;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.fail;

public class ListStorageTest extends AbstractArrayStorageTest {

    public ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    @Test
    public void saveOverflow() {
        storage.clear();
        int i = 0;
        try {
            for (i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            fail("RuntimeException: storage overflow in AbstractArrayStorage, " +
                    "storage limit <" + i + "> reached");
        }
        assertSize(AbstractArrayStorage.STORAGE_LIMIT);
        storage.save(new Resume());
    }
}