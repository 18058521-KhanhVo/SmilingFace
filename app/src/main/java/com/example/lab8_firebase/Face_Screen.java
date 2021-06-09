package com.example.lab8_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Face_Screen extends AppCompatActivity {

    private ImageView iv_smile, iv_normal, iv_bored;
    private Button btn_finish;
    private String result;
    private User user;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face__screen);

        btn_finish = findViewById(R.id.face_finish);
        iv_smile = findViewById(R.id.face_smile);
        iv_normal = findViewById(R.id.face_normal);
        iv_bored = findViewById(R.id.face_bored);

        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        iv_smile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = "smile";
            }
        });

        iv_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = "normal";
            }
        });

        iv_bored.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = "bored";
            }
        });

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (result == null || result.equals("")) {
                    Toast.makeText(Face_Screen.this, "Are you feeling happy today? ", Toast.LENGTH_SHORT).show();
                } else if (result.equals("smile")) {
                    databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("smile").setValue(user.getSmile() + 1);
                    Toast.makeText(Face_Screen.this, "Well that makes me happy too", Toast.LENGTH_SHORT).show();
                    result = null;
                } else if (result.equals("normal")) {
                    databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("normal").setValue(user.getNormal() + 1);
                    Toast.makeText(Face_Screen.this, "Dear friend, I wish you all the best on this day.", Toast.LENGTH_SHORT).show();
                    result = null;
                } else if (result.equals("bored")) {
                    databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("bored").setValue(user.getBored() + 1);
                    Toast.makeText(Face_Screen.this, "Want to get laid? I’m pretty bored too.", Toast.LENGTH_SHORT).show();
                    result = null;
                } else {

                }
//                    Toast.makeText(Face_Screen.this, user.getEmail() + "/" + user.getSmile() + "/" + user.getNormal() + "/" + user.getBored(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}