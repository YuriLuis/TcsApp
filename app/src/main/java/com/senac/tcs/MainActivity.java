package com.senac.tcs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.senac.tcs.activities.CaptureActivity;

public class MainActivity extends AppCompatActivity {

    Intent activityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void abrirActivity(View v) {
        activityIntent = new Intent(this, CaptureActivity.class);
        startActivity(activityIntent);
    }

}


