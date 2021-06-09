package ac.id.untad.capstoneproject2021.Activity.Umum;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ac.id.untad.capstoneproject2021.Activity.SigninActivity;
import ac.id.untad.capstoneproject2021.R;


public class UbahPasswordActivity extends AppCompatActivity {
    EditText edtubahpassword;
    Button btnsimpanpassword, btnbackpassword;
    FirebaseUser user;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);
        edtubahpassword = findViewById(R.id.edtubahpassword);
        btnbackpassword = findViewById(R.id.btnbackpassword);
        btnsimpanpassword = findViewById(R.id.btnsimpanpassword);
        progressBar = findViewById(R.id.progressbarubah);
        progressBar.setVisibility(View.GONE);
        user = FirebaseAuth.getInstance().getCurrentUser();
        edtubahpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        btnbackpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnsimpanpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                user.updatePassword(edtubahpassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UbahPasswordActivity.this, "Password Berhasil Diubah, Silahkan Masuk Kembali", Toast.LENGTH_SHORT).show();
                                    FirebaseAuth.getInstance().signOut();
                                    Intent ubah = new Intent(UbahPasswordActivity.this, SigninActivity.class);
                                    startActivity(ubah);
                                    finish();
                                } else {
                                    Toast.makeText(UbahPasswordActivity.this, "Terjadi Kesalahan, Silakan Keluar Aplikasi Dan Coba Kembali", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}