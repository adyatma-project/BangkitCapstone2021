package ac.id.untad.capstoneproject2021.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import ac.id.untad.capstoneproject2021.R;

public class SplashScreen extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser1, firebaseUser2;
    String status;
    String nama;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser1 = firebaseAuth.getCurrentUser();
        firebaseUser2 = firebaseAuth.getCurrentUser();
        if (firebaseUser1 == null) {
            Intent intent = new Intent(SplashScreen.this, StartActivity.class);
            startActivity(intent);
            finish();
        }
        if (firebaseUser1 != null) {
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Akun").child(firebaseUser2.getUid());
            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        firebaseDatabase = FirebaseDatabase.getInstance();
                        databaseReference = firebaseDatabase.getReference().child("Akun").child(firebaseUser2.getUid());
                        //  final FirebaseUser user=firebaseAuth.getCurrentUser();
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                User userProfile = dataSnapshot.getValue(User.class);
                                status = userProfile.getRole();
                                nama = userProfile.getNama();
                                if (status.equals("nakes")) {
                                    Intent intent = new Intent(SplashScreen.this, HomeNakes.class);
                                    startActivity(intent);
                                    finish();
                                } else if (status.equals("umum")) {
                                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(SplashScreen.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SplashScreen.this);
                        alertDialogBuilder
                                .setTitle("Data Tidak Ditemukan !")
                                .setMessage("Mohon Maaf, Data Tidak Ditemukan Di Server Kami. Silahkan Lakukan Pendaftaran Kembali, Terima Kasih")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        user.delete();
                                        //FirebaseAuth.getInstance().signOut();
                                        Intent inent = new Intent(SplashScreen.this, StartActivity.class);
                                        startActivity(inent);
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                }

            });
        }
    }
}