package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * List based storage for Resumes
 */
public class ListStorage extends AbstractStorage {

    protected List<Resume> listStorage = new ArrayList<>();

    @Override
    public int size() {
        return listStorage.size();
    }

    @Override
    public void clear() {
        listStorage.clear();
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        listStorage.set((int) searchKey, resume);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        listStorage.add(resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return listStorage.get((int) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        listStorage.remove((int) searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        listStorage.sort(RESUME_COMPARATOR);
        return listStorage;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        int index = 0;
        for (Resume resume : listStorage) {
            if (Objects.equals(resume.getUuid(), uuid)) {
                return index;
            }
            index++;
        }
        return null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }
}