package com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.security.NoSuchAlgorithmException;

public interface HashCalculator {

    void setHashType(@NonNull HashType hashType) throws NoSuchAlgorithmException;

    String fromString(@NonNull String text);

    String fromFile(
            @NonNull Context context,
            @NonNull Uri path
    );

}
