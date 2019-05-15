package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Map based storage for Resumes
 */
// TODO implement
// TODO create new MapStorage with search key not uuid
public class MapUuidStorage extends AbstractStorage {
    private Map<String, Resume> mapUuid = new HashMap<>();

    @Override
    public int size() {
        return mapUuid.size();
    }

    @Override
    public void clear() {
        mapUuid.clear();
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        mapUuid.put(r.getUuid(), r);
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        mapUuid.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return mapUuid.get(searchKey.toString());
    }

    @Override
    protected void doDelete(Object searchKey) {
        mapUuid.remove(searchKey.toString());
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>(mapUuid.values());
        list.sort(RESUME_COMPARATOR);
        return list;
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