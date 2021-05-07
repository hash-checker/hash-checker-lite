package com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.security.NoSuchAlgorithmException;

public interface HashCalculator {

    void setHashType(@NonNull HashType hashType) throws NoSuchAlgorithmException;

    @Nullable
    String fromString(@NonNull String text);

    @Nullable
    String fromFile(
            @NonNull Context context,
            @NonNull Uri path
    );

}
