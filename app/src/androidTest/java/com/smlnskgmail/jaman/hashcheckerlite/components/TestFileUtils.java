package com.smlnskgmail.jaman.hashcheckerlite.components;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestFileUtils {

    public static final String DOWNLOAD_DIRECTORY = "Download";
    public static final String TEST_FILE_NAME = "tokens.txt";

    public static void createTestFileInDownloadFolder() throws IOException {
        File file = Environment.getExternalStorageDirectory();
        File[] filteredFiles = file.listFiles(
                (dir, name) -> name.equals(DOWNLOAD_DIRECTORY)
        );
        assertEquals(
                1,
                filteredFiles.length
        );
        File downloads = filteredFiles[0];
        File tokensFile = new File(
                downloads.getAbsolutePath() + "/" + TEST_FILE_NAME
        );
        if (tokensFile.exists()) {
            assertTrue(tokensFile.delete());
        }
        assertTrue(tokensFile.createNewFile());
    }

}
