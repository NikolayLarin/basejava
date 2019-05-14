package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * List based storage for Resumes
 */
public class ListStorage extends AbstractStorage {

    protected List<Resume> listStorage = new ArrayList<>();

    public int size() {
        return listStorage.size();
    }

    public void clear() {
        listStorage.clear();
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        listStorage.set((int) searchKey, resume);
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        listStorage.add(r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return listStorage.get((int) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        listStorage.remove((int) searchKey);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return listStorage.toArray(new Resume[0]);
    }

    public List<Resume> getAllSorted() {
        listStorage.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return listStorage;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        int index = 0;
        for (Resume r : listStorage) {
            if (Objects.equals(r.getUuid(), uuid)) {
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