package ac.id.untad.capstoneproject2021.Activity.Nakes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import ac.id.untad.capstoneproject2021.Activity.StartActivity;
import ac.id.untad.capstoneproject2021.databinding.ActivityHomeNakesBinding;

public class HomeNakes extends AppCompatActivity {
    private ActivityHomeNakesBinding activityHomeNakesBinding;
    private Context context;
    private long backPressedTime;

    private DatabaseReference databaseReference, databaseReference1;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityHomeNakesBinding = ActivityHomeNakesBinding.inflate(getLayoutInflater());
        setContentView(activityHomeNakesBinding.getRoot());
        super.onCreate(savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference1 = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        this.context = context;
        onRestart();

        //atur pemanggilan nama nakes yang didapat dari database
       // activityHomeNakesBinding.userId.setText(userProfile.getNama_bidan());


        final FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Akun").child(user.getUid());
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(firebaseAuth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerInside().into(activityHomeNakesBinding.userPhotoHomeNakes);
            }
        });

        activityHomeNakesBinding.userPhotoHomeNakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inentProfile = new Intent(HomeNakes.this, ProfileNakes.class);
                startActivity(inentProfile);
            }
        });



        activityHomeNakesBinding.labelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent daftarhpl = new Intent(HomeNakes.this, LabellingActivity.class);
                startActivity(daftarhpl);
            }
        });


        activityHomeNakesBinding.keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeNakes.this);
                alertDialogBuilder
                        .setTitle("Apakah Anda Yakin Ingin Keluar ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseAuth.getInstance().signOut();
                                Intent logoutbidan = new Intent(HomeNakes.this, StartActivity.class);
                                startActivity(logoutbidan);
                                finish();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeNakes.this);
            alertDialogBuilder
                    .setTitle("Apakah Anda Yakin Ingin Keluar ?")
                    .setCancelable(false)
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            FirebaseAuth.getInstance().signOut();
                            Intent logoutbidan = new Intent(HomeNakes.this, StartActivity.class);
                            startActivity(logoutbidan);
                            finish();
                        }
                    })
                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {
            Toast.makeText(this, "Tekan Kembali Untuk Keluar", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }


    @Override
    protected void onRestart() {
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(HomeNakes.this).load(uri).circleCrop().into(activityHomeNakesBinding.userPhotoHomeNakes);
            }
        });
        super.onRestart();
    }

    @Override
    protected void onResume() {
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(HomeNakes.this).load(uri).circleCrop().into(activityHomeNakesBinding.userPhotoHomeNakes);
            }
        });
        super.onResume();
    }
}