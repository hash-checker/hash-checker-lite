package com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator;

import android.content.Context;

import androidx.annotation.NonNull;

import com.smlnskgmail.jaman.hashcheckerlite.R;
import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.ListItem;

public enum HashType implements ListItem {

    MD5("MD5"),
    SHA_1("SHA-1"),
    SHA_224("SHA-224"),
    SHA_256("SHA-256"),
    SHA_384("SHA-384"),
    SHA_512("SHA-512"),
    CRC_32("CRC-32");

    private final String hashName;

    HashType(String hashName) {
        this.hashName = hashName;
    }

    @NonNull
    public String getHashName() {
        return hashName;
    }

    @NonNull
    public String getTypeAsString() {
        return hashName;
    }

    @NonNull
    public static HashType getHashTypeFromString(@NonNull String string) {
        for (HashType hashType : values()) {
            if (hashType.hashName.equals(string)) {
                return hashType;
            }
        }
        return MD5;
    }

    @NonNull
    @Override
    public String getTitle(@NonNull Context context) {
        return hashName;
    }

    @Override
    public int getPrimaryIconResId() {
        return DEFAULT_ICON_VALUE;
    }

    @Override
    public int getAdditionalIconResId() {
        return R.drawable.ic_done;
    }

}
