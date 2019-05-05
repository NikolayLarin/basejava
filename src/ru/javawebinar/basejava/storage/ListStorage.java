package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Collection;

public class ListStorage {

    protected Collection<Resume> storage = new ArrayList();

    public int size() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    public void update(Resume r) {
        String uuid = r.getUuid();
        int index = 0; //getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
//            storage[index] = r;
        }
    }

    public void save(Resume r) {
        String uuid = r.getUuid();
        int index = 0; //getIndex(uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        } else {
            saveResume(r, index);
        }
    }

    public Resume get(String uuid) {
        int index = 0; //getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            return null; //storage[index];
        }
    }

    public void delete(String uuid) {
        int index = 0; //getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteResume(index);
//            storage[size - 1] = null;
//            size--;
        }
    }

    protected void saveResume(Resume resume, int index){}

    protected void deleteResume(int index) {}

//    protected int getIndex(String uuid) {}
}
