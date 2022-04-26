package com.example.bloodbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.bloodbank.ui.home.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestBlood extends Fragment {
    Button req_blood;
    Spinner blood;
    EditText name,age,gender,city,contact;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.requestblood_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        req_blood=view.findViewById(R.id.req_blood_btn);
        blood=view.findViewById(R.id.req_blood_spinner);
        name=view.findViewById(R.id.req_Name);
        age=view.findViewById(R.id.req_age);
        gender=view.findViewById(R.id.req_Gender);
        city=view.findViewById(R.id.req_city);
        contact=view.findViewById(R.id.req_contactNo);
        req_blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callapi();
            }
        });


    }
    void callapi()
    {
        AndroidNetworking.post(Api.ROOT_URL+"bloodmatch/post_req.php")

                .addBodyParameter("name", name.getText().toString())
                .addBodyParameter("blood", blood.getSelectedItem().toString())
                .addBodyParameter("age", age.getText().toString())
                .addBodyParameter("gender", gender.getText().toString())
                .addBodyParameter("city", city.getText().toString())
                .addBodyParameter("contact", contact.getText().toString())
                .addBodyParameter("userid", Dashboard.userid)
                .addBodyParameter("status","")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("error").equals("false"))
                            {
                                Toast.makeText(getContext(), "Request Posted", Toast.LENGTH_SHORT).show();

                                FragmentManager fm = getFragmentManager();
                                FragmentTransaction ft = fm.beginTransaction();
                                HomeFragment requestBlood = new HomeFragment ();
                                ft.replace(R.id.nav_host_fragment_content_dashboard,requestBlood );
                                ft.addToBackStack(null);
                                ft.commit();
                            }
                            else
                            {
                                Toast.makeText(getContext(), "Email Already Registered", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });


    }
}