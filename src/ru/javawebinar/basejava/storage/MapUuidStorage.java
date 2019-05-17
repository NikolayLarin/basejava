package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

/**
 * Map based storage for Resumes with Uuid as a searchKey
 */
public class MapUuidStorage extends AbstractStorage {
    protected Map<String, Resume> mapUuid = new HashMap<>();

    @Override
    public int size() {
        return mapUuid.size();
    }

    @Override
    public void clear() {
        mapUuid.clear();
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        mapUuid.put(resume.getUuid(), resume);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        mapUuid.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return mapUuid.get(searchKey.toString());
    }

    @Override
    protected void doDelete(Object searchKey) {
        mapUuid.remove(searchKey.toString());
    }

    /**
     * Returns sorted by Uuid Resume List
     */
    @Override
    public Resume[] getAll() {
        return mapUuid.values().toArray(new Resume[0]);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return mapUuid.containsKey(searchKey.toString());
    }
}