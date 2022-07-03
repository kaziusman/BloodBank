package com.example.bloodbank.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.bloodbank.Api;
import com.example.bloodbank.Dashboard;
import com.example.bloodbank.LoginActivity;
import com.example.bloodbank.R;
import com.example.bloodbank.databinding.FragmentGalleryBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileUserFragment extends Fragment {
    EditText email,pass,fname,lname,gender,age,city,contact;
    Spinner blood;
    Button update;
    EditText hosp_email,hosp_pass,hosp_institute,hosp_city,hosp_address,hosp_contact;
    Button hosp_update;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
if(Dashboard.loginas.equals("user")) {
    return inflater.inflate(R.layout.profile_user_fragment, container, false);
}
        else if(Dashboard.loginas.equals("hospital")) {
    return inflater.inflate(R.layout.profile_hosp_fragment, container, false);
}
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Dashboard.key="false";
        if(Dashboard.loginas.equals("user")) {
            email = view.findViewById(R.id.up_user_email);

            pass = view.findViewById(R.id.up_user_pass);
            fname = view.findViewById(R.id.up_user_FName);
            lname = view.findViewById(R.id.up_user_LName);
            gender = view.findViewById(R.id.up_user_Gender);
            age = view.findViewById(R.id.up_user_age);
            blood = view.findViewById(R.id.up_user_blood_group);
            city = view.findViewById(R.id.up_user_city);
            contact = view.findViewById(R.id.up_user_contactNo);
            update = view.findViewById(R.id.up_btn_user_signup);
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updaterec();
                }
            });
            callapi();
        }
        else if(Dashboard.loginas.equals("hospital"))
        {
            hosp_email=view.findViewById(R.id.up_hos_s_email);
            hosp_pass=view.findViewById(R.id.up_hos_pass);
            hosp_institute=view.findViewById(R.id.up_hos_institution_name);
            hosp_city=view.findViewById(R.id.up_hos_city);
            hosp_address=view.findViewById(R.id.up_hos_address);
            hosp_contact=view.findViewById(R.id.up_hos_contactNo);
            hosp_update=view.findViewById(R.id.up_btn_hos_signup);
            hosp_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updatehosprec();
                }
            });
            callhospapi();
        }
    }
    public void updatehosprec()
    {
        AndroidNetworking.post(Api.ROOT_URL+"bloodmatch/updatehosp.php")
                .addBodyParameter("id", Dashboard.userid)
                .addBodyParameter("institute_name", hosp_institute.getText().toString())
                .addBodyParameter("address", hosp_address.getText().toString())
                 .addBodyParameter("email", hosp_email.getText().toString())
                .addBodyParameter("password", hosp_pass.getText().toString())
                .addBodyParameter("city",hosp_city.getText().toString())
                .addBodyParameter("contact",hosp_contact.getText().toString())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(LoginActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                        try {


                            if(response.getString("success").equals("true"))
                            {
                                Toast.makeText(getContext(), "Updated... \n You need to Re Login to view Updates", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                        i.putExtra("key","this");
//                        startActivity(i);
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void callhospapi()
    {
        AndroidNetworking.post(Api.ROOT_URL+"bloodmatch/gethospbyid.php")
                .addBodyParameter("id", Dashboard.userid)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(LoginActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                        try {
                            if(response.getString("error").equals("true"))
                            {
                                Toast.makeText(getContext(), "Invalid email/password", Toast.LENGTH_SHORT).show();
                            }
                            else if (response.getString("error").equals("false")){
                                hosp_email.setText(response.getJSONObject("user").getString("email"));
                                hosp_pass.setText(response.getJSONObject("user").getString("password"));
                                hosp_institute.setText(response.getJSONObject("user").getString("institute_name"));
                                hosp_city.setText(response.getJSONObject("user").getString("city"));
                                hosp_address.setText(response.getJSONObject("user").getString("address"));
                                hosp_contact.setText(response.getJSONObject("user").getString("contact"));



                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                        i.putExtra("key","this");
//                        startActivity(i);
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
                    }
                });



                    }
    public void callapi()
    {
        AndroidNetworking.post(Api.ROOT_URL+"bloodmatch/getuserbyid.php")
                .addBodyParameter("id", Dashboard.userid)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(LoginActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                        try {
                            if(response.getString("error").equals("true"))
                            {
                                Toast.makeText(getContext(), "Invalid email/password", Toast.LENGTH_SHORT).show();
                            }
                            else if (response.getString("error").equals("false")){
                                email.setText(response.getJSONObject("user").getString("email"));
                                pass.setText(response.getJSONObject("user").getString("password"));
                                fname.setText(response.getJSONObject("user").getString("fname"));
                                lname.setText(response.getJSONObject("user").getString("lname"));
                                gender.setText(response.getJSONObject("user").getString("gender"));
                                age.setText(response.getJSONObject("user").getString("age"));

                                city.setText(response.getJSONObject("user").getString("city"));
                                contact.setText(response.getJSONObject("user").getString("contact"));
                                for (int i = 0; i < blood.getCount(); i++) {
                                    if (blood.getItemAtPosition(i).equals(response.getJSONObject("user").getString("blood"))) {
                                        blood.setSelection(i);
                                        break;
                                    }
                                }




                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                        i.putExtra("key","this");
//                        startActivity(i);
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
                    }
                });

    }
    public void updaterec()
    {
        AndroidNetworking.post(Api.ROOT_URL+"bloodmatch/updateuser.php")
                .addBodyParameter("id", Dashboard.userid)
                .addBodyParameter("fname", fname.getText().toString())
                .addBodyParameter("lname", lname.getText().toString())
                .addBodyParameter("gender", gender.getText().toString())
                .addBodyParameter("age", age.getText().toString())
                .addBodyParameter("blood", blood.getSelectedItem().toString())
                .addBodyParameter("email", email.getText().toString())
                .addBodyParameter("password", pass.getText().toString())
                .addBodyParameter("city",city.getText().toString())
                .addBodyParameter("contact",contact.getText().toString())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(LoginActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                        try {


                            if(response.getString("success").equals("true"))
                            {
                                Toast.makeText(getContext(), "Updated... \n You need to Re Login to view Updates", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                        i.putExtra("key","this");
//                        startActivity(i);
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}