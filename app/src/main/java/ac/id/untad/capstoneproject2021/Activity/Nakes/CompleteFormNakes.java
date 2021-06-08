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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import id.untad.informatika.adyatma.momscare.Model.BidanModel1;
import id.untad.informatika.adyatma.momscare.Model.BidanModel2;
import id.untad.informatika.adyatma.momscare.R;
import id.untad.informatika.adyatma.momscare.View.DatePickerFragment;
import id.untad.informatika.adyatma.momscare.View_Ibu.CompleteProfileIbu;

    public class CompleteFormNakes extends AppCompatActivity implements DatePickerFragment.DialogDateListener {

        private static final String TAG = CompleteProfileBidan.class.getSimpleName();
        private static int PICK_IMAGE = 123;
        final String DATE_PICKER_TAG1 = "DatePicker1";
        final String DATE_PICKER_TAG2 = "DatePicker2";
        Uri imagePath;
        EditText editprofilebidan_tanggallahir;
        Spinner edit_profile_bidan_kabupaten;
        EditText editprofilebidan_nama;
        EditText editprofilebidan_umur;
        EditText editprofilebidan_no_str;
        Spinner editprofilebidan_kecamatan;
        Spinner editprofilebidan_kelurahan;
        EditText editprofilebidan_no_handphone;
        EditText editprofilebidan_email;
        EditText editprofilebidan_alamat;
        Button btnupdatebidancomplete;

        String linkkota = "https://api.binderbyte.com/wilayah/kabupaten?api_key=9930c80eeabb35c075410c1bc15448f97b81fb49edb60fdaeb93d73621f123fb&id_provinsi=";
        String linkkecamatan = "https://api.binderbyte.com/wilayah/kecamatan?api_key=9930c80eeabb35c075410c1bc15448f97b81fb49edb60fdaeb93d73621f123fb&id_kabupaten=";
        String linkkelurahan = "https://api.binderbyte.com/wilayah/kelurahan?api_key=9930c80eeabb35c075410c1bc15448f97b81fb49edb60fdaeb93d73621f123fb&id_kecamatan=";

        ArrayList<String> listNamaKota = new ArrayList<>();
        ArrayList<String> listNamaKecamatan = new ArrayList<>();
        ArrayList<String> listNamaKelurahan = new ArrayList<>();

        ArrayList<String> listIDKota = new ArrayList<>();
        ArrayList<String> listIDKecamatan = new ArrayList<>();
        ArrayList<String> listIDKelurahan = new ArrayList<>();
        Context context;
        private DatabaseReference databaseReference1, databaseReference2, databaseReference3;
        private FirebaseAuth firebaseAuthcombid;
        private FirebaseStorage firebaseStorage;
        private StorageReference storageReference;
        private ImageView imgeditprofilebidan;
        private byte[] data1;

        public CompleteProfileBidan() {
        }

        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
                imagePath = data.getData();
                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                    data1 = baos.toByteArray();
                    imgeditprofilebidan.setImageBitmap(bmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            context = this;
            setContentView(R.layout.activity_complete_profile_bidan);
            firebaseAuthcombid = FirebaseAuth.getInstance();
            FirebaseUser user = firebaseAuthcombid.getCurrentUser();
            imgeditprofilebidan = findViewById(R.id.img_edit_bidan);
            edit_profile_bidan_kabupaten = findViewById(R.id.edit_profile_bidan_kabupaten);
            databaseReference1 = FirebaseDatabase.getInstance().getReference();
            databaseReference2 = FirebaseDatabase.getInstance().getReference();
            databaseReference3 = FirebaseDatabase.getInstance().getReference();
            editprofilebidan_nama = findViewById(R.id.edit_profile_bidan_nama);
            editprofilebidan_tanggallahir = findViewById(R.id.edit_profile_bidan_tanggal_lahir);
            editprofilebidan_umur = findViewById(R.id.edit_profile_bidan_umur);
            editprofilebidan_no_str = findViewById(R.id.edit_profile_bidan_nama_belakang);
            editprofilebidan_kecamatan = findViewById(R.id.edit_profile_bidan_kecamatan);
            editprofilebidan_kelurahan = findViewById(R.id.edit_profile_bidan_kelurahan);
            editprofilebidan_no_handphone = findViewById(R.id.edit_profile_bidan_no_telpon);
            editprofilebidan_email = findViewById(R.id.edit_profile_bidan_email);
            editprofilebidan_alamat = findViewById(R.id.edit_profile_bidan_alamat);
            btnupdatebidancomplete = findViewById(R.id.btn_editprofile_bidan_complete);
            editprofilebidan_email.setText(user.getEmail());
            firebaseStorage = FirebaseStorage.getInstance();
            storageReference = firebaseStorage.getReference();
            showDataKota();
            editprofilebidan_tanggallahir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG1);
                }
            });


            btnupdatebidancomplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imagePath == null) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CompleteProfileBidan.this);
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
                    }
                    else if (editprofilebidan_kecamatan.getSelectedItem().toString().trim() == "") {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CompleteProfileBidan.this);
                        alertDialogBuilder
                                .setTitle("Perhatian !")
                                .setCancelable(false)
                                .setMessage("Mohon Masukkan Pilihan Kecamatan !")
                                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                    else if (editprofilebidan_kelurahan.getSelectedItem().toString().trim() == "") {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CompleteProfileBidan.this);
                        alertDialogBuilder
                                .setTitle("Perhatian !")
                                .setCancelable(false)
                                .setMessage("Mohon Masukkan Pilihan Kelurahan !")
                                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                    else if (editprofilebidan_nama.getText().toString().length()==0) {
                        editprofilebidan_nama.setError("Mohon Di isi !");
                    }
                    else if (editprofilebidan_tanggallahir.getText().toString().length()==0) {
                        editprofilebidan_tanggallahir.setError("Mohon Di isi !");
                    }
                    else if (editprofilebidan_umur.getText().toString().length()==0) {
                        editprofilebidan_umur.setError("Mohon Di isi !");
                    }
                    else if (editprofilebidan_no_str.getText().toString().length() < 7) {
                        editprofilebidan_no_str.setError("Apabila Anda Tidak Memiliki STR, Silahkan Isi Dengan Tanggal,Bulan,Tahun Lahir Anda (Mis. 01 01 0001) !");
                    }
                    else if (editprofilebidan_no_handphone.getText().toString().length()==0) {
                        editprofilebidan_no_handphone.setError("Mohon Di isi !");
                    }
                    else if (editprofilebidan_no_handphone.getText().toString().length()<12) {
                        editprofilebidan_no_handphone.setError("Mohon Isi Dengan No Yang Valid !");
                    }
                    else if (editprofilebidan_alamat.getText().toString().length()==0) {
                        editprofilebidan_alamat.setError("Mohon Di isi !");
                    }
                    else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CompleteProfileBidan.this);
                        alertDialogBuilder
                                .setTitle("Apakah Data Yang Anda Masukkan Sudah Benar ?")
                                .setCancelable(false)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        userInformation();
                                        sendUserData();
                                        Intent intent2j = new Intent(CompleteProfileBidan.this, HomeActivityBidan.class);
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


            imgeditprofilebidan.setOnClickListener(new View.OnClickListener() {
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
            String name = editprofilebidan_nama.getText().toString().trim();
            String tanggallahir = editprofilebidan_tanggallahir.getText().toString().trim();
            String umur = editprofilebidan_umur.getText().toString().trim();
            String nostr = editprofilebidan_no_str.getText().toString().trim();
            String kota = edit_profile_bidan_kabupaten.getSelectedItem().toString().trim();
            String kecamatan = editprofilebidan_kecamatan.getSelectedItem().toString().trim();
            String kelurahan = editprofilebidan_kelurahan.getSelectedItem().toString().trim();
            String nohandphone = editprofilebidan_no_handphone.getText().toString().trim();
            String email = editprofilebidan_email.getText().toString().trim();
            String alamat = editprofilebidan_alamat.getText().toString().trim();
            String status = "bidan";
            String uid1 = (user.getUid());
            String uid2 = (name + " - " + nostr);
            BidanModel1 userinformation = new BidanModel1(name, tanggallahir, umur, nostr, kota, kecamatan, kelurahan, nohandphone, email, alamat, status);
            BidanModel2 userinformation2 = new BidanModel2(uid1, uid2);
            databaseReference1.child("Akun").child(user.getUid()).setValue(userinformation);
            databaseReference2.child("Data Bidan1").child(kecamatan).child(kelurahan).child(name + " - " + nostr).setValue(userinformation2);
            Toast.makeText(getApplicationContext(), "Data Bidan Telah Tersimpan, Terima Kasih", Toast.LENGTH_LONG).show();
            Intent compbin = new Intent(CompleteProfileBidan.this, HomeActivityBidan.class);
            startActivity(compbin);
            finish();
        }


        @SuppressLint("SetTextI18n")
        @Override
        public void onDialogDateSet(String tag, int year, int month, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd - MM - yyyy", Locale.getDefault());
            editprofilebidan_tanggallahir.setText(dateFormat.format(calendar.getTime()));
            editprofilebidan_tanggallahir.setFocusable(false);
            Calendar thatDay = Calendar.getInstance();
            thatDay.set(Calendar.DATE, dayOfMonth);
            thatDay.set(Calendar.MONTH, month); // 0-11 so 1 less
            thatDay.set(Calendar.YEAR, year);
            Calendar today = Calendar.getInstance();
            long diff = (today.get(Calendar.YEAR)) - (thatDay.get(Calendar.YEAR));
            String yearString = Long.toString(diff);
            editprofilebidan_umur.setText(yearString + " Tahun");
        }

        @Override
        public void onBackPressed() {
            finish();
        }


        private void showDataKota() {
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    linkkota + 72, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    listIDKota.clear();
                    listNamaKota.clear();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("value");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String nama = object.getString("name");

                            listIDKota.add(id);
                            listNamaKota.add(nama);
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, listNamaKota);
                        edit_profile_bidan_kabupaten.setAdapter(arrayAdapter);
                        edit_profile_bidan_kabupaten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                showDataKecamatan(listIDKota.get(position));
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }

        private void showDataKecamatan(String s) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    linkkecamatan + s, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    listIDKecamatan.clear();
                    listNamaKecamatan.clear();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("value");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String nama = object.getString("name");

                            listIDKecamatan.add(id);
                            listNamaKecamatan.add(nama);
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, listNamaKecamatan);
                        editprofilebidan_kecamatan.setAdapter(arrayAdapter);
                        editprofilebidan_kecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                showDataKelurahan(listIDKecamatan.get(position));
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }

        private void showDataKelurahan(String s) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    linkkelurahan + s, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    listIDKelurahan.clear();
                    listNamaKelurahan.clear();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("value");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String nama = object.getString("name");

                            listIDKelurahan.add(id);
                            listNamaKelurahan.add(nama);
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, listNamaKelurahan);
                        editprofilebidan_kelurahan.setAdapter(arrayAdapter);
                        editprofilebidan_kelurahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }


        private void sendUserData() {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            StorageReference imageReference = storageReference.child(firebaseAuthcombid.getUid());
            UploadTask uploadTask = imageReference.putBytes(data1);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CompleteProfileBidan.this, "Terjadi Kesalahan Saat Mengunggah Gambar", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(CompleteProfileBidan.this, "Foto Telah Disimpan", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }