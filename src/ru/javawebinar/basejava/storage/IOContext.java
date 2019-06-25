package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOContext {
    private IOStrategy ioStrategy;


    public IOContext(IOStrategy ioStrategy) {
        this.ioStrategy = ioStrategy;
    }

    public void setIoStrategy(IOStrategy ioStrategy) {
        this.ioStrategy = ioStrategy;
    }

    public void releaseOutputStrategy(Resume resume, OutputStream outputStream) {
        try {
            ioStrategy.doWrite(resume, outputStream);
        } catch (IOException e) {
            throw new StorageException("Can't write file", resume.getUuid(), e);
        }
    }

    public void releaseInputStrategy(InputStream inputStream) {
        try {
            ioStrategy.doRead(inputStream);
        } catch (IOException e) {
            throw new StorageException("Can't read file", null, e);
        }
    }
}
