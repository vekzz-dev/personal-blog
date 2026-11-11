package io.github.vekzzdev.personalblog;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.vekzzdev.personalblog.exception.StorageException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Storage {

    private static final String APP_DIR = ".personalblog";
    private static final String FILE_NAME = "articles_data.json";
    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    private final Path file;
    private final Dotenv env;

    public Storage() {
        env = Dotenv.configure()
                .filename("config.env")
                .ignoreIfMissing()
                .load();

        file = initFile();
    }

    public String read() {
        try {
            return Files.readString(file, UTF_8);

        } catch (IOException e) {
            throw new StorageException("Error with reading file", e);
        }
    }

    public void write(String content) {
        try {
            Files.writeString(file, content, UTF_8);

        } catch (IOException e) {
            throw new StorageException("Error with writing file", e);
        }
    }

    private Path initFile() {
        try {
            var dir = getUserDir();
            Files.createDirectories(dir);
            var target = dir.resolve(FILE_NAME);

            if (Files.notExists(target)) Files.writeString(target, "[]", UTF_8);
            return target;

        } catch (IOException e) {
            throw new StorageException("Error initializing the storage file", e);
        }
    }

    private Path getUserDir() {
        var envDirKey = "CUSTOM_DIR";
        var customDir = System.getenv(envDirKey);

        if (customDir == null || customDir.isBlank()) customDir = env.get(envDirKey);

        return (customDir == null || customDir.isBlank())
                ? Path.of(System.getProperty("user.home"), APP_DIR)
                : Path.of(customDir);
    }
}
