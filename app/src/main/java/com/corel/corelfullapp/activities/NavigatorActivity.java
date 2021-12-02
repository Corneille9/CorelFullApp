package com.corel.corelfullapp.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.corel.corelfullapp.R;

public class NavigatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigator_activity);
        if (savedInstanceState == null) {
            NavController navController = Navigation.findNavController(this, R.id.navigator_graph);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}