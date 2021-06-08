package ac.id.untad.capstoneproject2021.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import ac.id.untad.capstoneproject2021.Activity.Nakes.SignupBidanActivity;
import ac.id.untad.capstoneproject2021.Activity.Umum.SignupUmum;
import ac.id.untad.capstoneproject2021.databinding.ActivityStartBinding;

public class StartActivity extends AppCompatActivity {
    ImageView imgbidan;
    ImageView imgibu, imgbkkbn;
    Button btnmasuk;
    private ActivityStartBinding activity_startBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity_startBinding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(activity_startBinding.getRoot());

        imgbidan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bidan = new Intent(StartActivity.this, SignupBidanActivity.class);
                startActivity(bidan);
            }
        });
        imgibu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ibu = new Intent(StartActivity.this, SignupUmum.class);
                startActivity(ibu);
            }
        });

        btnmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent masuk = new Intent(StartActivity.this, SigninActivity.class);
                startActivity(masuk);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}