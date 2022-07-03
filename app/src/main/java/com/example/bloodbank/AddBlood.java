package com.example.bloodbank;

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

public class AddBlood extends Fragment {
    Spinner add_blood_spinner;
    EditText addblood_bottles,addblood_donor,addblood_contactNo;
    Button add_blood_btn;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.addblood_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Dashboard.key="false";
        add_blood_spinner=view.findViewById(R.id.add_blood_spinner);
        addblood_bottles=view.findViewById(R.id.addblood_bottles);
        addblood_donor=view.findViewById(R.id.addblood_donor);
        addblood_contactNo=view.findViewById(R.id.addblood_contactNo);
        add_blood_btn=view.findViewById(R.id.add_blood_btn);


        add_blood_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callapi();
            }
        });

    }
    public void  callapi()
    {
        AndroidNetworking.post(Api.ROOT_URL+"bloodmatch/addblood.php")

            .addBodyParameter("blood_group", add_blood_spinner.getSelectedItem().toString())
            .addBodyParameter("num_of_bottels", addblood_bottles.getText().toString())
            .addBodyParameter("donate_by", addblood_donor.getText().toString())
            .addBodyParameter("donor_contact", addblood_contactNo.getText().toString())
            .addBodyParameter("hospital_id", Dashboard.userid)

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
                            Toast.makeText(getContext(), "Error Adding Blood", Toast.LENGTH_SHORT).show();
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
