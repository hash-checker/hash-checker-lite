package com.smlnskgmail.jaman.hashcheckerlite.screenshots;

import androidx.test.runner.AndroidJUnit4;

import com.smlnskgmail.jaman.hashcheckerlite.R;

import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class HashTypesScreenshotTest extends BaseScreenshotTest {

    @Override
    public void runTest() {
        clickById(R.id.tv_selected_hash_type);
        makeScreenshot(
                "4_hash_types"
        );
    }

}
