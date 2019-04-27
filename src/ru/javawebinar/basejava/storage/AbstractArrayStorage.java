package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        String uuid = r.getUuid();
        int index = getIndex(uuid);
        if (index >= 0) {
            storage[index] = r;
        } else {
            printNotFound(uuid);
        }
    }

    public void save(Resume r) {
        if (size >= STORAGE_LIMIT) {
            System.out.println("Can't add resume: storage is full." +
                    "\nDelete unnecessary resumes or contact support.");
        } else {
            String uuid = r.getUuid();
            int index = getIndex(uuid);
            if (index >= 0) {
                System.out.println("Resume with uuid <" + uuid + "> already exist.");
            } else {
                saveResume(r, index);
                size++;

            }
        }
    }

    protected abstract void saveResume(Resume r, int index);

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            printNotFound(uuid);
            return null;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            deleteResume(index);
            size--;
        } else {
            printNotFound(uuid);
        }
    }

    protected abstract void deleteResume(int index);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected abstract int getIndex(String uuid);

    private void printNotFound(String uuid) {
        System.out.println("Resume with uuid <" + uuid + "> not found.");
    }
}