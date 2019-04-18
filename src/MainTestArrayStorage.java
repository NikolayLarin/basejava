/**
 * Test for your ArrayStorage implementation
 */
public class MainTestArrayStorage {
    private static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.uuid = "uuid1";
        Resume r2 = new Resume();
        r2.uuid = "uuid2";
        Resume r3 = new Resume();
        r3.uuid = "uuid3";

        saveThreeResumes(r1, r2, r3);
        checkArrayStorageMethods(r1, r2, r3);
        System.out.println("_________________________");

        Resume r1Upd = new Resume();
        r1Upd.uuid = "uuid2";
        Resume r4 = new Resume();
        r4.uuid = "uuid4";
        Resume r3Upd = new Resume();
        r3Upd.uuid = "uuid3";

        saveThreeResumes(r1Upd, r4, r3Upd);
        ARRAY_STORAGE.update(r1Upd);
        ARRAY_STORAGE.update(r3Upd);
        checkArrayStorageMethods(r1Upd, r4, r3Upd);
        System.out.println("_________________________");



//        System.out.println("\nGet r2 with uuid <" + ARRAY_STORAGE.get(r2.uuid) + ">");
//        ARRAY_STORAGE.update(r2Upd);
//        System.out.println("and update r2 with <" + ARRAY_STORAGE.get(r2.uuid) + "> to r2Upd with <" + r2Upd.toString() + ">:");
//        System.out.println("Get r2Upd: " + ARRAY_STORAGE.get(r2Upd.uuid));
//        printAll();
    }

    private static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }

    private static void saveThreeResumes(Resume r1, Resume r2, Resume r3) {
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
    }

    private static void checkArrayStorageMethods(Resume r1, Resume r2, Resume r3) {
        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.uuid));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.uuid);
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

}
