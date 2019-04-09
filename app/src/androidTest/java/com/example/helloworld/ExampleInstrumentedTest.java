package com.example.helloworld;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.os.SystemClock;



import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Button;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Rule;


import com.microsoft.appcenter.espresso.Factory;
import com.microsoft.appcenter.espresso.ReportHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.List;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;

import androidx.test.rule.GrantPermissionRule;

import androidx.test.uiautomator.UiObject2;
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


    @Rule
    public GrantPermissionRule permissionRule1 = GrantPermissionRule.grant(Manifest.permission.READ_EXTERNAL_STORAGE);
    @Rule
    public GrantPermissionRule permissionRule2 = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);

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

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void copyAssets() {
        AssetManager assetManager = getApplicationContext().getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        if (files != null) for (String filename : files) {
            Log.e("srinivas", filename);
            if (filename.contains(".apk") != true) {
                continue;
            }
            Log.e("srinivas attempting copy ", filename);
            InputStream in = null;
            OutputStream out = null;
            try {
                    in = assetManager.open(filename);
                    //File outFile = new File(getApplicationContext().getExternalFilesDir(null), filename + "srinivas");
                    File outFile = new File(Environment.getExternalStorageDirectory()+ "/Download/" , filename);
                    //File outFile = new File("/data/local/tmp", filename);
                    Log.e("srinivas", outFile.getAbsolutePath() + " " +  Environment.getExternalStorageDirectory() + " " + getApplicationContext().getExternalFilesDir(null));
                    out = new FileOutputStream(outFile);
                    copyFile(in, out);
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
            }
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
    @Test
    public void installFlash() {
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        copyAssets();
        //injectInstrumentation(getInstrumentation());
        //verifyStoragePermissions(ge());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Download/" + "Flash.apk")), "application/vnd.android.package-archive");
        //intent.setDataAndType(Uri.fromFile(new File(getApplicationContext().getExternalFilesDir(null) + "Flash.apk")), "application/vnd.android.package-archive");
        //intent.setDataAndType(Uri.fromFile(new File("/data/local/tmp/Flash.apk")), "application/vnd.android.package-archive");
        Log.e("srinivas", "Before sleep-1");
        getApplicationContext().startActivity(intent);
        mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.wait(Until.hasObject(By.pkg("com.android.packageinstaller").depth(0)), LAUNCH_TIMEOUT);
        SystemClock.sleep(1500);
        Log.e("srinivas", "After sleep-1");



        // We might get a system dialog prompt for install unknown apps
        UiObject2 SettingsButton = mDevice.findObject(By.res("android", "button1"));//.click();
        if (SettingsButton != null) {
            SettingsButton.click();

            SystemClock.sleep(1500);
            // Turn on the widget5 to allow apps from this source
            UiObject2 allowWidget = mDevice.findObject(By.res("android", "switch_widget"));
            allowWidget.click();
            SystemClock.sleep(1500);
            mDevice.pressBack();
        }
        Log.e("srinivas", "Before sleep-2");
        SystemClock.sleep(1500);

        mDevice.findObject(By.res("com.android.packageinstaller", "ok_button")).click();
        SystemClock.sleep(6000);
        Log.e("srinivas", "After sleep-2");
        mDevice.pressHome();

        // Install fsecure apk
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Download/" + "fsecure.apk")), "application/vnd.android.package-archive");
        //intent.setDataAndType(Uri.fromFile(new File(getApplicationContext().getExternalFilesDir(null) + "Flash.apk")), "application/vnd.android.package-archive");
        //intent.setDataAndType(Uri.fromFile(new File("/data/local/tmp/Flash.apk")), "application/vnd.android.package-archive");
        Log.e("srinivas", "Before sleep-1");
        getApplicationContext().startActivity(intent);
        mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.wait(Until.hasObject(By.pkg("com.android.packageinstaller").depth(0)), LAUNCH_TIMEOUT);
        SystemClock.sleep(1500);
        Log.e("srinivas", "After sleep-1");
        Log.e("srinivas", "Before sleep-2");
        SystemClock.sleep(1500);

        mDevice.findObject(By.res("com.android.packageinstaller", "ok_button")).click();
        SystemClock.sleep(6000);
        Log.e("srinivas", "After sleep-2");
        mDevice.pressHome();


        Intent intent1 = new Intent(Intent.ACTION_MAIN, null);
        intent1.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> apps = getApplicationContext().getPackageManager().queryIntentActivities( intent1 , 0);

        for (int i = 0; i < apps.size(); i++) {

            Log.e("srinivas package name is", apps.get(i).toString());
        }
    }

    @Test
    public void checkCountUp() {
        mDevice = UiDevice.getInstance(getInstrumentation());
        Context context = getApplicationContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.example.helloworld");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //intent.setData(Uri.parse("https://www.apkmirror.com/wp-content/themes/APKMirror/download.php?id=654038"));
        context.startActivity(intent);
        mDevice.wait(Until.hasObject(By.pkg("com.example.helloworld").depth(0)), LAUNCH_TIMEOUT);
        mDevice.findObject(By.res("com.example.helloworld", "button")).click();
    }

/*
    @Test
    public void installFlash2() {
        try {


            mDevice = UiDevice.getInstance(getInstrumentation());
            mDevice.executeShellCommand("pm install -t -r /storage/emulated/0/Android/data/com.example.helloworld/files/Flash.apk");
            //mDevice.executeShellCommand("pm install -t -r /data/local/tmp/Flash.apk");
        }
        catch (IOException e) {
            Log.e("srinivas", "Failed to install apk file: " +  e);
        }
    }
*/

/*
    @Test
    public void launchChrome() {

        mDevice = UiDevice.getInstance(getInstrumentation());
        Context context = getApplicationContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.android.chrome");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setData(Uri.parse("https://www.apkmirror.com/wp-content/themes/APKMirror/download.php?id=654038"));
        context.startActivity(intent);
        mDevice.wait(Until.hasObject(By.pkg("com.android.chrome").depth(0)), LAUNCH_TIMEOUT);
        UiObject2 termsButton = mDevice.findObject(By.res("com.android.chrome", "terms_accept"));
        if (termsButton != null) {
            termsButton.click();
            UiObject2 nextButton = mDevice.findObject(By.res("com.android.chrome", "next_button"));
            if (nextButton != null) {
                nextButton.click();
            }
            UiObject2 negButton = mDevice.findObject(By.res("com.android.chrome", "negative_button"));
            if (nextButton != null) {
                negButton.click();
            }
        }

    }
*/
/*
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
*/

}
