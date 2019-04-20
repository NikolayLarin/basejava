import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        String uuid = resume.getUuid();
        if (size < storage.length){
            int i = findIndex(uuid);
            if (i >= 0) {
                System.out.println("Resume with uuid <" + uuid + "> already exist.");
            } else {
                storage[size] = resume;
                size++;
            }
        } else {
            System.out.println("Can't add resume: storage is full." +
                    "\nDelete unnecessary resumes or contact support.");
        }
    }

    public Resume get(String uuid) {
        int i = findIndex(uuid);
        if (i >= 0) {
            return storage[i];
        } else {
            System.out.println("Resume with uuid <" + uuid + "> not found.");
            return null;
        }
    }

    public void delete(String uuid) {
        int i = findIndex(uuid);
        if (i >= 0) {
            storage[i] = storage[size - 1];
            size--;
        } else {
            System.out.println("Resume with uuid <" + uuid + "> not found.");
        }
    }

    public void update(Resume resume) {
        int i = findIndex(resume.getUuid());
        if (i >= 0) {
            storage[i] = resume;
        } else {
            System.out.println("Resume with uuid <" + resume.getUuid() + "> not found.");
        }
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        } return -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}