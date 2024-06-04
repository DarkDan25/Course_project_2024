package com.zyablik.courseproject2024;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FirebaseAuth auth;
    FirebaseUser usr;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
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
        auth = FirebaseAuth.getInstance();
        usr = auth.getCurrentUser();
        TextView header = findViewById(R.id.header_text);
        fragmentManager.beginTransaction().add(R.id.main_fragment_manager, Reception.class, null).commit();

        BottomNavigationView menu = findViewById(R.id.bottom_menu);
        menu.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.profile_page) {
                Fragment profile = new Profile();
                getNameSurname(header);
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

    private void getNameSurname(TextView header) {
        db.collection("users")
                .whereEqualTo("email", usr.getEmail().toString())
                .get()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        String title;
                        for (DocumentSnapshot doc : task.getResult()) {
                            title = doc.get("name").toString() + " " + doc.get("surname").toString();
                            header.setText(title);
                        }
                    }
                });
    }

}