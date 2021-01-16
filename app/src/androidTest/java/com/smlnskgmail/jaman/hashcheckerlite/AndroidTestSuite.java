package com.smlnskgmail.jaman.hashcheckerlite;

import com.smlnskgmail.jaman.hashcheckerlite.appopen.OpenAppWithClipDataTest;
import com.smlnskgmail.jaman.hashcheckerlite.appopen.OpenAppWithIntentTest;
import com.smlnskgmail.jaman.hashcheckerlite.calculator.GenerateHashFromTextTest;
import com.smlnskgmail.jaman.hashcheckerlite.calculator.jdk.crc.CRC32JdkHashCalculatorTest;
import com.smlnskgmail.jaman.hashcheckerlite.calculator.jdk.md.MD5JdkHashCalculatorTest;
import com.smlnskgmail.jaman.hashcheckerlite.calculator.jdk.sha.SHA1JdkHashCalculatorTest;
import com.smlnskgmail.jaman.hashcheckerlite.calculator.jdk.sha.SHA224JdkHashCalculatorTest;
import com.smlnskgmail.jaman.hashcheckerlite.calculator.jdk.sha.SHA256JdkHashCalculatorTest;
import com.smlnskgmail.jaman.hashcheckerlite.calculator.jdk.sha.SHA384JdkHashCalculatorTest;
import com.smlnskgmail.jaman.hashcheckerlite.calculator.jdk.sha.SHA512JdkHashCalculatorTest;
import com.smlnskgmail.jaman.hashcheckerlite.calculator.jdk.zeroleads.MessageDigestZeroLeadsJdkHashCalculatorTest;
import com.smlnskgmail.jaman.hashcheckerlite.feedback.FeedbackTest;
import com.smlnskgmail.jaman.hashcheckerlite.screenrunner.ScreenRunnerTest;
import com.smlnskgmail.jaman.hashcheckerlite.utils.ClipboardTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MD5JdkHashCalculatorTest.class,
        SHA1JdkHashCalculatorTest.class,
        SHA224JdkHashCalculatorTest.class,
        SHA256JdkHashCalculatorTest.class,
        SHA384JdkHashCalculatorTest.class,
        SHA512JdkHashCalculatorTest.class,
        CRC32JdkHashCalculatorTest.class,
        MessageDigestZeroLeadsJdkHashCalculatorTest.class,
        GenerateHashFromTextTest.class,
        OpenAppWithIntentTest.class,
        OpenAppWithClipDataTest.class,
        FeedbackTest.class,
        ClipboardTest.class,
        ScreenRunnerTest.class
})
public class AndroidTestSuite {

}
