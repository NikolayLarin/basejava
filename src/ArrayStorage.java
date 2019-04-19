import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void save(Resume resume) {
        String uuid = resume.uuid;
        if (size < storage.length) {
            int i = findElementIndex(uuid);
            if (i >= 0) {
                System.out.println("Резюме с uuid <" + uuid + "> уже существует.");
            } else {
                storage[size] = resume;
                System.out.println("Резюме с uuid <" + uuid + "> успешно сохранено.");
                size++;
            }
        } else {
            System.out.println("Невозможно добавить резюме: база полностью заполнена." +
                    "\nУдалите неактуальные резюме или обратитесь в техподдержку.");
        }
    }

    Resume get(String uuid) {
        int i = findElementIndex(uuid);
        if (i >= 0) {
            return storage[i];
        } else {
            System.out.println("Резюме с uuid <" + uuid + "> не найденo.");
            return null;
        }
    }

    void delete(String uuid) {
        int i = findElementIndex(uuid);
        if (i >= 0) {
            System.arraycopy(storage, i + 1, storage, i, size - i);
            size--;
        } else {
            System.out.println("Резюме с uuid <" + uuid + "> не найденo.");
        }
    }

    void update(Resume resume) {
        String uuid = resume.toString();
        int i = findElementIndex(uuid);
        if (i >= 0) {
            storage[i] = resume;
            System.out.println("Резюме с uuid <" + uuid + "> успешно изменено.");
        } else {
            System.out.println("Резюме с uuid <" + uuid + "> не найденo.");
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