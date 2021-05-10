package com.smlnskgmail.jaman.hashcheckerlite.tools;

import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.TextUtils;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TextUtilsTest {

    @Test
    public void compareText() {
        assertTrue(
                TextUtils.compareText(
                        "equals",
                        "equals"
                )
        );
        assertTrue(
                TextUtils.compareText(
                        "Blob",
                        "BLOB"
                )
        );
        assertFalse(
                TextUtils.compareText(
                        "equals",
                        "blob"
                )
        );
    }

}
