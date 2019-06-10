package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.ResumeTestData;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.AboutSection;
import ru.javawebinar.basejava.model.CareerSection;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.model.SkillsSection;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;


public abstract class AbstractStorageTest {
    protected final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final ResumeTestData.ResumeData DATA = new ResumeTestData().new ResumeData();


    protected static final Resume RESUME_1;
    protected static final Resume RESUME_2;
    protected static final Resume RESUME_3;
    protected static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, "Name1");
        RESUME_1.setContact(ContactType.PHONE, DATA.phone);
        RESUME_1.setContact(ContactType.SKYPE, DATA.skype);
        RESUME_1.setSection(SectionType.OBJECTIVE, new AboutSection(DATA.objective));
        RESUME_1.setSection(SectionType.PERSONAL, new AboutSection(DATA.personal));

        RESUME_2 = new Resume(UUID_2, "Name2");
        RESUME_2.setContact(ContactType.EMAIL, DATA.email);
        RESUME_2.setContact(ContactType.LINKEDIN, DATA.linkedin);
        RESUME_2.setSection(SectionType.ACHIEVEMENT, new SkillsSection(DATA.getAchievements()));
        RESUME_2.setSection(SectionType.QUALIFICATIONS, new SkillsSection(DATA.getQualifications()));

        RESUME_3 = new Resume(UUID_3, "Name1");
        RESUME_3.setContact(ContactType.GITHUB, DATA.gitHub);
        RESUME_3.setContact(ContactType.STACKOVERFLOW, DATA.stackOverflow);
        RESUME_3.setSection(SectionType.EXPERIENCE, new CareerSection(DATA.getExperience()));

        RESUME_4 = new Resume(UUID_4, "Name4");
        RESUME_4.setContact(ContactType.SITE, DATA.site);
        RESUME_4.setSection(SectionType.EDUCATION, new CareerSection(DATA.getEducation()));
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_3);
        storage.save(RESUME_1);
        storage.save(RESUME_2);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() {
        Resume updatedResume = new Resume(UUID_1, "Name5");
        storage.update(updatedResume);
        assertSame(updatedResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("notExist", "Name1"));
    }

    @Test
    public void getAllSorted() {
        List<Resume> expected = Arrays.asList(RESUME_1, RESUME_3, RESUME_2);
        List<Resume> result = storage.getAllSorted();
        assertEquals(expected, result);
        assertEquals(3, result.size());
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertSame(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }


    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        assertSize(2);
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("notExist");
    }

    @Test
    public void get() {
        Resume resume = storage.get(UUID_2);
        assertSame(RESUME_2, resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("notExist");
    }

    protected void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}