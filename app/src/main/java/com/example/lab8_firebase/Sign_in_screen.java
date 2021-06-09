package com.example.lab8_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_in_screen extends AppCompatActivity {

    private Button btnSignIn;
    private TextView txt_email, txt_password, link_register;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);

        txt_email = findViewById(R.id.sign_email);
        txt_password = findViewById(R.id.sign_pasword);

        link_register = findViewById(R.id.sign_register);
        btnSignIn = findViewById(R.id.sign_btn);

        firebaseAuth = FirebaseAuth.getInstance();
        // if (firebaseAuth.getCurrentUser() != null) {
        //    startActivity(new Intent(Sign_in_screen.this, Face_Screen.class));
        //    finish();
        //}

        String email;
        String password;
        if (getIntent().hasExtra("email") && getIntent().hasExtra("password")) {
            email = getIntent().getStringExtra("email");
            password = getIntent().getStringExtra("password");
            txt_email.setText(email);
            txt_password.setText(password);
        }

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txt_email.getText().toString();
                String password = txt_password.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Sign_in_screen.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()) {
                                        // there was an error
                                        Toast.makeText(Sign_in_screen.this, "Login fail!", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(Sign_in_screen.this, "Login success!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(Sign_in_screen.this, Face_Screen.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });


        link_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register_Screen.class);
                startActivity(intent);
            }
        });
    }
}