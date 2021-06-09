package ac.id.untad.capstoneproject2021.Activity.Nakes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import ac.id.untad.capstoneproject2021.Activity.SigninActivity;
import ac.id.untad.capstoneproject2021.Activity.SplashScreen;
import ac.id.untad.capstoneproject2021.Activity.StartActivity;
import ac.id.untad.capstoneproject2021.Activity.Umum.UbahPasswordActivity;
import ac.id.untad.capstoneproject2021.databinding.ActivityProfileNakesBinding;

public class ProfileNakes extends AppCompatActivity {
    private ActivityProfileNakesBinding activityProfileNakesBinding;
    String uriget, uid2bidan;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileNakesBinding = ActivityProfileNakesBinding.inflate(getLayoutInflater());
        setContentView(activityProfileNakesBinding.getRoot());
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Akun").child(firebaseAuth.getUid());
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(user.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerInside().into(activityProfileNakesBinding.imgProfilNakes);
                uriget = uri.toString();
            }
        });
        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), SigninActivity.class));
            finish();
        }

        activityProfileNakesBinding.imgBackProfileNakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Intent back = new Intent(ProfilNakes.this, HomeNakes.class);
              //  startActivity(back);
                finish();
            }
        });



        activityProfileNakesBinding.ubahpasswordbidan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent backprofilebind = new Intent(ProfilNakes.this, UbahPasswordActivity.class);
                //startActivity(backprofilebind);
            }
        });




      /*  final FirebaseUser user1 = firebaseAuth.getCurrentUser();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressbarprofilebidan.setVisibility(View.GONE);
                BidanModel1 userProfile = dataSnapshot.getValue(BidanModel1.class);
                nama_bidan.setText(userProfile.getNama_bidan());
                tanggal_lahir.setText(userProfile.getTanggal_lahir());
                umur.setText(userProfile.getUmur());
                no_str.setText(userProfile.getNo_str());
                tv_profile_kota_bidan.setText(userProfile.getKota());
                kecamatan.setText(userProfile.getKecamatan());
                kelurahan.setText(userProfile.getKelurahan());
                nophone.setText(userProfile.getPhoneno());
                email.setText(user1.getEmail());
                alamat.setText(userProfile.getAlamat());
                String namabidan = (userProfile.getNama_bidan());
                String str = (userProfile.getNo_str());
                uid2bidan = (namabidan + " - " + str);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProfilNakes.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        }); */
    }




    @Override
    protected void onDestroy() {
        Intent inentkjk = new Intent(ProfileNakes.this, SplashScreen.class);
        startActivity(inentkjk);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent profilbidanintenback = new Intent(ProfileNakes.this, HomeNakes.class);
        startActivity(profilbidanintenback);
        finish();
    }

    public void navigateLogOut(View v) {
        FirebaseAuth.getInstance().signOut();
        Intent inent = new Intent(this, StartActivity.class);
        startActivity(inent);
    }
}