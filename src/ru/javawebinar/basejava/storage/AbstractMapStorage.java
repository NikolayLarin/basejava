package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Map based storage for Resumes.
 */
public abstract class AbstractMapStorage extends AbstractStorage {
    protected Map<String, Resume> map = new HashMap<>();

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        doPut(resume);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        doPut(resume);
    }

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(map.values());
    }

    private void doPut(Resume resume) {
        map.put(resume.getUuid(), resume);
    }

}