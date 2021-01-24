package com.smlnskgmail.jaman.hashcheckerlite.screenshots;

import androidx.test.runner.AndroidJUnit4;

import com.smlnskgmail.jaman.hashcheckerlite.R;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SourceActionsScreenshotTest extends BaseScreenshotTest {

    @Test
    @Override
    public void runTest() {
        clickById(R.id.btn_generate_from);
        makeScreenshot(
                "2_source_actions"
        );
    }

}
