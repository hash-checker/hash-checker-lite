package com.smlnskgmail.jaman.hashcheckerlite.rateapp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.InstrumentationRegistry;

import com.smlnskgmail.jaman.hashcheckerlite.R;
import com.smlnskgmail.jaman.hashcheckerlite.components.BaseUITest;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.HashType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertTrue;

public class RateAppDialogTest extends BaseUITest {

    private static final String TEST_TEXT = "TEST";

    private static final HashType TEST_HASH_TYPE = HashType.MD5;

    private static final int TEXT_BUTTON_POSITION = 0;
    private static final int GENERATE_BUTTON_POSITION = 0;

    @Override
    public void runTest() throws InterruptedException {
        clearRateAppCounter();
        for (int i = 0; i < 6; i++) {
            showInputDialog();
            enterText();
            selectHashType();
            generateHashFromText();
        }
        secondDelay();
        checkRateAppDialog();
    }

    private void clearRateAppCounter() {
        Context context = InstrumentationRegistry.getTargetContext();
        SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName() + "_preferences",
                Context.MODE_PRIVATE
        );
        prefs.edit().putInt(
                context.getString(R.string.key_hash_generation_count),
                0
        ).apply();
    }

    private void showInputDialog() {
        clickById(R.id.btn_generate_from);
        secondDelay();

        inRecyclerViewClickOnPosition(
                R.id.rv_bottom_sheet_list_items,
                TEXT_BUTTON_POSITION
        );
        secondDelay();
    }

    private void enterText() {
        onView(withId(R.id.et_dialog_input_text)).perform(clearText());
        onView(withId(R.id.et_dialog_input_text)).perform(typeText(TEST_TEXT));
        secondDelay();

        clickById(R.id.btn_dialog_input_text_add);
        secondDelay();
    }

    private void selectHashType() {
        clickById(R.id.tv_selected_hash_type);
        secondDelay();

        List<HashType> hashTypes = new ArrayList<>(
                Arrays.asList(HashType.values())
        );
        int hashTypePosition = hashTypes.indexOf(TEST_HASH_TYPE);
        assertTrue(hashTypePosition >= 0);
        inRecyclerViewClickOnPosition(
                R.id.rv_bottom_sheet_list_items,
                hashTypePosition
        );
        textEquals(
                TEST_HASH_TYPE.getTypeAsString(),
                R.id.tv_selected_hash_type
        );
    }

    private void generateHashFromText() throws InterruptedException {
        clickById(R.id.btn_hash_actions);
        secondDelay();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        inRecyclerViewClickOnPosition(
                R.id.rv_bottom_sheet_list_items,
                GENERATE_BUTTON_POSITION
        );
        countDownLatch.await(
                SECOND_IN_MILLIS,
                TimeUnit.MILLISECONDS
        );
        secondDelay();
    }

    private void checkRateAppDialog() {
        onView(withText(R.string.rate_app_message)).check(matches(isDisplayed()));
        onView(withText(R.string.common_cancel)).perform(click());
    }

}
