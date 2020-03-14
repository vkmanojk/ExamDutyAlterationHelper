package com.example.android.examdutyalterationhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    protected EditText emailEditText,pwdEditText;
    protected TextView signIn,forgotPwd;
    protected FloatingActionButton floatButtonSignIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.email_signin);
        pwdEditText = findViewById(R.id.pwd_signin);
        signIn = findViewById(R.id.signin_textview_loginpage);
        forgotPwd = findViewById(R.id.forgotpwd_textview_loginpage);
        floatButtonSignIn = findViewById(R.id.float_button_signin);

        mAuth = FirebaseAuth.getInstance();

        floatButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSignIn();
            }
        });

        forgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ForgotPassword.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }

    public void userSignIn(){
        final String email = emailEditText.getText().toString().trim();
        final String pwd = pwdEditText.getText().toString().trim();

        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Users");

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

        mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(mAuth.getCurrentUser().isEmailVerified()){
                        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String e = email.split("@")[0];
                                if(dataSnapshot.child(e).exists()){
                                    Users user = dataSnapshot.child(e).getValue(Users.class);
                                    Toast.makeText(MainActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                    Prevalent.currentOnlineUser = user;
                                    Prevalent.UserEmail = user.email;
                                    Intent i = new Intent(MainActivity.this,HomeActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(),"Please Verify Your Email",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Invalid Credentials!!!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void OpenSignUpPage(View view) {
        startActivity(new Intent(MainActivity.this,SignUpActivity.class));
    }
}