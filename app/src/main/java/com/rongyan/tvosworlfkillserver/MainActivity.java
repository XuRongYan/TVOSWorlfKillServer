package com.rongyan.tvosworlfkillserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button viewById = (Button) findViewById(R.id.btn_click);
        viewById.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "ckick", Toast.LENGTH_SHORT).show();
    }
}
