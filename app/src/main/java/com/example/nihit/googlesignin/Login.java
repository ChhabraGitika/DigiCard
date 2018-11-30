package com.example.nihit.googlesignin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email,password;
    private Button signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance(); // important Call

        Button signin = (Button)findViewById(R.id.loginSubmit);
        email = (EditText)findViewById(R.id.loginEmail);
        password = (EditText)findViewById(R.id.loginPassword);

        //Check if User is Already LoggedIn
        if(mAuth.getCurrentUser() != null)
        {
//User NOT logged In
            finish();
            startActivity(new Intent(getApplicationContext(),Swipe.class));
        }
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( email.getText().toString().isEmpty()){
                    email.setError( "Email id is required!" );
                }
                if( password.getText().toString().isEmpty()){
                    password.setError( "Password is required!" );
                }
                else {
                    String getemail = email.getText().toString().trim();
                    String getepassword = password.getText().toString().trim();
                    callsignin(getemail, getepassword);
                }

            }
        });
    }
    //Now start Sign In Process
//SignIn Process
    private void callsignin(String email,String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TESTING", "sign In Successful:" + task.isSuccessful());

// If sign in fails, display a message to the user. If sign in succeeds
// the auth state listener will be notified and logic to handle the
// signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("TESTING", "signInWithEmail:failed", task.getException());
                            Toast.makeText(Login.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                        else {
                        //    Toast.makeText(Login.this, "done", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Login.this, Swipe.class);
                            finish();
                            startActivity(i);
                        }
                    }
                });

    }


}

