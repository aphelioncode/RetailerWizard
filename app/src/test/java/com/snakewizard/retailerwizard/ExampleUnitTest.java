package com.snakewizard.retailerwizard;

import com.snakewizard.retailerwizard.apputil.AppUtil;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void strip_isCorrect(){
        assert("abc".equals(AppUtil.strip("  abc  ")));
        assert("abc".equals(AppUtil.strip("abc  ")));
        assert("abc".equals(AppUtil.strip("  abc")));
        assert("".equals(AppUtil.strip("   ")));
        assert("".equals(AppUtil.strip("")));
    }
}