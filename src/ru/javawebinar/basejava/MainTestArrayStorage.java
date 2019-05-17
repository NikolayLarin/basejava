package ru.javawebinar.basejava;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SortedArrayStorage;
import ru.javawebinar.basejava.storage.Storage;

/**
 * Test for ru.javawebinar.basejava.storage.ArrayStorage & SortedArrayStorage implementation
 */
public class MainTestArrayStorage {
    private static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume("uuid1", "name");
        final Resume r2 = new Resume("uuid2", "name");
        final Resume r3 = new Resume("uuid3", "name");


        saveResume(r1, r2, r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
        System.out.println("-----------------------");

        /*
         * Verification of the update method
         */
        saveResume(r1, r2, r3);

        Resume r1upd = new Resume("uuid1", "name");
        Resume r3upd = new Resume("uuid3", "name");

        Resume[] beforeUpdate = ARRAY_STORAGE.getAllSorted().toArray(new Resume[0]);
        ARRAY_STORAGE.update(r1upd);
        ARRAY_STORAGE.update(r3upd);
        Resume[] afterUpdate = ARRAY_STORAGE.getAllSorted().toArray(new Resume[0]);

        for (int i = 0; i < ARRAY_STORAGE.size(); i++) {
            if (afterUpdate[i] == beforeUpdate[i]) {
                System.out.println("Резюме c идентификатором <" + afterUpdate[i] + "> осталось без изменений");
            } else {
                System.out.println("Резюме c идентификатором <" + afterUpdate[i] + "> успешно изменено");
            }
        }

        try {
            System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
        } catch (StorageException ignored) {}
    }

    private static void printAll() {
        System.out.println("\nGet All");
        for (Resume resume : ARRAY_STORAGE.getAllSorted().toArray(new Resume[0])) {
            System.out.println(resume);
        }
    }

    private static void saveResume(Resume r1, Resume r2, Resume r3) {
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
    }
}
