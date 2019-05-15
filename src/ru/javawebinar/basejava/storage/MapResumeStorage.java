package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Map based storage for Resumes
 */
public class MapResumeStorage extends AbstractStorage {
    protected Map<String, Resume> mapResume = new HashMap<>();

    @Override
    public int size() {
        return mapResume.size();
    }

    @Override
    public void clear() {
        mapResume.clear();
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        mapResume.put(r.getUuid(), r);
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        mapResume.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return mapResume.get(searchKey.toString());
    }

    @Override
    protected void doDelete(Object searchKey) {
        mapResume.remove(searchKey.toString());
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>(mapResume.values());
        list.sort(RESUME_COMPARATOR);
        return list;
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return mapResume.containsKey(searchKey.toString());
    }
}