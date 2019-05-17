package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.util.Objects;

public class MainHashCode {
    public static void main(String[] args) {
        String uuid = "uuid";
        String fullName = "name";
        Resume resume = new Resume(uuid, fullName);

        int result = resume.getUuid().hashCode();
        result = 31 * result + resume.getFullName().hashCode();
        System.out.println(result);                                                 // 115015216
        System.out.println(Objects.hash(resume.getUuid(), resume.getFullName()));   // 115016177
        System.out.println(Objects.hash(uuid, fullName));                           // 115016177
//        System.out.println(Objects.hash(resume, uuid, fullName));                   -1023558592
    }
}
