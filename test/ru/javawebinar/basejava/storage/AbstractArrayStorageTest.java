package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.*;

public class AbstractArrayStorageTest {
    private Storage storage = new ArrayStorage();

//    public AbstractArrayStorageTest (Storage storage) {
//        this.storage = storage;
//    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        storage.update(new Resume(UUID_1));
    }

    @Test
    public void getAll() {
        Resume[] expected = {new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        Resume[] result = storage.getAll();
        Assert.assertArrayEquals(expected, result);

    }

    @Test
    public void save() {
        storage.save(new Resume());
        Assert.assertEquals(4, storage.size());

    }

    @Test
    public void delete() {
    }

    @Test
    public void get() {
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
        storage.save(new Resume("uuid4"));
        storage.update(new Resume("uuid4"));
    }
}