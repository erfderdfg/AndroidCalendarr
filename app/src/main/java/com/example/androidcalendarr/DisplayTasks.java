package com.example.androidcalendarr;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayTasks extends AppCompatActivity {

    private String mDate;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mDate = data.getStringExtra(NewWordActivity.EXTRA_REPLY);}
}
