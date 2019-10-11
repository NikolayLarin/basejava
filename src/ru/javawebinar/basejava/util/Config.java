package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.storage.SqlStorage;
import ru.javawebinar.basejava.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    // private static final File PROPS = new File("D:/Java/MyProjects/basejava/config/resumes.properties");
/*
    // settings for local db:
    private static final File PROPS = new File(getHomeDir(), "config/resumes.properties");
*/

    // settings for heroku db on https://resumesappbylarin.herokuapp.com:
    // also mark config folder as resource root
    private static final String PROPS = "/resumes.properties";

    private static final Config INSTANCE = new Config();

    private final File storageDir;

    private final Storage sqlStorage;

    // settings for heroku db on https://resumesappbylarin.herokuapp.com:
    private Config() {
        try (InputStream is = Config.class.getResourceAsStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            sqlStorage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS);
        }
    }

/*
    // settings for local db:
    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            sqlStorage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }
*/

    public static Config getINSTANCE() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getSqlStorage() {
        return sqlStorage;
    }

/*
    // settings for local db:
    private static File getHomeDir() {
        String prop = System.getProperty("homeDir");
        File homeDir = new File(prop == null ? "." : prop);
        if (!homeDir.isDirectory()) {
            throw new IllegalStateException(homeDir + " is not directory");
        }
        return homeDir;
    }
*/
}