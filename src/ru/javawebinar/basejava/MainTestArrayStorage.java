package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.ArrayStorage;

/**
 * Test for ru.javawebinar.basejava.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    private static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r2 = new Resume();
        r2.setUuid("uuid2");
        Resume r3 = new Resume();
        r3.setUuid("uuid3");

        saveResume(r1, r2, r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
        System.out.println("-----------------------");

        /*
         * Verification of the update method in the ru.javawebinar.basejava.storage.ArrayStorage
         */
        saveResume(r1, r2, r3);

        Resume r1upd = new Resume();
        r1upd.setUuid("uuid1");
        Resume r3upd = new Resume();
        r3upd.setUuid("uuid3");

        Resume[] beforeUpdate = ARRAY_STORAGE.getAll();
        ARRAY_STORAGE.update(r1upd);
        ARRAY_STORAGE.update(r3upd);
        Resume[] afterUpdate = ARRAY_STORAGE.getAll();

        for (int i = 0; i < ARRAY_STORAGE.size(); i++) {
            if (afterUpdate[i].hashCode() == beforeUpdate[i].hashCode()) {
                if (afterUpdate[i].equals(beforeUpdate[i])) {
                System.out.println("Резюме c идентификатором <" + afterUpdate[i] + "> осталось без изменений");
                }
            } else {
                System.out.println("Резюме c идентификатором <" + afterUpdate[i] + "> успешно изменено");
            }
        }
    }

    private static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }

    private static void saveResume(Resume r1, Resume r2, Resume r3) {
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
    }
}
