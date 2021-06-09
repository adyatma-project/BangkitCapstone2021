package ac.id.untad.capstoneproject2021.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ac.id.untad.capstoneproject2021.Activity.Nakes.ProfileNakes;
import ac.id.untad.capstoneproject2021.R;
import ac.id.untad.capstoneproject2021.ViewModel.MainViewModel;
import ac.id.untad.capstoneproject2021.databinding.ActivityMainBinding;
import ac.id.untad.capstoneproject2021.databinding.ActivityMapsBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home_nav, R.id.dashboard_nav, R.id.profile_nav
        ).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.loadData();
        subscribe();


    }

    private void subscribe() {
        final Observer<Integer> observeLoad = new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.d("SUB", integer.toString());
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        };
        mainViewModel.getReport().observe(this, observeLoad);
    }
}