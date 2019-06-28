package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private IOStrategy ioStrategy;

    public PathStorage(String dir, IOStrategy ioStrategy) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "Directory can't be null");
        Objects.requireNonNull(ioStrategy, "Input/Output strategy can't be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is't directory or is't writable");
        }
        this.ioStrategy = ioStrategy;
    }


    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory + "/" + uuid);
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
        try {
            ioStrategy.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Can't write path " + path.getFileName(), resume.getUuid(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Can't create path " + path.getFileName(), resume.getUuid(), e);
        }
        doUpdate(resume, path);
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Can't delete path " + path.getFileName(), path.toFile().getName(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path, LinkOption.NOFOLLOW_LINKS);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return ioStrategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Can't read path", path.toFile().getName(), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Path> paths;
        try {
            paths = Files.list(directory).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Can't read directory" + directory.getFileName(), null, e);
        }
        List<Resume> list = new ArrayList<>(paths.size());
        for (Path path : paths) {
            list.add(doGet(path));
        }
        return list;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Can't delete path" + directory.getFileName(), null, e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory reed Error", null, e);
        }
    }
}