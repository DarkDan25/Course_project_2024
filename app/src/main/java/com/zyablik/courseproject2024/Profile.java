package com.zyablik.courseproject2024;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.badge.BadgeUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button edit_profile_butt;
    private Button quit_butt;
    private TextView email;
    private TextView ID;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseAuth auth;
    FirebaseUser usr;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        usr = auth.getCurrentUser();

        edit_profile_butt = view.findViewById(R.id.edit_profile);
        quit_butt = view.findViewById(R.id.quit_from_profile);
        email = view.findViewById(R.id.email_text);
        ID = view.findViewById(R.id.id_text);
        getID(ID);
        email.setText("Почта: " + usr.getEmail());
        edit_profile_butt.setOnClickListener(v->{
            Toast.makeText(getContext(), R.string.in_progress, Toast.LENGTH_LONG).show();
        });
        quit_butt.setOnClickListener(v ->{
            auth.signOut();
            Toast.makeText(getContext(), "Успешно вышли из аккаунта", Toast.LENGTH_LONG).show();
            startActivity(new Intent(view.getContext(), AuthActivity.class));
            getActivity().finish();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    public void getID(TextView id){
        db.collection("users")
                .whereEqualTo("email", usr.getEmail().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String title;
                        for (DocumentSnapshot doc : task.getResult()) {
                            title = doc.get("id").toString();
                            id.setText("ID: "+title);
                        }
                    }
                });
    }
}