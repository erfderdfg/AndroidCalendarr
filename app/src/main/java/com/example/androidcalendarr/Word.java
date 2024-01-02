package com.example.androidcalendarr;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String date;
    @ColumnInfo(name = "word")
    private String mWord;

    public Word(@NonNull String word, String date, int id)
    {this.mWord = word;
        this.date = date;
        this.id = id;}

    public String getWord(){return this.mWord;}
    public String getDate(){return this.date;}
    public int getId(){return this.id;}
}
