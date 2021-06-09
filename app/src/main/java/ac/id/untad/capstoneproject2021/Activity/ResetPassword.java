package ac.id.untad.capstoneproject2021.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import com.google.firebase.auth.FirebaseAuth;

import ac.id.untad.capstoneproject2021.Activity.Nakes.SignupBidanActivity;
import ac.id.untad.capstoneproject2021.R;

public class ResetPassword extends AppCompatActivity {
    private EditText inputEmail;
    private Button btnReset;
    ProgressBar progressbarrest;
    private FirebaseAuth auth;

    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        inputEmail = findViewById(R.id.EditTextSurname);
        btnReset = findViewById(R.id.btn_reset_password);
        auth = FirebaseAuth.getInstance();
        progressbarrest = findViewById(R.id.progressbarrest);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbarrest.setVisibility(View.VISIBLE);
                String email = inputEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    progressbarrest.setVisibility(View.GONE);
                    Toast.makeText(getApplication(), "Mohon Masukkan Email Anda", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressbarrest.setVisibility(View.GONE);
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ResetPassword.this);
                                    alertDialogBuilder
                                            .setTitle("Reset Password")
                                            .setMessage("Kami Telah Mengirimi Anda E-Mail Berisi Link Reset Password, Silahkan Buka Email Anda")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                    finish();
                                                }
                                            });
                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                } else {
                                    progressbarrest.setVisibility(View.GONE);
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ResetPassword.this);
                                    alertDialogBuilder
                                            .setTitle("Terjadi Kesalahan")
                                            .setMessage("Mohon Maaf, Email Yang Anda Masukkan Tidak Terdaftar")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });
                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                }
                            }
                        });
            }
        });
    }


    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }

    public void NavigateSignUp(View v) {
        Intent inent = new Intent(this, SignupBidanActivity.class);
        startActivity(inent);
    }
}