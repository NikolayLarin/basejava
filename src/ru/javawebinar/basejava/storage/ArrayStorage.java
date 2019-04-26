package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
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

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0){
            storage[index] = storage[size - 1];
            storage [size - 1] = null;
            size--;
        } else {
            System.out.println("Resume with uuid <" + uuid + "> not found.");
        }
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        } return -1;
    }
}