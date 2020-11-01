package com.laioffer.tinnews;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.laioffer.tinnews.repository.NewsRepository;
import com.laioffer.tinnews.repository.NewsViewModelFactory;
import com.laioffer.tinnews.ui.home.HomeViewModel;

public class MainActivity extends AppCompatActivity {

  private NavController navController;

  private HomeViewModel viewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    BottomNavigationView navView = findViewById(R.id.nav_view);
    NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
        .findFragmentById(R.id.nav_host_fragment);
    navController = navHostFragment.getNavController();
    NavigationUI.setupWithNavController(navView, navController);
//    NavigationUI.setupActionBarWithNavController(this, navController);


    NewsRepository repository = new NewsRepository(this);
    viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository))
        .get(HomeViewModel.class);

    viewModel
        .getTopHeadlines()
        .observe(this, newsResponse -> {
          if (newsResponse != null) {
                    Log.d("getTopHeadlines", newsResponse.toString());
          }
        });

    viewModel.setCountryInput("us");
  }

  @Override
  public boolean onSupportNavigateUp() {
    return navController.navigateUp();
  }
}