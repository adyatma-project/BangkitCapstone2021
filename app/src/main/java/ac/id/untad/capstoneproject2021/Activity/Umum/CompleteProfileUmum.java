package ac.id.untad.capstoneproject2021.Activity.Umum;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ac.id.untad.capstoneproject2021.Adapter.DatePickerFragment;
import ac.id.untad.capstoneproject2021.Model.User;
import ac.id.untad.capstoneproject2021.databinding.ActivityCompleteProfileUmumBinding;


public class CompleteProfileUmum extends AppCompatActivity implements DatePickerFragment.DialogDateListener {
    private static final String TAG = CompleteProfileUmum.class.getSimpleName();
    private static final int PICK_IMAGE = 123;
    final String DATE_PICKER_TAG1 = "DatePicker1";
    final String DATE_PICKER_TAG2 = "DatePicker2";
    Uri imagePath;
    Context context;
    private ActivityCompleteProfileUmumBinding activityCompleteProfileUmumBinding;
    private DatabaseReference databaseReference1, databaseReference2, databaseReference3;
    private FirebaseAuth firebaseAuthcombid;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private byte[] data1;

    public CompleteProfileUmum() {
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                data1 = baos.toByteArray();
                activityCompleteProfileUmumBinding.imgEditBidan.setImageBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCompleteProfileUmumBinding = ActivityCompleteProfileUmumBinding.inflate(getLayoutInflater());
        setContentView(activityCompleteProfileUmumBinding.getRoot());
        context = this;
        firebaseAuthcombid = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuthcombid.getCurrentUser();
        activityCompleteProfileUmumBinding.edtEmailNakes.setText(user.getEmail());
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        activityCompleteProfileUmumBinding.edtTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG1);
            }
        });


        activityCompleteProfileUmumBinding.btnEditprofileBidanComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagePath == null) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CompleteProfileUmum.this);
                    alertDialogBuilder
                            .setTitle("Mohon Masukkan Foto Anda !")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CompleteProfileUmum.this);
                    alertDialogBuilder
                            .setTitle("Apakah Data Yang Anda Masukkan Sudah Benar ?")
                            .setCancelable(false)
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    userInformation();
                                    sendUserData();
                                    Intent intent2j = new Intent(CompleteProfileUmum.this, HomeUmum.class);
                                    startActivity(intent2j);
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
            }
        });


        activityCompleteProfileUmumBinding.imgEditBidan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent();
                profileIntent.setType("image/*");
                profileIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(profileIntent, "Select Image."), PICK_IMAGE);
            }
        });
    }

    private void userInformation() {
        FirebaseUser user = firebaseAuthcombid.getCurrentUser();
        String nik = activityCompleteProfileUmumBinding.edtNomorKtpNakes.getText().toString().trim();
        String name = activityCompleteProfileUmumBinding.editProfileBidanNama.getText().toString().trim();
        String alamat = activityCompleteProfileUmumBinding.edtTanggalLahir.getText().toString().trim();
        String pekerjaan = activityCompleteProfileUmumBinding.edtTanggalLahir.getText().toString().trim();
        String email = activityCompleteProfileUmumBinding.edtTanggalLahir.getText().toString().trim();
        String jenis_kelamin = activityCompleteProfileUmumBinding.edtTanggalLahir.getText().toString().trim();
        //  String label = activityCompleteFormNakesBinding.edtTanggalLahir.getText().toString().trim();
        String role = "umum";
        String label = "0";
        String tanggallahir = activityCompleteProfileUmumBinding.edtTanggalLahir.getText().toString().trim();
        String nomorhp = activityCompleteProfileUmumBinding.edtTanggalLahir.getText().toString().trim();
        String uid1 = (user.getUid());

        User userinformation = new User(uid1, nik, name, alamat, pekerjaan, email, jenis_kelamin, label, role, tanggallahir, nomorhp);
        databaseReference1.child("Akun").child(user.getUid()).setValue(userinformation);

        Toast.makeText(getApplicationContext(), "Data Telah Tersimpan, Terima Kasih", Toast.LENGTH_LONG).show();
        Intent compbin = new Intent(CompleteProfileUmum.this, HomeUmum.class);
        startActivity(compbin);
        finish();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onDialogDateSet(String tag, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd - MM - yyyy", Locale.getDefault());
        activityCompleteProfileUmumBinding.edtTanggalLahir.setText(dateFormat.format(calendar.getTime()));
        activityCompleteProfileUmumBinding.edtTanggalLahir.setFocusable(false);
        Calendar thatDay = Calendar.getInstance();
        thatDay.set(Calendar.DATE, dayOfMonth);
        thatDay.set(Calendar.MONTH, month); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR, year);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        StorageReference imageReference = storageReference.child(firebaseAuthcombid.getUid());
        UploadTask uploadTask = imageReference.putBytes(data1);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CompleteProfileUmum.this, "Terjadi Kesalahan Saat Mengunggah Gambar", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(CompleteProfileUmum.this, "Foto Telah Disimpan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}