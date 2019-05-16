package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sorted array based storage implementation for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public List<Resume> getAllSorted() {
        return new ArrayList<>(Arrays.asList(Arrays.copyOf(storage, size)));

    }

    @Override
    protected void insertElement(Resume r, int index) {
        int insertPoint = -index - 1;
        System.arraycopy(storage, insertPoint, storage, insertPoint + 1, size - insertPoint);
        storage[insertPoint] = r;
    }

    @Override
    protected void fillDeletedElement(int index) {
        int length = size - index - 1;
        System.arraycopy(storage, index + 1, storage, index, length);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        String fullName = null;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                fullName = storage[i].getFullName();
            }
        }
        Resume searchKey = new Resume(uuid, fullName);
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }

/*
    nested class
    private static class ResumeComparator implements Comparator<Resume> {
        @Override
        public int compare(Resume r1, Resume r2) {
            return r1.getUuid().compareTo(r2.getUuid());
        }
    }

    private static final ResumeComparator RESUME_COMPARATOR = new ResumeComparator();

    anonymous class
    private static final Comparator<Resume> RESUME_COMPARATOR = new Comparator<Resume>() {
        @Override
        public int compare(Resume r1, Resume r2) {
            return r1.getUuid().compareTo(r2.getUuid());
        }
    };

    lambda
    private static final Comparator<Resume> RESUME_COMPARATOR = (r1, r2) -> r1.getUuid().compareTo(r2.getUuid());

    private static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);
*/
}