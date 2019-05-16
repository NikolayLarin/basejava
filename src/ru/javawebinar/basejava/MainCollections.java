package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class MainCollections {
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1, "name");

    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2, "name");

    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3, "name");

    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = new Resume(UUID_4, "name");

    public static void main(String[] args) {
        Collection<Resume> collection = new ArrayList<>();
        collection.add(RESUME_1);
        collection.add(RESUME_2);
        collection.add(RESUME_3);

        for (Resume r : collection) {
            System.out.println(r);
            if (Objects.equals(r.getUuid(), UUID_1)) {
//                collection.remove(r);
            }
        }
        System.out.println("---------------");

        Iterator<Resume> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (Objects.equals(r.getUuid(), UUID_1)) {
                iterator.remove();
                System.out.println(r);
            }
            System.out.println(r);
        }
        System.out.println(collection.toString());
        System.out.println("---------------");

        Map<String, Resume> map = new HashMap<>();
        map.put(UUID_1, RESUME_1);
        map.put(UUID_2, RESUME_2);
        map.put(UUID_3, RESUME_3);

        // It's  a bad implementation
        for (String uuid : map.keySet()) {
            System.out.println(map.get(uuid));
        }
        System.out.println("---------------");

        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }
        System.out.println("---------------");


        Deque<Integer> queue = new ArrayDeque<>(Arrays.asList(1, 2, 3, 4));
        queue.add(5);
        queue.remove(1);
        queue.remove(2);
        System.out.println(queue);

        queue.clear();
        queue.push(7);
        queue.push(1);
        queue.push(0);
        queue.push(2);
        System.out.println(queue);

        List<String> nameList = new ArrayList<>(Arrays.asList("Mr.Green", "Mr.Yellow", "Mr.Red"));
        String[] strArray = nameList.toArray(new String[0]);
        for (String str : strArray) {
            System.out.println(str);
        }
        nameList.add("Mr.Add");
        System.out.println(nameList);
        nameList.remove(1);
        nameList.remove("Mr.Yellow");
        System.out.println(nameList);

        List<Resume> resumes = Arrays.asList(RESUME_1, RESUME_2, RESUME_3); //fixed size
        resumes.remove(1); // UnsupportedOperationException
        resumes.remove(RESUME_1);  // UnsupportedOperationException
        System.out.println(resumes);

    }
}
