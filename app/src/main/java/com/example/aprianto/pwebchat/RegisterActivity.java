
package com.example.aprianto.pwebchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference userRef = database.getReference("users");

    EditText etName, etTlp, etEmail;
    TextView btLogin;
    Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText) findViewById(R.id.etName);
        etTlp = (EditText) findViewById(R.id.etTlp);
        etEmail = (EditText) findViewById(R.id.etEmail);
        btLogin = (TextView) findViewById(R.id.btLogin);
        btRegister = (Button) findViewById(R.id.btRegister);


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setNama( etName.getText().toString() );
                user.setTelepon( etTlp.getText().toString() );
                user.setEmail( etEmail.getText().toString() );

                user.register();
                Toast.makeText(RegisterActivity.this, "registration successful", Toast.LENGTH_LONG).show();
                etName.setText("");
                etTlp.setText("");
                etEmail.setText("");
            }
        });
    }
}
