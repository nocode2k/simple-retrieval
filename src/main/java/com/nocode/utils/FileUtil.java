package com.nocode.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtil {
    private static InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = FileUtil.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

    public static List<Path> getPathsFromResource(String directory)
            throws URISyntaxException, IOException {
        List<Path> result;
        ClassLoader classLoader = FileUtil.class.getClassLoader();
        URL resource = classLoader.getResource(directory);
        assert resource != null;
        if (!resource.getProtocol().equals("jar")) {
            result = Files.walk(Paths.get(resource.toURI()))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }else{
            String jarPath = FileUtil.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()
                    .getPath();

            URI uri = URI.create("jar:file:" + jarPath);
            List<Path> tempPathList;
            try (FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
                tempPathList = Files.walk(fs.getPath(directory))
                        .filter(Files::isRegularFile)
                        .collect(Collectors.toList());
            }

            result = new ArrayList<>();
            for(Path inputFile : tempPathList) {
                String filePathInJAR = inputFile.toString();

                if (filePathInJAR.startsWith("/")) {
                    filePathInJAR = filePathInJAR.substring(1);
                }

                InputStream inputStream = getFileFromResourceAsStream(filePathInJAR);
                Path tempFile = Files.createTempFile(inputFile.getFileName().toString(), ".tmp");
                tempFile.toFile().deleteOnExit();
                Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
                result.add(tempFile);
            }
        }
        return result;
    }
}
