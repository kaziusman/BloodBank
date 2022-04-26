package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity_User extends AppCompatActivity {
    EditText email,pass,fname,lname,gender,age,city,contact;
    Spinner blood;
    Button submit;
    boolean valid = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_user);
        email=findViewById(R.id.user_email);
        pass=findViewById(R.id.user_pass);
        fname=findViewById(R.id.user_FName);
        lname=findViewById(R.id.user_LName);
        gender=findViewById(R.id.user_Gender);
        age=findViewById(R.id.user_age);
        blood=findViewById(R.id.user_blood_group);
        city=findViewById(R.id.user_city);
        contact=findViewById(R.id.user_contactNo);
        submit=findViewById(R.id.btn_user_signup);
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
        AndroidNetworking.post(Api.ROOT_URL+"bloodmatch/user_signup.php")

                .addBodyParameter("fname", fname.getText().toString())
                .addBodyParameter("lname", lname.getText().toString())
                .addBodyParameter("gender", gender.getText().toString())
                .addBodyParameter("age", age.getText().toString())
                .addBodyParameter("blood", blood.getSelectedItem().toString())
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
                                Toast.makeText(SignupActivity_User.this, "Account Created", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(SignupActivity_User.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else if(response.getString("error").equals("true"))
                            {
                                Toast.makeText(SignupActivity_User.this, "Email Already Registered", Toast.LENGTH_SHORT).show();
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