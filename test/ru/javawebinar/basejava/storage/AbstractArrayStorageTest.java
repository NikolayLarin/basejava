package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest {

    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final Resume r1 = new Resume(UUID_1);
    private static final Resume r2 = new Resume(UUID_2);
    private static final Resume r3 = new Resume(UUID_3);

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
        storage.save(new Resume("uuid4"));
        Assert.assertEquals(4, storage.size());
        storage.delete("uuid4");
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume r1upd = new Resume(UUID_1);
        storage.update(r1upd);
        Assert.assertSame(r1upd, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("uuid4"));
    }

    @Test
    public void getAll() {
        Resume[] expected = {r1, r2, r3};
        Resume[] result = storage.getAll();
        Assert.assertArrayEquals(expected, result);
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void save() {
        Resume r4 = new Resume("uuid4");
        storage.save(r4);
        Assert.assertSame(r4, storage.get("uuid4"));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        storage.clear();
        int i = 0;
        try {
            for (i = 0; i < 10000; i++) {
                storage.save(new Resume());
            }
        } catch (RuntimeException e) {
            fail("RuntimeException: storage overflow in AbstractArrayStorage, " +
                    "storage limit <" + i + "> reached");
        }
        Assert.assertEquals(10000, storage.size());
        storage.save(new Resume());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(r1);
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertSame(r3, storage.get(UUID_3));
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("uuid4");

    }

    @Test
    public void get() {
        Resume r2pulled = storage.get(UUID_2);
        Assert.assertSame(r2, r2pulled);
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("uuid4");
    }
}