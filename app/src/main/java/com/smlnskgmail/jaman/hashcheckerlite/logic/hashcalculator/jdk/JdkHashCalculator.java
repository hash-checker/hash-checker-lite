package com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.jdk;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.HashCalculator;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.HashType;
import com.smlnskgmail.jaman.hashcheckerlite.logic.logs.L;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class JdkHashCalculator implements HashCalculator {

    private JdkHashCalculatorDigest jdkHashCalculatorDigest;

    @Override
    public void setHashType(
            @NonNull HashType hashType
    ) throws NoSuchAlgorithmException {
        this.jdkHashCalculatorDigest = JdkHashCalculatorDigest.instanceFor(hashType);
    }

    @Nullable
    @Override
    public String fromString(@NonNull String text) {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        jdkHashCalculatorDigest.update(bytes);
        return jdkHashCalculatorDigest.result();
    }

    @Nullable
    @Override
    public String fromFile(
            @NonNull Context context,
            @NonNull Uri path
    ) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(path);
            if (inputStream != null) {
                try {
                    byte[] buffer = new byte[1024];
                    int read;
                    do {
                        read = inputStream.read(buffer);
                        if (read > 0) {
                            jdkHashCalculatorDigest.update(
                                    buffer,
                                    read
                            );
                        }
                    } while (read != -1);
                    return jdkHashCalculatorDigest.result();
                } catch (IOException e) {
                    L.e(e);
                }
            }
        } catch (Exception e) {
            L.e(e);
        }
        return null;
    }

}
