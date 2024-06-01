package com.zyablik.courseproject2024;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView header = findViewById(R.id.header_text);
        fragmentManager.beginTransaction().add(R.id.main_fragment_manager, Reception.class, null).commit();
        BottomNavigationView menu = findViewById(R.id.bottom_menu);
        menu.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.profile_page) {
                Fragment profile = new Profile();
                header.setText(R.string.profile);
                fragmentManager.beginTransaction().replace(R.id.main_fragment_manager, profile, "profile_page").commit();
            } else if (item.getItemId() == R.id.go_to_doctor_page) {
                Toast.makeText(this, R.string.in_progress, Toast.LENGTH_LONG).show();
            } else if (item.getItemId() == R.id.reception_page) {
                Fragment reception = new Reception();
                header.setText(R.string.app_name);
                fragmentManager.beginTransaction().replace(R.id.main_fragment_manager, reception, "reception_page").commit();
            }
            return false;
        });
    }
}