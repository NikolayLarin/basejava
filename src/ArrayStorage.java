import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];

    void clear() {
        int size = size();
        for (int i = 0; i < size; i++) {
            this.storage[i] = null;
        }
    }

    void save(Resume resume) {
        this.storage[size()] = resume;
    }

    Resume get(String uuid) {
        int i = findElementIndex(uuid);
        if (i >= 0) {
            return storage[i];
        } else {
            Resume notPresent = new Resume();
            notPresent.uuid = "Элемент с uuid, равным <" + uuid + ">, не найден.";
            return notPresent;
        }
    }

    void delete(String uuid) {
        int i = findElementIndex(uuid);
        int size = size();
        if (i >= 0) {
            System.arraycopy(storage, i + 1, storage, i, size - i);
//            for (int j = i; j < size; j++) {
//                storage[j] = storage[j + 1];
//            }
        } else {
            System.out.println("Элемент с uuid, равным <" + uuid + ">, не найден.");
        }
    }

    private int findElementIndex(String uuid) {
        int size = size();
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        } return -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size());
    }

    int size() {
        int i = 0;
        do {
            i++;
        } while (storage[i - 1] != null);
//        System.out.println(i - 1);
        return i - 1;
    }
}