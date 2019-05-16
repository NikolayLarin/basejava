package ru.javawebinar.basejava.storage;

import org.junit.Ignore;
import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MapResumeStorageTest extends AbstractStorageTest {

    public MapResumeStorageTest() {
        super(new MapResumeStorage());
    }

    @Ignore
    @Test
    public void saveOverflow() {
    }

    @Test
    @Override
    public void getAllSorted() {
        storage.clear();
        List<Resume> expected = new ArrayList<>(Arrays.asList(
                RESUME_1_1,
                RESUME_4_1,
                RESUME_3_3));
        storage.save(RESUME_3_3);
        storage.save(RESUME_1_1);
        storage.save(RESUME_4_1);
        List<Resume> result = storage.getAllSorted();
        assertEquals(expected, result);
        assertEquals(3, result.size());
    }
}