package com.zyablik.courseproject2024;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class login extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button regbutt;
    private Button logbutt;
    private TextView email;
    private TextView pswrd;
    FirebaseAuth auth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment login.
     */
    // TODO: Rename and change types and number of parameters
    public static login newInstance(String param1, String param2) {
        login fragment = new login();
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
        regbutt = view.findViewById(R.id.register_button);
        auth = FirebaseAuth.getInstance();
        email = view.findViewById(R.id.login_field);
        pswrd = view.findViewById(R.id.reg_password);
        regbutt.setOnClickListener(v -> {
            Fragment Registration = new RegFragment();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.auth_fragment_container, Registration, "Reg").commit();
        });

        logbutt = view.findViewById(R.id.log_button);
        logbutt.setOnClickListener(v -> {
            auth.signInWithEmailAndPassword(email.getText().toString().toLowerCase(), pswrd.getText().toString())
                    .addOnCompleteListener(getActivity(), task -> {
                        if (task.isSuccessful()) {
                            getNameSurname(view);
                            startActivity(new Intent(view.getContext(), MainActivity.class));
                            getActivity().finish();
                        } else {
                            Toast.makeText(view.getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            email.setText("");
                            pswrd.setText("");
                        }
                    });
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    private void getNameSurname(View view) {
        db.collection("users")
                .whereEqualTo("email", auth.getCurrentUser().getEmail().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String title;
                        for (DocumentSnapshot doc : task.getResult()) {
                            title = doc.get("name").toString() + " " + doc.get("surname").toString();
                            Toast.makeText(view.getContext(), "Welcome back, " + title, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}