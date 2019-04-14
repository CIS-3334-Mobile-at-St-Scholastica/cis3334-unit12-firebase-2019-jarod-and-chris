package edu.css.cis3334_unit12_firebase_2019;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * @author Chris Killian, Jarod Wilken
 */
public class MainActivity extends AppCompatActivity {

    private TextView textViewStatus;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonGoogleLogin;
    private Button buttonCreateLogin;
    private Button buttonSignOut;
    private Button buttonStartChat;
    private FirebaseAuth mAuth;

    /**
     * Sets up the textViews and buttons. Creates an
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonGoogleLogin = (Button) findViewById(R.id.buttonGoogleLogin);
        buttonCreateLogin = (Button) findViewById(R.id.buttonCreateLogin);
        buttonSignOut = (Button) findViewById(R.id.buttonSignOut);
        buttonStartChat = findViewById(R.id.buttonStartChat);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        buttonCreateLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        buttonGoogleLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                googleSignIn();
            }
        });

        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signOut();
            }
        });
        //Sets up chat activity.
        buttonStartChat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
        // Configure Google Sign In
    }

    /**
     *Checks if the user is already signed in or signed out.
     */
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        if (currentUser != null) {
            // User is signed in
            textViewStatus.setText("Signed In");
        } else {
            // User is signed out
            textViewStatus.setText("Signed Out");
        }
    }

    /**
     * Takes in an email and password and add its to the FireBase authentication user list.
     * @param email the entered email.
     * @param password the entered password.
     */
    private void createAccount(String email, String password) {
        Log.d("CIS3334", "createAccount:" + email);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            textViewStatus.setText("Status: Account Created!");
                        } else {
                            // If sign in fails, display a message to the user.
                            textViewStatus.setText("Status: Account Creation Failed!");
                            //updateUI(null);
                        }
                    }
                });
    }

    /**
     * Authenticates a user if the correct password and email matched the FireBase authentication list.
     * @param email the entered email.
     * @param password the entered password.
     */
    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            textViewStatus.setText("Status: Logged in!!");

                        } else {
                            // If sign in fails, display a message to the user.
                            //updateUI(null);
                            textViewStatus.setText("Status: Logged in Failed!!");
                        }
                    }
                });
    }

    /**
     * Signs the user out.
     */
    private void signOut() {
        mAuth.signOut();
        textViewStatus.setText("Signed Out");
    }

    /**
     * Lets the user use Google sign in option for authentication.
     */
    private void googleSignIn() {
        textViewStatus.setText("Status: Logged in Failed!!");

    }


}
