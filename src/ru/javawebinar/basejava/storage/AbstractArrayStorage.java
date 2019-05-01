package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
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

    public void update(Resume r) {
        String uuid = r.getUuid();
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            storage[index] = r;
        }
    }

    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            String storageFull = "Can't add resume: storage is full." +
                    "\nDelete unnecessary resumes or contact support.";
            throw new StorageException(storageFull, r.getUuid());
        } else {
            String uuid = r.getUuid();
            int index = getIndex(uuid);
            if (index >= 0) {
                throw new ExistStorageException(uuid);
            } else {
                saveResume(r, index);
                size++;
            }
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            return storage[index];
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteResume(index);
            storage[size - 1] = null;
            size--;
        }
    }

    protected abstract void saveResume(Resume resume, int index);

    protected abstract void deleteResume(int index);

    protected abstract int getIndex(String uuid);
}