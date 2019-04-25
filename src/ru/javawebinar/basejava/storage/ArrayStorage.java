package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size >= STORAGE_LIMIT){
            System.out.println("Can't add resume: storage is full." +
                    "\nDelete unnecessary resumes or contact support.");
        } else {
            String uuid = r.getUuid();
            if (getIndex(uuid) >= 0) {
                System.out.println("Resume with uuid <" + uuid + "> already exist.");
            } else {
                storage[size] = r;
                size++;
            }
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0){
            storage[index] = storage[size - 1];
            storage [size - 1] = null;
            size--;
        } else {
            printNotFound(uuid);
        }
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

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        } return -1;
    }

    protected void printNotFound(String uuid) {
        System.out.println("Resume with uuid <" + uuid + "> not found.");
    }
}