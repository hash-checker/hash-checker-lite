package com.smlnskgmail.jaman.hashcheckerlite;

import com.smlnskgmail.jaman.hashcheckerlite.calculator.HashTypeTest;
import com.smlnskgmail.jaman.hashcheckerlite.calculator.jdk.JdkHashCalculatorDigestTest;
import com.smlnskgmail.jaman.hashcheckerlite.calculator.jdk.JdkHashCalculatorTest;
import com.smlnskgmail.jaman.hashcheckerlite.calculator.jdk.JdkHashToolsTest;
import com.smlnskgmail.jaman.hashcheckerlite.tools.TextToolsTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        JdkHashToolsTest.class,
        HashTypeTest.class,
        JdkHashCalculatorDigestTest.class,
        JdkHashCalculatorTest.class,
        TextToolsTest.class,
})
public class TestSuite {}
