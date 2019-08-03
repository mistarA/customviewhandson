package com.example.customviewhandson;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import com.example.customviewhandson.customviews.CircleAnimatorView;
import com.example.customviewhandson.customviews.CircleView;
import com.example.customviewhandson.customviews.MyCustomView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CircleView circleView;
    Button animateButton;
    boolean isAnimationRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        circleView = findViewById(R.id.circle_view);
        animateButton = findViewById(R.id.id_animate_bv);
        animateButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (isAnimationRunning) {
            circleView.stopAnimation();
            circleView.setVisibility(View.GONE);
            animateButton.setText(R.string.start);
            isAnimationRunning = false;
        }
        else {
            circleView.setVisibility(View.VISIBLE);
            circleView.startAnimation();
            animateButton.setText(R.string.stop);
            isAnimationRunning = true;
        }

    }
}
