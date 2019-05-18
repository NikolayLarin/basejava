package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Sorted array based storage implementation for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertElement(Resume resume, int index) {
        int insertPoint = -index - 1;
        System.arraycopy(storage, insertPoint, storage, insertPoint + 1, size - insertPoint);
        storage[insertPoint] = resume;
    }

    @Override
    protected void fillDeletedElement(int index) {
        int length = size - index - 1;
        System.arraycopy(storage, index + 1, storage, index, length);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "unused");
        return Arrays.binarySearch(storage, 0, size, searchKey, Comparator.comparing(Resume::getUuid));
    }

    @Override
    public List<Resume> getAllSorted() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }
}