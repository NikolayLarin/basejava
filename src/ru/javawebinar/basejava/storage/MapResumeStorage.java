package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    /**
     * Returns sorted by fullName Resume List
     */
    @Override
    public Resume[] getAll() {
        return mapResume.values().toArray(new Resume[0]);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume resume;
        for (Map.Entry<String, Resume> entry : mapResume.entrySet()) {
            resume = entry.getValue();
            if (Objects.equals(resume.getUuid(), uuid)) {
                return resume;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }
}