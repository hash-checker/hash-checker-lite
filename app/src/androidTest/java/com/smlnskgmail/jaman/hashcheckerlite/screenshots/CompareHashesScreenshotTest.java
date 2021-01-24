package com.smlnskgmail.jaman.hashcheckerlite.screenshots;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.test.runner.AndroidJUnit4;

import com.smlnskgmail.jaman.hashcheckerlite.R;
import com.smlnskgmail.jaman.hashcheckerlite.components.TestFileUtils;

import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.smlnskgmail.jaman.hashcheckerlite.components.matchers.TextMatcher.getText;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CompareHashesScreenshotTest extends BaseScreenshotTest {

    private static final int GENERATE_HASH_BUTTON_POSITION = 0;
    private static final int COMPARE_HASHES_BUTTON_POSITION = 1;

    @Override
    public void startActivity() {
        try {
            TestFileUtils.createTestFileInDownloadFolder();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent startIntent = new Intent();
        startIntent.setData(
                Uri.fromFile(
                        getTestFile()
                )
        );
        activityTestRule.launchActivity(
                startIntent
        );
    }

    @NonNull
    protected File getTestFile() {
        File file = Environment.getExternalStorageDirectory();
        File[] filteredFiles = file.listFiles(
                (dir, name) -> name.equals(TestFileUtils.DOWNLOAD_DIRECTORY)
        );
        assertEquals(
                1,
                filteredFiles.length
        );
        File downloads = filteredFiles[0];
        return new File(
                downloads.getAbsolutePath() + "/" + TestFileUtils.TEST_FILE_NAME
        );
    }

    @Override
    public void runTest() {
        clickById(R.id.btn_hash_actions);
        inRecyclerViewClickOnPosition(
                R.id.rv_bottom_sheet_list_items,
                GENERATE_HASH_BUTTON_POSITION
        );
        onView(withId(R.id.et_field_custom_hash)).perform(
                replaceText(getText(withId(R.id.et_field_generated_hash)))
        );
        clickById(R.id.btn_hash_actions);
        inRecyclerViewClickOnPosition(
                R.id.rv_bottom_sheet_list_items,
                COMPARE_HASHES_BUTTON_POSITION
        );
        makeScreenshot(
                "5_compare_hashes"
        );
    }

}
