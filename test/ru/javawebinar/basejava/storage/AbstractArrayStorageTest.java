package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @Before
    public void setUp() {
        storage.clear();
        Resume r1 = new Resume(UUID_1);
        Resume r2 = new Resume(UUID_2);
        Resume r3 = new Resume(UUID_3);
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
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

    @Test(expected = NotExistStorageException.class)
    public void update() {
        Resume r1 = storage.get(UUID_1);
        Resume r3 = storage.get(UUID_3);
        storage.update(new Resume(UUID_1));
        storage.update(new Resume(UUID_3));
        Resume r1upd = storage.get(UUID_1);
        Resume r3upd = storage.get(UUID_3);

        Assert.assertNotSame(r1, r1upd);
        Assert.assertNotSame(r3, r3upd);
        Assert.assertEquals(3, storage.size());

        storage.update(new Resume("uuid4"));
    }

    @Test
    public void getAll() {
        Resume r1 = storage.get(UUID_1);
        Resume r2 = storage.get(UUID_2);
        Resume r3 = storage.get(UUID_3);
        Resume[] expected = {r1, r2, r3};
        Resume[] result = storage.getAll();
        Assert.assertArrayEquals(expected, result);
        Assert.assertEquals(3, result.length);
    }

    @Test(expected = StorageException.class)
    public void save() {
        storage.save(new Resume());
        Assert.assertEquals(4, storage.size());
        storage.clear();
        for (int i = 0; i < 10000; i++) {
            storage.save(new Resume());
        }
        Assert.assertEquals(10000, storage.size());
        storage.save(new Resume());
        Assert.assertEquals(10000, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume());
        Assert.assertEquals(4, storage.size());
        Resume r1 = storage.get(UUID_1);
        storage.save(r1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("uuid1");
        Assert.assertEquals(2, storage.size());
        storage.delete("uuid4");

    }

    @Test(expected = NotExistStorageException.class)
    public void get() {
        Resume r4 = new Resume("uuid4");
        storage.save(r4);
        Assert.assertSame(r4, storage.get("uuid4"));
        Assert.assertEquals(4, storage.size());
        storage.get("uuid5");
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
        storage.save(new Resume("uuid4"));
        storage.update(new Resume("uuid4"));
    }
}