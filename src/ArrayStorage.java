import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < size(); i++) {
            this.storage[i] = null;
        }
    }

    void save(Resume resume) {
        this.storage[size() - 1] = resume;
    }

    Resume get(String uuid) {
//        return storage[findElementNumber(uuid)];
        int size = size();
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                return storage[i];
            }
        }
        System.out.println("Элемент с uuid, равным " + uuid + " не найден.");
        return null;
    }

    void delete(String uuid) {
//        for (int i = findElementNumber(uuid); i <= size() ; i++) {
////            storage[i] = storage[i++];
//        }
    }
//    private int findElementNumber(String uuid) {
//        int i = 0;
//        while (i < size() - 1) {
//            if (!storage[i].toString().equals(uuid)) {
//                i++;
//            }
//        }
//        if (i == size() - 1 && !storage[i].toString().equals(uuid)) {
//            System.out.println("Элемент с uuid, равным " + uuid + " не найден.");
//            return 0;
//        }
//        return i;
//    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size());
    }

    int size() {
        int size = 0;
        while (storage[size] != null) {
                size++;
        }
        System.out.println(size);
        return size + 1;
    }
}