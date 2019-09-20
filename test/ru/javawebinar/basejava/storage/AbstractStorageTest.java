package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.ResumeTestData;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.util.Config;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;


public abstract class AbstractStorageTest {
    //    protected static final File STORAGE_DIR = new File("D:/Java/MyProjects/basejava/storage");
    protected static final File STORAGE_DIR = Config.getINSTANCE().getStorageDir();
    protected Storage storage;

    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final String UUID_4 = UUID.randomUUID().toString();


    public static final Resume RESUME_1;
    public static final Resume RESUME_2;
    public static final Resume RESUME_3;
    public static final Resume RESUME_4;

    static {
        RESUME_1 = ResumeTestData.createResume(UUID_1, "Name1");
        RESUME_2 = new Resume(UUID_2, "Name2");
        RESUME_3 = ResumeTestData.createResume(UUID_3, "Name3");
        RESUME_4 = ResumeTestData.createResume(UUID_4, "Name4");
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_3);
        storage.save(RESUME_1);
        storage.save(RESUME_2);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() {
        Resume updatedResume = ResumeTestData.createResume(UUID_1, "Name5");
        storage.update(updatedResume);
        assertEquals(updatedResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("notExist", "Name1"));
    }

    @Test
    public void getAllSorted() {
        List<Resume> expected = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        List<Resume> result = storage.getAllSorted();
        assertEquals(expected, result);
        assertEquals(3, result.size());
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertEquals(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }


    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        assertSize(2);
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("notExist");
    }

    @Test
    public void get() {
        Resume resume = storage.get(UUID_2);
        assertEquals(RESUME_2, resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("notExist");
    }

    protected void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}