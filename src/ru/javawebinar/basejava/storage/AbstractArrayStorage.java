package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
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

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public void save(Resume r) {
        String uuid = r.getUuid();
        int index = getIndex(uuid);
        if (size == STORAGE_LIMIT) {
            String storageFull = "Can't add resume: storage is full.";
            throw new StorageException(storageFull, r.getUuid());
        } else if (index >= 0) {
            throw new ExistStorageException(uuid);
        } else {
            saveResume(r, index);
            size++;
        }
    }

    @Override
    protected void updateResume(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected Resume getResume(int index) {
        return storage[index];
    }

    protected abstract void saveResume(Resume resume, int index);

    protected abstract int getIndex(String uuid);
}