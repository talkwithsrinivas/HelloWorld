package com.example.helloworld;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;


import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Rule;


import com.microsoft.appcenter.espresso.Factory;
import com.microsoft.appcenter.espresso.ReportHelper;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;

import androidx.test.uiautomator.Until;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class
ExampleInstrumentedTest {
    private UiDevice mDevice;
    private static final int LAUNCH_TIMEOUT = 5000;


    @Rule
    public ReportHelper reportHelper = Factory.getReportHelper();
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = getApplicationContext();
        Log.e("srinivas", "isEmulator returns " +  isEmulator());
        Log.e("srinivas", "Build.Model is  " +  Build.MODEL);
        Log.e("srinivas", "Build.Manufacturer is  " +  Build.MANUFACTURER);
        Log.e("srinivas", "Build.Brand is  " +  Build.BRAND);
        Log.e("srinivas", "Build.PRODUCT is  " +  Build.PRODUCT);
        //Log.e("srinivas", "Build.Serial is  " +  Build.getSerial());
        //TelephonyManager telephonyManager = (TelephonyManager)appContext.getSystemService(Context.TELEPHONY_SERVICE);
        //Log.e("srinivas"," imei is " + telephonyManager.getDeviceId());
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

    @Test
    public void launchPlayStore() {
        mDevice = UiDevice.getInstance(getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        Context context = getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage("com.android.vending");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);
        mDevice.wait(Until.hasObject(By.pkg("com.android.vending").depth(0)), LAUNCH_TIMEOUT);
        mDevice.findObject(By.res("com.android.vending", "search_box_idle_text"))
                .setText("eicar test virus");
        mDevice.findObject(By.res("com.android.vending", "search_box_idle_text")).click();
        SystemClock.sleep(1500);
        mDevice.findObject(By.res("com.android.vending", "search_box_text_input"))
                .setText("Zoner AntiVirus Test");
        SystemClock.sleep(1500);
        mDevice.pressEnter();
        SystemClock.sleep(1500);
        mDevice.findObject(By.res("com.android.vending", "play_card"))
                .click();
        SystemClock.sleep(1500);
        mDevice.findObject(By.text("INSTALL"))
                .click();
        SystemClock.sleep(1500);
        mDevice.findObject(By.text("ACCEPT"))
                .click();
        SystemClock.sleep(1500);
        final Intent intent1 = context.getPackageManager()
                .getLaunchIntentForPackage("com.example.linearlayout");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent1);
        SystemClock.sleep(1500);
        mDevice.findObject(By.res("com.example.linearlayout", "button5"))
                .click();
        SystemClock.sleep(1500);
        mDevice.findObject(By.res("com.example.linearlayout", "scan_btn"))
                .click();
        SystemClock.sleep(5000);
        assertNotNull(mDevice.findObject(By.textContains("Zoner AntiVirus Test is INFECTED with EICAR")));
    }


}
