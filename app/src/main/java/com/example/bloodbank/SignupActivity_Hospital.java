package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity_Hospital extends AppCompatActivity {
    EditText email,pass,institute,city,address,contact;
    Button submit;
    boolean valid = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_hospital);
        email=findViewById(R.id.hos_s_email);
        pass=findViewById(R.id.hos_pass);
        institute=findViewById(R.id.hos_institution_name);
        city=findViewById(R.id.hos_city);
        address=findViewById(R.id.hos_address);
        contact=findViewById(R.id.hos_contactNo);
        submit=findViewById(R.id.btn_hos_signup);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                validate();
            }
        });

    }
    public void validate()
    {

        String u_email = email.getText().toString();
        String u_pass = pass.getText().toString();


        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (u_email.isEmpty() || u_email.length() < 3 || !u_email.matches(emailPattern)) {
            email.setError("Email Required");
            email.findFocus();
            valid = false;
        }

        else if (u_pass.isEmpty() || u_pass.length() < 3) {
            pass.setError("at least 3 characters");
            pass.findFocus();
            valid = false;
        }

        else{

            callapi();
        }
    }

    void callapi()
    {
        Toast.makeText(SignupActivity_Hospital.this, "here", Toast.LENGTH_SHORT).show();
        AndroidNetworking.post(Api.ROOT_URL+"bloodmatch/hospital_signup.php")

                .addBodyParameter("institute_name", institute.getText().toString())
                .addBodyParameter("address", address.getText().toString())
                .addBodyParameter("email", email.getText().toString())
                .addBodyParameter("password", pass.getText().toString())
                .addBodyParameter("city",city.getText().toString())
                .addBodyParameter("contact",contact.getText().toString())
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
                                Toast.makeText(SignupActivity_Hospital.this, "Account Created", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(SignupActivity_Hospital.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else if(response.getString("error").equals("true"))
                            {
                                Toast.makeText(SignupActivity_Hospital.this, "Email Already Registered", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(SignupActivity_Hospital.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                });

    }
}