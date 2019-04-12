package com.example.helloworld;

import android.os.Build;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.apache.tools.ant.Main;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import static org.junit.Assert.*;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(RobolectricTestRunner.class)
public class ExampleUnitTest {
    private MainActivity activity;

    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        activity = Robolectric.setupActivity(MainActivity.class);
        // To redirect output to run log.
        ShadowLog.stream = System.out;
        Log.e("srinivas", "In setup");
    }

    @Test
    public void validateTextViewContent() {
        TextView tvHelloWorld = (TextView) activity.findViewById(R.id.HelloText);
        assertNotNull("TextView could not be found", tvHelloWorld);
        assertTrue("TextView contains incorrect text",
                "Hello World!".equals(tvHelloWorld.getText().toString()));
    }

    @Test
    public void validateCountButton() {
        Button b = (Button) activity.findViewById(R.id.button);
        assertNotNull("TextView could not be found", b);
        b.performClick();

    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}