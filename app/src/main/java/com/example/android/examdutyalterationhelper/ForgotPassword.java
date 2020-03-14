package com.example.android.examdutyalterationhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    EditText resetEmailEditTest;
    Button resetLinkButton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        resetEmailEditTest = findViewById(R.id.reset_email);
        resetLinkButton = findViewById(R.id.reset_email_button);

        mAuth = FirebaseAuth.getInstance();
        resetLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateAndSendResetLink();
            }
        });
    }

    public void generateAndSendResetLink(){
        String email = resetEmailEditTest.getText().toString().trim();

        if(email.isEmpty()){
            resetEmailEditTest.setError("Email Required");
            resetEmailEditTest.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            resetEmailEditTest.setError("Enter A Valid Email Address");
            resetEmailEditTest.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if ((task.isSuccessful())){
                    Toast.makeText(getApplicationContext(), "Password Reset Link Sent! Please Check You Email", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ForgotPassword.this,MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Reset Link Not Sent! Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
