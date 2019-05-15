package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        mapUuid.put(r.getFullName(), r);
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        mapUuid.put(r.getFullName(), r);
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
        list.sort(Comparator.comparing(Resume::getFullName));
        return list;
    }

    @Override
    protected String getSearchKey(String uuid) {
        for (Map.Entry<String, Resume> entry : mapUuid.entrySet()) {
            Resume r = entry.getValue();
            if (Objects.equals(r.getUuid(), uuid)) {
                return r.getFullName();
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return false;
//        return mapUuid.containsKey(searchKey.toString());
    }
}