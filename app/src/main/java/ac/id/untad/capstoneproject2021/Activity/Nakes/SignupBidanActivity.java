package ac.id.untad.capstoneproject2021.Activity.Nakes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ac.id.untad.capstoneproject2021.Activity.StartActivity;
import ac.id.untad.capstoneproject2021.R;

public class SignupBidanActivity extends AppCompatActivity {

    EditText SignUpMail, SignUpPass;
    Button SignUpButton;
    ProgressBar prgbidan;
    private FirebaseAuth authsignupbidan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_bidan);
        SignUpMail = findViewById(R.id.edt_email_bidan);
        SignUpPass = findViewById(R.id.edt_password_bidan);
        authsignupbidan = FirebaseAuth.getInstance();
        SignUpButton = findViewById(R.id.btnsimpanbidan);
        prgbidan = findViewById(R.id.progreebaebidan);
        SignUpButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String email = SignUpMail.getText().toString();
                String pass = SignUpPass.getText().toString();
                prgbidan.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Mohon Masukkan E-mail Anda !", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Mohon Masukkan Password Yang Anda Inginkan", Toast.LENGTH_LONG).show();
                }
                if (pass.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Anda Belum Memasukkan Password Yang Anda Inginkan", Toast.LENGTH_LONG).show();
                }
                if (pass.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Mohon Maaf, Password Minimal 8 Digit", Toast.LENGTH_LONG).show();
                } else {
                    authsignupbidan.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(SignupBidanActivity.this, new OnCompleteListener<AuthResult>() {
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        prgbidan.setVisibility(View.GONE);
                                        Toast.makeText(SignupBidanActivity.this, "Mohon Maaf, Email Sudah Terdaftar", Toast.LENGTH_LONG).show();
                                    } else if (task.isSuccessful()) {
                                        prgbidan.setVisibility(View.GONE);
                                        Intent ya = new Intent(SignupBidanActivity.this, CompleteFormNakes.class);
                                        startActivity(ya);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent signupbidanintback = new Intent(SignupBidanActivity.this, StartActivity.class);
        startActivity(signupbidanintback);
        finish();
    }
}