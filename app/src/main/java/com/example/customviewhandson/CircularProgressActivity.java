package com.example.customviewhandson;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.customviewhandson.customviews.CircularProgressTickView;

import androidx.appcompat.app.AppCompatActivity;

public class CircularProgressActivity extends AppCompatActivity implements View.OnClickListener {

    CircularProgressTickView circularProgressTickView;
    Button animateButton;
    private boolean isAnimationRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circular_progress_activity_main);
        circularProgressTickView = findViewById(R.id.circular_progress);
        animateButton = findViewById(R.id.animateButton);
        animateButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (isAnimationRunning) {
            circularProgressTickView.stopCircleAnimation();
            animateButton.setText(R.string.start);
            isAnimationRunning = false;
        }
        else {
            circularProgressTickView.startCircleAnimation();
            animateButton.setText(R.string.stop);
            isAnimationRunning = true;
        }
    }
}
