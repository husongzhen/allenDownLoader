package com.allen.code.allendownloader;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ScrollingActivityTest {

    @Rule
    public ActivityTestRule<ScrollingActivity> mActivityTestRule = new ActivityTestRule<>(ScrollingActivity.class);

    @Test
    public void scrollingActivityTest() {
    }

}
