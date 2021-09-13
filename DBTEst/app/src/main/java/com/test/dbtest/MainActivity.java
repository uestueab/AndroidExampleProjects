package com.test.dbtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.test.dbtest.model.entity.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tv_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_main = findViewById(R.id.tv_main);

        NoteViewModel model = new ViewModelProvider(this).get(NoteViewModel.class);
    }
}