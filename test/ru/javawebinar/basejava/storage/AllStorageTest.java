package ru.javawebinar.basejava.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.javawebinar.basejava.util.JsonParserTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapResumeStorageTest.class,
        MapUuidStorageTest.class,
        ObjectFileStorageTest.class,
        ObjectPathStorageTest.class,
        XmlPathStorageTest.class,
        XmlFileStorageTest.class,
        JsonFileStorageTest.class,
        JsonPathStorageTest.class,
        DataFileStorageTest.class,
        DataPathStorageTest.class,
        SqlStorageTest.class,
        JsonParserTest.class})
public class AllStorageTest {
}