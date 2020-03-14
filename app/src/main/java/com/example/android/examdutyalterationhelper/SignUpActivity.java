package com.example.android.examdutyalterationhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    protected EditText emailEditText,pwdEditText,phoneEditText,nameEditText;
    protected TextView signUpTextView,signInTextView;
    protected FloatingActionButton floatButtonSignUp;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailEditText = findViewById(R.id.email_signup);
        pwdEditText = findViewById(R.id.pwd_signup);
        phoneEditText = findViewById(R.id.phone_signup);
        nameEditText = findViewById(R.id.name_signup);
        signUpTextView = findViewById(R.id.signup_textview_signuppage);
        signInTextView = findViewById(R.id.signin_textview_signuppage);
        floatButtonSignUp = findViewById(R.id.float_button_signup);

        mAuth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");

        floatButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }

    protected static boolean EmailCheck(String email) {
        if(email.isEmpty()){
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if(!(pat.matcher(email).matches())) {
            return false;
        }
        return true;
    }

    protected static boolean NameCheck(String name) {
        if(name.isEmpty() || name.equals(" ")){
            return false;
        }
        if(Pattern.compile( "[0-9]" ).matcher(name).find()) {
            return false;
        }
        return true;
    }

    protected static boolean PwdCheck(String pwd) {
        if(pwd.isEmpty()){
            return false;
        }
        if(pwd.length()<6){;
            return false;
        }
        return true;
    }

    protected static boolean PhoneCheck(String phone) {
        if(phone.isEmpty()){
            return false;
        }
        if(!(phone.length() == 10)){;
            return false;
        }
        if(phone.matches("^[a-zA-Z]*$")) {
            return false;
        }
        return true;
    }


    protected void registerUser(){
        final String email = emailEditText.getText().toString().trim();
        final String pwd = pwdEditText.getText().toString().trim();
        final String phone = phoneEditText.getText().toString().trim();
        final String name = nameEditText.getText().toString().trim();

        if(name.isEmpty()){
            nameEditText.setError("Name Is Required");
            nameEditText.requestFocus();
            return;
        }

        if(Pattern.compile( "[0-9]" ).matcher(name).find()) {
            nameEditText.setError("Enter a Valid Name");
            nameEditText.requestFocus();
            return;
        }

        if(email.isEmpty()){
            emailEditText.setError("Email Is Required");
            emailEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Enter A Valid Email Address");
            emailEditText.requestFocus();
            return;
        }

        if(pwd.isEmpty()){
            pwdEditText.setError("Password Is Required");
            pwdEditText.requestFocus();
            return;
        }
        if(pwd.length()<6){
            pwdEditText.setError("Password Should Be At Least 6 Characters");
            pwdEditText.requestFocus();
            return;
        }

        if(phone.isEmpty()){
            phoneEditText.setError("Phone Number Is Required");
            phoneEditText.requestFocus();
            return;
        }
        if(!(phone.length()==10)){
            pwdEditText.setError("Phone Number Should Contain Only 10 Digits");
            pwdEditText.requestFocus();
            return;
        }
        if(phone.matches("^[a-zA-Z]*$")) {
            phoneEditText.setError("Phone Number should contain only digits");
            phoneEditText.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Users user = new Users(name,phone,email);
                                String e = email.split("@")[0];
                                databaseUsers.child(e).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(getApplicationContext(),"Registration Successful! Please Verify Your Email",Toast.LENGTH_LONG).show();

                                            Intent i = new Intent(SignUpActivity.this,MainActivity.class);
                                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(i);
                                        }else{
                                            Toast.makeText(getApplicationContext(),"Registration Unsuccessful",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }
                    });

                }else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"User Already Exists! Try With A New Email",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Registration Unsuccessful! Try Again",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}