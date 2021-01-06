package com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.security.NoSuchAlgorithmException;

public interface HashCalculator {

    void setHashType(@NonNull HashType hashType) throws NoSuchAlgorithmException;

    @NonNull
    String fromString(@NonNull String text);

    @NonNull
    String fromFile(
            @NonNull Context context,
            @NonNull Uri path
    );

}
