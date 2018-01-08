package com.example.aprianto.pwebchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfilActivity extends AppCompatActivity {

    TextView tvName, tvEmail, tvNomor;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        tvName = (TextView) findViewById(R.id.tvName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvNomor = (TextView) findViewById(R.id.tvNomor);

        user = getIntent().getParcelableExtra("user");

        tvName.setText(user.getNama());
        tvEmail.setText(user.getEmail());
        tvNomor.setText(user.getTelepon());


    }
}
