package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Objects;

/**
 * List based storage for Resumes
 */
public class ListStorage extends AbstractStorage {

    protected final static ArrayList<Resume> storage = new ArrayList<>();

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
        int size = storage.size();
        return storage.toArray(new Resume[size]);
    }

    public void save(Resume r) {
        if (storage.contains(r)) {
            throw new ExistStorageException(r.getUuid());
        } else {
            storage.add(r);
        }
    }

    @Override
    protected int getIndex(String uuid) {
        int index = 0;
        for (Resume r : storage) {
            if (Objects.equals(r.getUuid(), uuid)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    protected void updateResume(Resume resume, int index) {
        storage.remove(resume);
        storage.add(resume);
    }

    @Override
    protected void deleteResume(int index) {
        storage.remove(index);
    }

    @Override
    protected Resume getResume(int index) {
        return storage.get(index);
    }
}