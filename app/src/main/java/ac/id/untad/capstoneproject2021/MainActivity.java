package ac.id.untad.capstoneproject2021;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ac.id.untad.capstoneproject2021.databinding.ActivityMainBinding;
import ac.id.untad.capstoneproject2021.databinding.ActivityMapsBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}