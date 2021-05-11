package com.example.badinder.matches;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.badinder.MainActivity;
import com.example.badinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mMatchesAdapter;
    private RecyclerView.LayoutManager mMatchesLayoutManager;
    private TextView haveNotMatches;

    private ArrayList<MatchesObject> resultMatches = new ArrayList<MatchesObject>();
    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches2);

        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        haveNotMatches = findViewById(R.id.have_not_matches);
        haveNotMatches.setVisibility(View.INVISIBLE);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mMatchesLayoutManager = new LinearLayoutManager(MatchesActivity.this);
        mRecyclerView.setLayoutManager(mMatchesLayoutManager);
        mMatchesAdapter = new MatchesAdapter(getDataSetMatches(), MatchesActivity.this);
        mRecyclerView.setAdapter(mMatchesAdapter);

        getUserMatchId();

//        if (resultMatches.size() == 0) haveNotMatches.setVisibility(View.VISIBLE);

    }

    private void getUserMatchId() {
        DatabaseReference matchDB = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("connections").child("matches");
        matchDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot match : snapshot.getChildren()) {
                        FetchMatchInfo(match.getKey());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    private void FetchMatchInfo(String key) {
        DatabaseReference userDB = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                   if (snapshot.exists()) {
                       String userId = snapshot.getKey();
                       String name = "";
                       String profileImageUrl = "";


                       if(snapshot.child("name").getValue() != null) {
                            name = snapshot.child("name").getValue().toString();
                       }
                       if(snapshot.child("profileImageUrl").getValue() != null) {
                           profileImageUrl = snapshot.child("profileImageUrl").getValue().toString();
                       }

                       MatchesObject obj = new MatchesObject(userId, name, profileImageUrl);
                       resultMatches.add(obj);
                       mMatchesAdapter.notifyDataSetChanged();
                   }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


    private List<MatchesObject> getDataSetMatches() {
        return resultMatches;
    }
}