package com.example.customviewhandson;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.customviewhandson.customviews.CircleView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class LauncherActivity extends AppCompatActivity implements View.OnClickListener {

    Button circleView;
    Button circularProgressTickView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        circleView = findViewById(R.id.bv_circle_view);
        circularProgressTickView = findViewById(R.id.bv_circular_progress_tick);
        circleView.setOnClickListener(this);
        circularProgressTickView.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = null;
        switch (id) {
            case R.id.bv_circle_view:
                intent = new Intent(this, CircleViewActivity.class);
                break;
            case R.id.bv_circular_progress_tick:
                intent = new Intent(this, CircularProgressActivity.class);
                break;
            default:
                break;
        }
        launchActivity(intent);

    }

    void launchActivity(Intent intent) {
        startActivity(intent);
    }
}
