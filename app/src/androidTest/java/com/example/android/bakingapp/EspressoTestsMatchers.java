package com.example.android.bakingapp;
//Reference: https://github.com/dbottillo/Blog/blob/espresso_match_imageview/app/src/androidTest/java/com/danielebottillo/blog/config/DrawableMatcher.java
import android.view.View;

import org.hamcrest.Matcher;

public class EspressoTestsMatchers {
    public static Matcher<View> withDrawable(final int resourceId) {
        return new DrawableMatcher(resourceId);
    }

    public static Matcher<View> noDrawable() {
        return new DrawableMatcher(-1);
    }
}
