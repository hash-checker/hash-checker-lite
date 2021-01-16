package com.smlnskgmail.jaman.hashcheckerlite.appopen;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

public class OpenAppWithIntentTest extends BaseOpenAppTest {

    @NonNull
    @Override
    protected Intent getIntentForTest() {
        Intent startIntent = new Intent();
        startIntent.setData(
                Uri.fromFile(
                        getTestFile()
                )
        );
        return startIntent;
    }

}
