package com.smlnskgmail.jaman.hashcheckerlite.components;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.runner.AndroidJUnit4;

import com.smlnskgmail.jaman.hashcheckerlite.components.matchers.TextMatcher;
import com.smlnskgmail.jaman.hashcheckerlite.logic.logs.L;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public abstract class BaseUITest {

    protected static final int SECOND_IN_MILLIS = 2000;

    @Test
    public abstract void runTest() throws Exception;

    protected void clickById(int id) {
        onView(withId(id)).perform(click());
    }

    protected void clickByText(@NonNull String text) {
        onView(withText(text)).perform(click());
    }

    protected void textEquals(@NonNull String text, int textViewId) {
        onView(withId(textViewId)).check(
                matches(TextMatcher.hasStringEqualsTo(text))
        );
    }

    @SuppressWarnings("SameParameterValue")
    protected void inRecyclerViewClickOnPosition(int recyclerId, int position) {
        onView(withId(recyclerId)).perform(
                        RecyclerViewActions.actionOnItemAtPosition(
                                position,
                                click()
                        )
        );
    }

    protected Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    protected void delayAndBack() {
        secondDelay();
        pressBack();
        secondDelay();
    }

    protected void secondDelay() {
        try {
            Thread.sleep(SECOND_IN_MILLIS);
        } catch (InterruptedException e) {
            L.e(e);
        }
    }

}
