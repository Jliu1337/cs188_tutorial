package com.remjeyinc.remjeyliu.beautifulbulldog;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class BulldogActivity extends Activity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulldog);

        textView = (TextView) findViewById(R.id.textView);

        Bulldog bulldog = (Bulldog) getIntent().getSerializableExtra("bulldog");
        textView.setText(bulldog.getName());
    }
}
