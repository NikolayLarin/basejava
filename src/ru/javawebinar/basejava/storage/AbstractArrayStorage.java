package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void updateResume(Resume r, Object searchKey) {
        storage[Integer.parseInt(searchKey.toString())] = r;
    }

    @Override
    protected void saveResume(Resume r, Object searchKey) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Can't add resume: storage is full.", r.getUuid());
        } else {
            insertElement(r, Integer.parseInt(searchKey.toString()));
            size++;
        }
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage[Integer.parseInt(searchKey.toString())];
    }

    @Override
    protected void deleteResume(Object searchKey) {
        fillDeletedElement(Integer.parseInt(searchKey.toString()));
        storage[size - 1] = null;
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }


    @Override
    protected Object getSearchKey(String uuid) {
        return getIndex(uuid);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return Integer.parseInt(searchKey.toString()) >= 0;
    }

    protected abstract void insertElement(Resume r, int index);

    protected abstract void fillDeletedElement(int index);

    protected abstract int getIndex(String uuid);
}