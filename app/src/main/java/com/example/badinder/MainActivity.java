package com.example.badinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.badinder.cards.Card;
import com.example.badinder.cards.CardsArrayAdapter;
import com.example.badinder.matches.MatchesActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Card[] cardsData;
    private CardsArrayAdapter arrayAdapter;
    private int i;

    private FirebaseAuth mAuth;

    private String userSex;
    private String notUserSex;
    private String currentUserId;

    private DatabaseReference usersDB;

    private ListView listView;
    private List<Card> cards;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usersDB = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getUid();
        checkUserSex();

        cards = new ArrayList<Card>();
        arrayAdapter = new CardsArrayAdapter(this, R.layout.item, cards);

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        if (cards.size() == 0) {

        }

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                cards.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Card obj = (Card) dataObject;
                String userId = obj.getUserId();
                usersDB.child(userId).child("connections").child("Не нравится").child(currentUserId).setValue(true);
                Toast.makeText(MainActivity.this, "Like", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Card obj = (Card) dataObject;
                String userId = obj.getUserId();
                usersDB.child(userId).child("connections").child("Нравится").child(currentUserId).setValue(true);
                isConnectionMatch(userId);
                Toast.makeText(MainActivity.this, "Like", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                    Toast.makeText(MainActivity.this, "Больше нет непроверенных анкет.", Toast.LENGTH_LONG);
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });

        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(MainActivity.this, "Ну не кликай", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void isConnectionMatch(String userId) {
        DatabaseReference currentUserConnectionsDb = usersDB.child(currentUserId).child("connections").child("like").child(userId);
        currentUserConnectionsDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(MainActivity.this, "У вас есть совпадение!", Toast.LENGTH_LONG).show();

                    String key = FirebaseDatabase.getInstance().getReference().child("Chat").push().getKey();

                    usersDB.child(snapshot.getKey()).child("connections").child("matches").child(currentUserId).child("ChatId").setValue(key);

                    usersDB.child(currentUserId).child("connections").child("matches").child(snapshot.getKey()).child("ChatId").setValue(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    public void checkUserSex() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userDb = usersDB.child(user.getUid());
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.child("sex").getValue() != null) {
                        userSex = snapshot.child("sex").getValue().toString();
                        notUserSex = (userSex.equals("male")) ? "female" : "male";
                    }
                    getOppositesBySex();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void getOppositesBySex() {
        usersDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists() && !snapshot.child("connections").child("dislike").hasChild(currentUserId) &&
                        !snapshot.child("connections").child("like").hasChild(currentUserId) &&
                        snapshot.hasChild("profileImageUrl") && snapshot.child("sex").getValue().toString().equals(notUserSex)) {

                    String profileImageUrl = "default";
                    if(!snapshot.child("profileImageUrl").getValue().equals("default")) {
                        profileImageUrl = snapshot.child("profileImageUrl").getValue().toString();
                    }
                    Card item = new Card(snapshot.getKey(), snapshot.child("name").getValue().toString(), profileImageUrl);
                    cards.add(item);
                    arrayAdapter.notifyDataSetChanged();


                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void logoutUser(View view) {
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this, LoginRegistrationActivity.class);
        startActivity(intent);
        finish();
        return;
    }

    public void goToSettings(View view) {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(intent);
        return;
    }

    public void goToMatches(View view) {
        Intent intent = new Intent(MainActivity.this, MatchesActivity.class);
        startActivity(intent);
        return;
    }
}