import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume resume) {
        storage[size] = resume;
        size++;
    }

    Resume get(String uuid) {
        int i = findElementIndex(uuid);
        if (i >= 0) {
            return storage[i];
        } else {
            return null;
        }
    }

    void delete(String uuid) {
        int i = findElementIndex(uuid);
        if (i >= 0) {
            System.arraycopy(storage, i + 1, storage, i, size - i);
            size--;
        } else {
            System.out.println("Элемент с uuid, равным <" + uuid + ">, не найден.");
        }
    }

    private int findElementIndex(String uuid) {
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
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }
}