package com.nocode.document;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.nocode.utils.FileUtil;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class TextFileVectorTest {

    @Test
    void getTokenMapVector() {
        try {
            List<Path> filePathList = FileUtil.getPathsFromResource("data");
            for(Path inputFile : filePathList) {
                TextFileVector textFileVector = new TextFileVector(inputFile.toFile());
                assertNotNull(textFileVector);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}