package com.smlnskgmail.jaman.hashcheckerlite.tools;

import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.TextTools;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TextToolsTest {

    @Test
    public void compareText() {
        assertTrue(
                TextTools.compareText(
                        "equals",
                        "equals"
                )
        );
        assertTrue(
                TextTools.compareText(
                        "Blob",
                        "BLOB"
                )
        );
        assertFalse(
                TextTools.compareText(
                        "equals",
                        "blob"
                )
        );
    }

}
