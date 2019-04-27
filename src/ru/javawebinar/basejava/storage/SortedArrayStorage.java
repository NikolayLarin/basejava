package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        if (size >= STORAGE_LIMIT){
            System.out.println("Can't add resume: storage is full." +
                    "\nDelete unnecessary resumes or contact support.");
        } else {
            String uuid = r.getUuid();
            int index = getIndex(uuid);
            if (index >= 0) {
                System.out.println("Resume with uuid <" + uuid + "> already exist.");
            } else {
                System.arraycopy(storage, -index - 1, storage, -index, size);
                storage[-index - 1] = r;
                size++;
            }
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0){
            System.arraycopy(storage, index + 1, storage, index, size);
            storage [size - 1] = null;
            size--;
        } else {
            System.out.println("Resume with uuid <" + uuid + "> not found.");
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
