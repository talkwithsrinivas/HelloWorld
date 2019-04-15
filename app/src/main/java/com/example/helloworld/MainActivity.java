package com.example.helloworld;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
//@SuppressWarnings("PMD")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "Hello world");

    }

    public void countUp(View v) {
        Log.i("message:", "In countUp method");
        int x = 0;
        while(true) {
            x++;
            Log.i("x =", "" + x);
            if (x == 3) {
                break;
            }
        }
        Toast.makeText(this, "Count UP button clicked", Toast.LENGTH_LONG).show();
        Log.i("message:", "Toast CountUp method");
    }


    public void someMethod(int variable) {
        switch (variable) {
            case 1:
                char ch = 'A';
                if (ch != -1) {
                    System.out.println("1");
                }

            case 2:
                System.out.println("2");
        }
        int ar[] = {1, 2, 3, 4, 5};
        //for (int i=0; i<=ar.length; i++)
        for (int i=0; i< ar.length; i++)
            System.out.println(ar[i]);
    }


}

