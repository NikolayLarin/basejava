package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;

/**
 * List based storage for Resumes
 */
public class MapStorage extends AbstractStorage {

    protected HashMap<String, Resume> storage = new HashMap<>();

    public int size() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateResume(Resume r, Integer searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void saveResume(Resume r, Integer searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume getResume(Integer searchKey) {
        return storage.get(searchKey.toString());
    }

    @Override
    protected void deleteResume(Integer searchKey) {
        storage.remove(searchKey.toString());
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        int size = storage.size();
        return storage.values().toArray(new Resume[size]);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        return 0;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return storage.containsKey(searchKey.toString());
    }
}