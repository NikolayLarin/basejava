package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Map based storage for Resumes with Uuid as a searchKey for searching Resume in collection.
 */
public class MapUuidStorage extends AbstractMapStorage {

    @Override
    protected Resume doGet(Object uuid) {
        return map.get(uuid.toString());
    }

    @Override
    protected void doDelete(Object uuid) {
        map.remove(uuid.toString());
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return map.containsKey(searchKey.toString());
    }
}