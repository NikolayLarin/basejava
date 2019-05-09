package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume r) {
        String uuid = r.getUuid();
        Integer searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        } else {
            updateResume(r, searchKey);
        }
    }

    public void save(Resume r) {
        String uuid = r.getUuid();
        Integer searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        } else {
            saveResume(r, searchKey);
        }
    }

    public Resume get(String uuid) {
        Integer searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        } else {
            return getResume(searchKey);
        }
    }

    public void delete(String uuid) {
        Integer searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteResume(searchKey);
        }
    }

    protected abstract void updateResume(Resume r, Integer searchKey);

    protected abstract void saveResume(Resume r, Integer searchKey);

    protected abstract void deleteResume(Integer searchKey);

    protected abstract Integer getSearchKey(String uuid);

    protected abstract boolean isExist(Integer searchKey);

    protected abstract Resume getResume(Integer searchKey);
}