package com.smlnskgmail.jaman.hashcheckerlite.screenshots;

import androidx.test.runner.AndroidJUnit4;

import com.smlnskgmail.jaman.hashcheckerlite.R;

import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class HashActionsScreenshotTest extends BaseScreenshotTest {

    @Override
    public void runTest() {
        clickById(R.id.btn_hash_actions);
        makeScreenshot(
                "3_hash_actions"
        );
    }

}
