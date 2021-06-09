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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_Screen extends AppCompatActivity {

    private Button btn_regis;
    private TextView txt_name, txt_email, txt_password, txt_confirmpassword, link_signin;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__screen);

        btn_regis = findViewById(R.id.regis_btn);
        link_signin = findViewById(R.id.regis_signin);

        txt_name = findViewById(R.id.regis_name);
        txt_email = findViewById(R.id.regis_email);
        txt_password = findViewById(R.id.regis_password);
        txt_confirmpassword = findViewById(R.id.regis_confirmpass);

        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        firebaseAuth = FirebaseAuth.getInstance();

        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txt_name.getText().toString();
                String email = txt_email.getText().toString();
                String password = txt_password.getText().toString();
                String confirmpass = txt_confirmpassword.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(Register_Screen.this, "Enter your name!", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Register_Screen.this, "Enter email address!", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Register_Screen.this, "Enter password!", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(confirmpass)) {
                    Toast.makeText(Register_Screen.this, "Enter confirm password!", Toast.LENGTH_SHORT).show();

                } else if (password.equals(confirmpass) == false) {
                    Toast.makeText(Register_Screen.this, "Password and Confirm Password ddoesn't match!", Toast.LENGTH_SHORT).show();

                } else {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Register_Screen.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(Register_Screen.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                    if (task.isSuccessful()) {
                                        User user = new User(name, email, 0, 0, 0);
                                        FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(Register_Screen.this, "Registration complete!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Register_Screen.this, Sign_in_screen.class);
                                                intent.putExtra("email", email);
                                                intent.putExtra("password", password);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                    } else {
                                        Toast.makeText(Register_Screen.this, "Registration fail!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });

        link_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Sign_in_screen.class);
                startActivity(intent);
            }
        });
    }

}