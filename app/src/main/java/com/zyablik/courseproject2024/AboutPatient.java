package com.zyablik.courseproject2024;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutPatient#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutPatient extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button find_butt;
    private TextView type_snils;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public AboutPatient() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutPatient.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutPatient newInstance(String param1, String param2) {
        AboutPatient fragment = new AboutPatient();
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
        find_butt = view.findViewById(R.id.find_button);
        type_snils = view.findViewById(R.id.snils_field);
        find_butt.setOnClickListener(v ->{
            FrameLayout f = view.findViewById(R.id.info_pati);
            f.setVisibility(View.VISIBLE);
            TextView snls =  f.findViewById(R.id.snils_text);
            TextView nm = f.findViewById(R.id.fio_text);
            TextView gndr = f.findViewById(R.id.gender_text);
            db.collection("users")
                    .whereEqualTo("id", Integer.parseInt(type_snils.getText().toString()))
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String title;
                            String namesurname;
                            String gender;
                            for (DocumentSnapshot doc : task.getResult()) {
                                title = doc.getId().toString();
                                namesurname = doc.get("name").toString() +" "+doc.get("surname").toString();
                                gender = doc.get("gender").toString();
                                snls.setText("СНИЛС: "+title);
                                nm.setText("ФИ: "+namesurname);
                                gndr.setText("Пол: "+gender);
                            }
                        }
                    });
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_patient, container, false);
    }
    public void getID(TextView id){

    }
}