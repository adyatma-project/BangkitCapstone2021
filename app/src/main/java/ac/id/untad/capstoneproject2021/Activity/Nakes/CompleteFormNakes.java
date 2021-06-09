package ac.id.untad.capstoneproject2021.Activity.Nakes;

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
import ac.id.untad.capstoneproject2021.databinding.ActivityCompleteFormNakesBinding;


public class CompleteFormNakes extends AppCompatActivity implements DatePickerFragment.DialogDateListener {
    private static final String TAG = CompleteFormNakes.class.getSimpleName();
    private static final int PICK_IMAGE = 123;
    final String DATE_PICKER_TAG1 = "DatePicker1";
    final String DATE_PICKER_TAG2 = "DatePicker2";
    Uri imagePath;
    Context context;
    private ActivityCompleteFormNakesBinding activityCompleteFormNakesBinding;
    private DatabaseReference databaseReference1, databaseReference2, databaseReference3;
    private FirebaseAuth firebaseAuthcombid;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private byte[] data1;

    public CompleteFormNakes() {
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                data1 = baos.toByteArray();
                activityCompleteFormNakesBinding.imgEditBidan.setImageBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCompleteFormNakesBinding = ActivityCompleteFormNakesBinding.inflate(getLayoutInflater());
        setContentView(activityCompleteFormNakesBinding.getRoot());
        context = this;
        firebaseAuthcombid = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuthcombid.getCurrentUser();
        activityCompleteFormNakesBinding.edtEmailNakes.setText(user.getEmail());
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        activityCompleteFormNakesBinding.edtTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG1);
            }
        });


        activityCompleteFormNakesBinding.btnEditprofileBidanComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagePath == null) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CompleteFormNakes.this);
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
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CompleteFormNakes.this);
                    alertDialogBuilder
                            .setTitle("Apakah Data Yang Anda Masukkan Sudah Benar ?")
                            .setCancelable(false)
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    userInformation();
                                    sendUserData();
                                    Intent intent2j = new Intent(CompleteFormNakes.this, HomeNakes.class);
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


        activityCompleteFormNakesBinding.imgEditBidan.setOnClickListener(new View.OnClickListener() {
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
        String nik = activityCompleteFormNakesBinding.edtNomorKtpNakes.getText().toString().trim();
        String name = activityCompleteFormNakesBinding.editProfileBidanNama.getText().toString().trim();
        String alamat = activityCompleteFormNakesBinding.edtTanggalLahir.getText().toString().trim();
        String pekerjaan = activityCompleteFormNakesBinding.edtTanggalLahir.getText().toString().trim();
        String email = activityCompleteFormNakesBinding.edtTanggalLahir.getText().toString().trim();
        String jenis_kelamin = activityCompleteFormNakesBinding.edtTanggalLahir.getText().toString().trim();
        //  String label = activityCompleteFormNakesBinding.edtTanggalLahir.getText().toString().trim();
        String role = "nakes";
        String tanggallahir = activityCompleteFormNakesBinding.edtTanggalLahir.getText().toString().trim();
        String nomorhp = activityCompleteFormNakesBinding.edtTanggalLahir.getText().toString().trim();
        String uid1 = (user.getUid());

        User userinformation = new User(name, tanggallahir, umur, nostr, kota, kecamatan, kelurahan, nohandphone, email, alamat, status);
        databaseReference1.child("Akun").child(user.getUid()).setValue(userinformation);

        Toast.makeText(getApplicationContext(), "Data Bidan Telah Tersimpan, Terima Kasih", Toast.LENGTH_LONG).show();
        Intent compbin = new Intent(CompleteFormNakes.this, HomeNakes.class);
        startActivity(compbin);
        finish();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onDialogDateSet(String tag, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd - MM - yyyy", Locale.getDefault());
        activityCompleteFormNakesBinding.edtTanggalLahir.setText(dateFormat.format(calendar.getTime()));
        activityCompleteFormNakesBinding.edtTanggalLahir.setFocusable(false);
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
                Toast.makeText(CompleteFormNakes.this, "Terjadi Kesalahan Saat Mengunggah Gambar", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(CompleteFormNakes.this, "Foto Telah Disimpan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}