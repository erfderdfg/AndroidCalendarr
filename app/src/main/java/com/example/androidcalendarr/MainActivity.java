package com.example.androidcalendarr;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import java.util.Date;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    CalendarView simpleCalendarView;
    private WordViewModel mWordViewModel;
    private String mDate;
private Integer mMaxId;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private void obseveSelectedTasks(String date){
        mWordViewModel.getCertainTasks(mDate).observe(this, words -> {
            // Update the cached copy of the words in the adapter.
            Log.i("test","total size "+words.size());
            for (int i = 0; i < words.size(); i++) {
                Word word=words.get(i);
                Log.i("test", word.getWord()+" date "+word.getDate()+" id "+ word.getId());

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        obseveSelectedTasks(mDate);
        mWordViewModel.getMaxId().observe(this, maxId -> {
            mMaxId = maxId;
            Log.i("test","Max id changed to "+maxId);
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleCalendarView = (CalendarView) findViewById(R.id.simpleCalendarView); // get the reference of CalendarView
        // perform setOnDateChangeListener event on CalendarView
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // display the selected date by using a toast
                mDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + (month + 1) + "/" + year, Toast.LENGTH_LONG).show();
                LiveData<List<Word>> allWords = mWordViewModel.getAllWords();
                List<Word> lstofWords = allWords.getValue();
                Log.i("test","size "+lstofWords.size());
                Intent intent = new Intent(MainActivity.this, DisplayTasks.class);
                intent.putExtra("extrareply", mDate);
                startActivityForResult(intent, 10);
            }
        });
        long selectedDateMs=simpleCalendarView.getDate();
        Date date=new Date(selectedDateMs);
        mDate = date.getDay() + "/" + (date.getMonth() + 1) + "/" + date.getYear();
        Log.i("test","Initial selected date= "+mDate);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener( view -> {
            Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY), mDate, mMaxId+1);
            Log.i("test", "maxid: "+(mMaxId+1));
            mWordViewModel.insert(word);
            Log.i("test","insert "+word.getWord()+" date "+word.getDate()+" id "+ word.getId());
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
