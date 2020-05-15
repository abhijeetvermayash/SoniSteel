package com.example.sonisteel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Admin extends AppCompatActivity {
    TextInputLayout email,password;
    Button btnsign;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        email=(TextInputLayout) findViewById((R.id.email));
        password=(TextInputLayout)findViewById((R.id.password));
        btnsign=(Button)findViewById(R.id.btnsign);
         firebaseAuth=FirebaseAuth.getInstance();
        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mailid=email.getEditText().getText().toString().trim();
                String pass=password.getEditText().getText().toString().trim();

                if(TextUtils.isEmpty(mailid)){
                    Toast.makeText(Admin.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(Admin.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                }

                firebaseAuth.signInWithEmailAndPassword(mailid, pass)
                        .addOnCompleteListener(Admin.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                     startActivity(new Intent(getApplicationContext(),SeeDetails.class));
                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(Admin.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }

                                // ...
                            }
                        });





            }
        });
    }

}
