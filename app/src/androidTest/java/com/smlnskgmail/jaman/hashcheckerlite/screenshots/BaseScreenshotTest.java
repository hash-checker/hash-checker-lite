package com.smlnskgmail.jaman.hashcheckerlite.screenshots;

import androidx.annotation.NonNull;

import com.smlnskgmail.jaman.hashcheckerlite.components.BaseUITest;

import org.junit.Before;

import tools.fastlane.screengrab.Screengrab;
import tools.fastlane.screengrab.UiAutomatorScreenshotStrategy;

public abstract class BaseScreenshotTest extends BaseUITest {

    @Before
    public void setup() {
        Screengrab.setDefaultScreenshotStrategy(
                new UiAutomatorScreenshotStrategy()
        );
    }

    public void makeScreenshot(
            @NonNull String screenshotName
    ) {
        secondDelay();
        Screengrab.screenshot(
                screenshotName
        );
    }

}
