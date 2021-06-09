package ac.id.untad.capstoneproject2021.Activity.Umum;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
import ac.id.untad.capstoneproject2021.Model.User;
import ac.id.untad.capstoneproject2021.databinding.ActivityProfileNakesBinding;
import ac.id.untad.capstoneproject2021.databinding.ActivityUmumProfileBinding;

public class UmumProfile extends AppCompatActivity {
    String uriget, uid2bidan;
    private ActivityUmumProfileBinding activityUmumProfileBinding;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUmumProfileBinding = ActivityUmumProfileBinding.inflate(getLayoutInflater());
        setContentView(activityUmumProfileBinding.getRoot());
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
                Picasso.get().load(uri).fit().centerInside().into(activityUmumProfileBinding.imgProfilNakes);
                uriget = uri.toString();
            }
        });
        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), SigninActivity.class));
            finish();
        }

        activityUmumProfileBinding.imgBackProfileNakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(UmumProfile.this, HomeUmum.class);
                startActivity(back);
                finish();
            }
        });


        activityUmumProfileBinding.ubahpasswordbidan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backprofilebind = new Intent(UmumProfile.this, UbahPasswordActivity.class);
                startActivity(backprofilebind);
            }
        });


        final FirebaseUser user1 = firebaseAuth.getCurrentUser();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                activityUmumProfileBinding.progressbarProfileNakes.setVisibility(View.GONE);
                User userProfile = dataSnapshot.getValue(User.class);
                activityUmumProfileBinding.profileNamaBidan.setText(userProfile.getNama());
                activityUmumProfileBinding.textAlmatNakes.setText(userProfile.getAlamat());
                activityUmumProfileBinding.textPekerjaanNakes.setText(userProfile.getPekerjaan());
                activityUmumProfileBinding.textEmailProfilNakes.setText(user1.getEmail());
                activityUmumProfileBinding.textJenisKelamin.setText(userProfile.getJenis_kelamin());
                activityUmumProfileBinding.textTanggallahirProfileNakes.setText(userProfile.getTanggal_lahir());
                activityUmumProfileBinding.textNomorTeleponNakes.setText(userProfile.getNo_hp());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UmumProfile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        Intent inentkjk = new Intent(UmumProfile.this, SplashScreen.class);
        startActivity(inentkjk);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent profilbidanintenback = new Intent(UmumProfile.this, HomeUmum.class);
        startActivity(profilbidanintenback);
        finish();
    }

    public void navigateLogOut(View v) {
        FirebaseAuth.getInstance().signOut();
        Intent inent = new Intent(this, StartActivity.class);
        startActivity(inent);
    }
}