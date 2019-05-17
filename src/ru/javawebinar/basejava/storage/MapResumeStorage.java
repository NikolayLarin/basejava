package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

/**
 * Map based storage for Resumes with Resume as a searchKey
 */
public class MapResumeStorage extends AbstractStorage {
    private Map<String, Resume> mapResume = new HashMap<>();

    @Override
    public int size() {
        return mapResume.size();
    }

    @Override
    public void clear() {
        mapResume.clear();
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        mapResume.put(resume.getUuid(), resume);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        mapResume.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void doDelete(Object searchKey) {
        Resume resume = (Resume) searchKey;
        mapResume.remove(resume.getUuid());
    }

    /**
     * Returns sorted by fullName Resume List
     */
    @Override
    public Resume[] getAll() {
        return mapResume.values().toArray(new Resume[0]);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return mapResume.get(uuid);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }
}