package ac.id.untad.capstoneproject2021.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ac.id.untad.capstoneproject2021.Activity.Nakes.HomeNakes;
import ac.id.untad.capstoneproject2021.Activity.Umum.HomeUmum;
import ac.id.untad.capstoneproject2021.Model.User;
import ac.id.untad.capstoneproject2021.databinding.ActivitySigninBinding;
import ac.id.untad.capstoneproject2021.databinding.ActivityStartBinding;


public class SigninActivity extends AppCompatActivity {
    private ActivitySigninBinding activitySigninBinding;
    ProgressBar progressbarsignin;
    private FirebaseAuth auth;
    private Button SignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        auth = FirebaseAuth.getInstance();
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = activitySigninBinding.edtUsernameLogin.getText().toString();
                final String password = activitySigninBinding.edtPasswordLogin.getText().toString();
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
                                    FirebaseDatabase fd1 = FirebaseDatabase.getInstance();
                                    DatabaseReference databaseReference = fd1.getReference().child("Akun").child(auth.getUid());
                                    //  final FirebaseUser user=firebaseAuth.getCurrentUser();
                                    databaseReference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            User userProfile = dataSnapshot.getValue(User.class);
                                            String status = userProfile.getRole();
                                            if (status.equals("nakes")) {
                                                Intent intent = new Intent(SigninActivity.this, HomeNakes.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else if (status.equals("umum")) {
                                                Intent intent = new Intent(SigninActivity.this, HomeUmum.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else{
                                                Intent inendt = new Intent(SigninActivity.this, SplashScreen.class);
                                                startActivity(inendt);
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Toast.makeText(SigninActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });
            }
        });
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