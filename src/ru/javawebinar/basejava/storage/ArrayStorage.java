package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Unsorted array based storage implementation for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public List<Resume> getAllSorted() {
        Arrays.sort(storage, 0, size, RESUME_COMPARATOR);
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    protected void insertElement(Resume r, int index) {
        storage[size] = r;
    }

    @Override
    protected void fillDeletedElement(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}