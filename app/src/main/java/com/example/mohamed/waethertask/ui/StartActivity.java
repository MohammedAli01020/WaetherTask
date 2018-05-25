package com.example.mohamed.waethertask.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.mohamed.waethertask.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.tv_map)
    TextView mapTextView;

    @BindView(R.id.tv_list)
    TextView listTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ButterKnife.bind(this);

        mapTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startMap = new Intent(StartActivity.this, MapActivity.class);
                startActivity(startMap);
            }
        });

        listTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startList = new Intent(StartActivity.this, CitiesActivity.class);
                startActivity(startList);
            }
        });

    }
}
