package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Sorted array based storage implementation for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    private final static Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

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
        Resume searchKey = new Resume(uuid, "dummy");
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }
}