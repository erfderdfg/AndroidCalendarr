package com.example.androidcalendarr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    CalendarView simpleCalendarView;
    private WordViewModel mWordViewModel;
    String mDate;
    private Integer mMaxId;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    androidx.lifecycle.Observer<? super List<Word>> mObserver;
    LiveData<List<Word>> mTaskList;
private void observeTaskAtDate(final WordListAdapter adapter,String date) {
        //must remove existing observer before observe//mWordViewModel.getCertainTasks(date).removeObservers(MainActivity.this);
                if(mTaskList != null) {
            mTaskList.removeObserver(mObserver);
            }
        mTaskList = mWordViewModel.getCertainTasks(date);
        Log.i("test","re-observe for " + date);
        mTaskList.observe(this, mObserver);
        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WordListAdapter adapter = new WordListAdapter(new WordListAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        mObserver = words -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(words);
            Log.i("test","submitList total size: " + words.size());
            };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMaxId=1;
        mWordViewModel.getMaxId().observe(this, maxId -> {
            mMaxId = maxId;
            Log.i("test","Max id changed to "+maxId);
        });
        simpleCalendarView = (CalendarView) findViewById(R.id.simpleCalendarView); // get the reference of CalendarView
        // perform setOnDateChangeListener event on CalendarView
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //observe and callback to system when date changes and run
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // display the selected date by using a toast
                mDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + (month + 1) + "/" + year, Toast.LENGTH_LONG).show();
                mDate = getDateField(dayOfMonth ,month + 1,year);
                //Toast.makeText(getApplicationContext(), dayOfMonth + "/" + (month + 1) + "/" + year, Toast.LENGTH_LONG).show();
                observeTaskAtDate(adapter,mDate);
            }
        });
        mDate = getDateField(simpleCalendarView.getDate());
        observeTaskAtDate(adapter,mDate);
        //if user didn't select another, show item for initialized focus date
        Log.i("test","Initial selected date= "+mDate);
        FloatingActionButton fab = findViewById(R.id.fab);

        ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>(){
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // Here, no request code
                            Intent data = result.getData();
                            String rawWord = data.getStringExtra(NewWordActivity.EXTRA_REPLY);
                            Word word = new Word(rawWord, mDate, mMaxId+1);
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
                    });
        fab.setOnClickListener( view -> {
            startForResult.launch(new Intent(MainActivity.this, NewWordActivity.class));
        });
    }

    private String getDateField(int dayOfMonth,int month, int year) {
        return dayOfMonth + "/" + month + "/" + year;
        }
private String getDateField(long selectedDateMs) {
        Date date = new Date(selectedDateMs);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Log.i("test", "calendar.get(Calendar.DAY_OF_MONTH)=" + calendar.get(Calendar.DAY_OF_MONTH)
                +" calendar.get(Calendar.DAY_OF_MONTH)=" + calendar.get(Calendar.DAY_OF_MONTH)
                +"calendar.get(Calendar.YEAR)=" + calendar.get(Calendar.YEAR));
        return calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1)
                +"/" + calendar.get(Calendar.YEAR);
    }
}
