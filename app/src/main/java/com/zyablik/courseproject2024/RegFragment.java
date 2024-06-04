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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button regbutt;
    TextView email;
    TextView password;
    TextView snils;
    TextView gender;
    TextView name;
    TextView surname;
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public RegFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegFragment newInstance(String param1, String param2) {
        RegFragment fragment = new RegFragment();
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
        regbutt = view.findViewById(R.id.reg_button);
        email = view.findViewById(R.id.login_field);
        password = view.findViewById(R.id.reg_password);
        name = view.findViewById(R.id.name_field);
        surname = view.findViewById(R.id.surname_field);
        snils = view.findViewById(R.id.snils_regfield);
        gender = view.findViewById(R.id.gender_field);

        mAuth = FirebaseAuth.getInstance();

        regbutt.setOnClickListener(v -> {
            if (!name.getText().toString().isEmpty() &&
                    !surname.getText().toString().isEmpty()&&
                    !password.getText().toString().isEmpty()&&
                    !email.getText().toString().isEmpty()&&
                    !gender.getText().toString().isEmpty()&&
                    !snils.getText().toString().isEmpty()){
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(getActivity(), task -> {
                            if (task.isSuccessful()) {
                                addUser(email.getText().toString(), name.getText().toString(),
                                        surname.getText().toString(), snils.getText().toString(), gender.getText().toString(),view);
                                startActivity(new Intent(view.getContext(), MainActivity.class));
                                getActivity().finish();
                            } else {
                                email.setText("");
                                password.setText("");
                            }
                        });
            }
            else {
                Toast.makeText(view.getContext(), "Все должно быть заполнено!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reg, container, false);
    }

    public void addUser(String emailstr, String name, String surname, String idstr, String gend, View view) {
        int id = Integer.parseInt(idstr);
        Map<String, Object> user = new HashMap<>();

        user.put("email", emailstr);
        user.put("name", name);
        user.put("surname", surname);
        user.put("id", id); // Он же будет СНИЛС'ом
        user.put("role", "user");
        user.put("gender", gend);

        db.collection("users").document(idstr)
                .set(user)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(view.getContext(), "Greetings, " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(unused -> {
                    Toast.makeText(view.getContext(), "Couldn't create account", Toast.LENGTH_SHORT).show();
                });
    }
}