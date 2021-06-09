package ac.id.untad.capstoneproject2021.Activity.Umum;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ac.id.untad.capstoneproject2021.Activity.SigninActivity;
import ac.id.untad.capstoneproject2021.Activity.StartActivity;
import ac.id.untad.capstoneproject2021.databinding.ActivitySignupUmumBinding;


public class SignupUmum extends AppCompatActivity {
    private FirebaseAuth authsignupbidan;
    private ActivitySignupUmumBinding activitySignupUmumBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignupUmumBinding = ActivitySignupUmumBinding.inflate(getLayoutInflater());
        setContentView(activitySignupUmumBinding.getRoot());
        activitySignupUmumBinding.progresssignupibu.setVisibility(View.GONE);
        authsignupbidan = FirebaseAuth.getInstance();
        activitySignupUmumBinding.btndaftaribu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String email = activitySignupUmumBinding.edtEmailIbu.getText().toString();
                String pass = activitySignupUmumBinding.edtPasswordIbu.getText().toString();
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
                    activitySignupUmumBinding.progresssignupibu.setVisibility(View.VISIBLE);
                    authsignupbidan.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(SignupUmum.this, new OnCompleteListener<AuthResult>() {
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        activitySignupUmumBinding.progresssignupibu.setVisibility(View.GONE);
                                        Toast.makeText(SignupUmum.this, "Mohon Maaf, Email Sudah Terdaftar", Toast.LENGTH_LONG).show();
                                    } else if (task.isSuccessful()) {
                                        activitySignupUmumBinding.progresssignupibu.setVisibility(View.GONE);
                                        Intent ya = new Intent(SignupUmum.this, CompleteProfileUmum.class);
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
        Intent backsignup = new Intent(SignupUmum.this, StartActivity.class);
        startActivity(backsignup);
        finish();
    }

    public void navigate_sign_in(View v) {
        Intent inent = new Intent(this, SigninActivity.class);
        startActivity(inent);
    }
}

