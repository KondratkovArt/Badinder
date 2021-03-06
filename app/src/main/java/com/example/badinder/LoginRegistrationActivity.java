package com.example.badinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginRegistrationActivity extends AppCompatActivity {

    private Button mLogin, mRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registration);

        mLogin = (Button) findViewById(R.id.login);
        mRegister = (Button) findViewById(R.id.registration);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginRegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginRegistrationActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

    }
}