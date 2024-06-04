package com.zyablik.courseproject2024;

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

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addDoc#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addDoc extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView name_field;
    private TextView surname_field;
    private TextView spec_field;
    private TextView room_field;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button add_butt;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public addDoc() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addDoc.
     */
    // TODO: Rename and change types and number of parameters
    public static addDoc newInstance(String param1, String param2) {
        addDoc fragment = new addDoc();
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
        name_field = view.findViewById(R.id.add_doc_name);
        surname_field = view.findViewById(R.id.add_doc_surname);
        room_field = view.findViewById(R.id.add_doc_room);
        spec_field = view.findViewById(R.id.add_doc_spec);
        add_butt = view.findViewById(R.id.add_doc_button);
        add_butt.setOnClickListener(v ->{
            if(!name_field.getText().toString().isEmpty() &&
                    !surname_field.getText().toString().isEmpty()&&
                    !room_field.getText().toString().isEmpty()&&
                    !spec_field.getText().toString().isEmpty()){
                addDoc(name_field.getText().toString(),surname_field.getText().toString(),
                        room_field.getText().toString(),spec_field.getText().toString(),view);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_fragment_manager, new Reception(), "rec").commit();
            }else{
                Toast.makeText(view.getContext(), "Все должно быть заполнено!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_doc, container, false);
    }
    public void addDoc(String name, String surname, String roomstr, String spec, View view){
        int room = Integer.parseInt(roomstr);
        Map<String, Object> user = new HashMap<>();

        user.put("name", name);
        user.put("surname", surname);
        user.put("room", room);
        user.put("speciality", spec);

        db.collection("doctors").document(name+room+surname)
                .set(user)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(view.getContext(), "Doctor added", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(unused -> {
                    Toast.makeText(view.getContext(), "Couldn't add doctor", Toast.LENGTH_SHORT).show();
                });
    }
}