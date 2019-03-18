package com.example.helloworld;

import android.content.Context;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.Rule;
import org.junit.After;
import com.microsoft.appcenter.espresso.Factory;
import com.microsoft.appcenter.espresso.ReportHelper;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class
ExampleInstrumentedTest {
    @Rule
    public ReportHelper reportHelper = Factory.getReportHelper();
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        Log.e("srinivas", "isEmulator returns " +  isEmulator());
        Log.e("srinivas", "Build.Model is  " +  Build.MODEL);
        Log.e("srinivas", "Build.Manufacturer is  " +  Build.MANUFACTURER);
        Log.e("srinivas", "Build.Brand is  " +  Build.BRAND);
        Log.e("srinivas", "Build.PRODUCT is  " +  Build.PRODUCT);
        Log.e("srinivas", "Build.Serial is  " +  Build.getSerial());
        assertEquals("com.example.helloworld", appContext.getPackageName());
        // Uncomment this to cause test failure.
        //assertEquals("com.example.helloworld1", appContext.getPackageName());
    }

    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }
}
