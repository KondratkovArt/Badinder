package com.example.badinder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.badinder.chat.ChatActivity;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class CallActivity extends AppCompatActivity {


    private EditText roomCode;

    private Button backBtn, joinBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        roomCode = findViewById(R.id.call_code);

        backBtn = (Button) findViewById(R.id.call_backBtn);

        URL serverURL;


        try {
            serverURL = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions = new JitsiMeetConferenceOptions.Builder().
                    setServerURL(serverURL).
                    setWelcomePageEnabled(true).
                    build();

            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CallActivity.this, ChatActivity.class));
                finish();
            }
        });

        joinBtn = (Button) findViewById(R.id.call_joinBtn);

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                        .setRoom(roomCode.getText().toString())
                        .setWelcomePageEnabled(true)
                        .build();

                JitsiMeetActivity.launch(CallActivity.this, options);
            }
        });


    }
}