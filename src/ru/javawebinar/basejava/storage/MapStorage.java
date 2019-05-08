package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.TreeMap;

/**
 * List based storage for Resumes
 */
public class MapStorage extends AbstractStorage {

    protected final static TreeMap<String, Resume> storage = new TreeMap<>();

    public int size() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateResume(Resume r, int index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void saveResume(Resume r, int index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume getResume(String uuid, int index) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteResume(String uuid, int index) {
        storage.remove(uuid);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        int size = storage.size();
        return storage.values().toArray(new Resume[size]);
    }

    @Override
    protected int getIndex(String uuid) {
        if (storage.containsKey(uuid)) {
            return 1;
        }
        return -1;
    }

}