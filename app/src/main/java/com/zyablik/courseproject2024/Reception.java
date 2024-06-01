package com.zyablik.courseproject2024;

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

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Reception#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reception extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button doctors_table;
    private Button print_docs;
    private Button doctors;
    private Button about_patient;

    public Reception() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Reception.
     */
    // TODO: Rename and change types and number of parameters
    public static Reception newInstance(String param1, String param2) {
        Reception fragment = new Reception();
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
        doctors = view.findViewById(R.id.doctors_button);
        about_patient = view.findViewById(R.id.patient_info);
        doctors_table = view.findViewById(R.id.doctors_table);
        print_docs = view.findViewById(R.id.print_docs);
        TextView header = getActivity().findViewById(R.id.header_text);
        doctors_table.setOnClickListener(v -> {
            Toast.makeText(getContext(), R.string.in_progress, Toast.LENGTH_LONG).show();
        });
        print_docs.setOnClickListener(v -> {
            Toast.makeText(getContext(), R.string.in_progress, Toast.LENGTH_LONG).show();
        });
        FragmentManager fm = getActivity().getSupportFragmentManager();
        doctors.setOnClickListener(v -> {
            Fragment Doctors = new Doctors();
            header.setText(R.string.doctors);
            fm.beginTransaction().replace(R.id.main_fragment_manager, Doctors, "Doc").commit();
        });
        about_patient.setOnClickListener(v -> {
            Fragment Patient = new AboutPatient();
            header.setText(R.string.about_patient);
            fm.beginTransaction().replace(R.id.main_fragment_manager, Patient, "Patient").commit();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reception, container, false);
    }
}