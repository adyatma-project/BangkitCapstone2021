package ac.id.untad.capstoneproject2021.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ac.id.untad.capstoneproject2021.Activity.Nakes.SignupBidanActivity;
import ac.id.untad.capstoneproject2021.R;
import ac.id.untad.capstoneproject2021.databinding.ActivitySigninBinding;

public class SigninActivity extends AppCompatActivity {
    ProgressBar progressbarsignin;
    private ActivitySigninBinding activitySigninBinding;
    private EditText SignInMail, SignInPass;
    private FirebaseAuth auth;
    private Button SignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySigninBinding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(activitySigninBinding.getRoot());
        auth = FirebaseAuth.getInstance();
        progressbarsignin = findViewById(R.id.progressbarsignin);
        SignInMail = findViewById(R.id.edt_username_login);
        SignInPass = findViewById(R.id.edt_password_login);
        SignInButton = findViewById(R.id.btnmasuk);
        auth = FirebaseAuth.getInstance();
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = SignInMail.getText().toString();
                final String password = SignInPass.getText().toString();
                progressbarsignin.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Harap Masukkan E-Mail Anda", Toast.LENGTH_SHORT).show();
                    progressbarsignin.setVisibility(View.GONE);
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Harap Masukkan Password Anda", Toast.LENGTH_SHORT).show();
                    progressbarsignin.setVisibility(View.GONE);
                    return;
                }
                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SigninActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    if (password.length() < 8) {
                                        Toast.makeText(SigninActivity.this, "Password Minimal 8 Karakter", Toast.LENGTH_SHORT).show();
                                        progressbarsignin.setVisibility(View.GONE);
                                    } else {
                                        progressbarsignin.setVisibility(View.GONE);
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SigninActivity.this);
                                        alertDialogBuilder
                                                .setTitle("E-Mail Atau Password Yang Anda Masukkan Salah")
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });
                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        alertDialog.show();
                                    }
                                } else {


                                    // Kondisi Intent Nakes Dan Umum



                                }


                            }
                        });
            }
        });
    }

    public void NavigateSignUp(View v) {
        Intent inent = new Intent(this, SignupBidanActivity.class);
        startActivity(inent);
    }

    public void NavigateForgetMyPassword(View v) {
        Intent inents = new Intent(this, ResetPassword.class);
        startActivity(inents);
        finish();
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}