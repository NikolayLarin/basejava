package ru.javawebinar.basejava.storage;

import org.junit.Test;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.UUID;

import static org.junit.Assert.fail;

public class ArrayStorageTest extends AbstractStorageTest {

    public ArrayStorageTest() {
        super(new ArrayStorage());
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        storage.clear();
        int i = 0;
        try {
            for (i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume(randomUUID(), "name"));
            }
        } catch (StorageException e) {
            fail("RuntimeException: storage overflow in AbstractArrayStorage, " +
                    "storage limit <" + i + "> reached");
        }
        assertSize(AbstractArrayStorage.STORAGE_LIMIT);
        storage.save(RESUME_4);
    }

    private String randomUUID() {
        return UUID.randomUUID().toString();
    }
}