package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Objects;

/**
 * List based storage for Resumes
 */
public class ListStorage extends AbstractStorage {

    protected ArrayList<Resume> storage = new ArrayList<>();

    public int size() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateResume(Resume resume, String searchKey) {
        storage.set(Integer.parseInt(searchKey), resume);
    }

    @Override
    protected void saveResume(Resume r, String searchKey) {
        storage.add(r);
    }

    @Override
    protected Resume getResume(String searchKey) {
        return storage.get(Integer.parseInt(searchKey));
    }

    @Override
    protected void deleteResume(String searchKey) {
        storage.remove(Integer.parseInt(searchKey));
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        int size = storage.size();
        return storage.toArray(new Resume[size]);
    }

    @Override
    protected String getSearchKey(String uuid) {
        int index = 0;
        for (Resume r : storage) {
            if (Objects.equals(r.getUuid(), uuid)) {
                return Integer.toString(index);
            }
            index++;
        }
        return "-1";
    }

    @Override
    protected boolean isExist(String searchKey) {
        int index = Integer.parseInt(searchKey);
        return index >= 0;
    }
}