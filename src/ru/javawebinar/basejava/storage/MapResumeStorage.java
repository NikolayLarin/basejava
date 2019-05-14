package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Map based storage for Resumes
 */
public class MapResumeStorage extends AbstractStorage {
    protected Map<String, Resume> mapStorage = new HashMap<>();

    public int size() {
        return mapStorage.size();
    }

    public void clear() {
        mapStorage.clear();
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        mapStorage.put(r.getUuid(), r);
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        mapStorage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return mapStorage.get(searchKey.toString());
    }

    @Override
    protected void doDelete(Object searchKey) {
        mapStorage.remove(searchKey.toString());
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return mapStorage.values().toArray(new Resume[0]);
    }


    public List<Resume> getAllSorted() {
        return null;
    }


    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return mapStorage.containsKey(searchKey.toString());
    }
}