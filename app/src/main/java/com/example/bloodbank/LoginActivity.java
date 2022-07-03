package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    Button login,signup;
    TextView hospital_login,user_login;
    String select;
    EditText user,pass;
    public static String name,city,contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.login_btn);
        user=findViewById(R.id.login_username);
        pass=findViewById(R.id.login_pass);
        signup=findViewById(R.id.signup_btn);
        select="hospital";
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent i=new Intent(LoginActivity.this,Dashboard.class);
//                startActivity(i);

                if(select.equals("hospital"))
                {
                    callhospapi();
//                    Intent i = new Intent(LoginActivity.this,SignupActivity_Hospital.class);
//                    startActivity(i);

                }
                else if(select.equals("user"))
                {
                    callapi();
//                    Toast.makeText(LoginActivity.this, "check", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(LoginActivity.this,SignupActivity_User.class);
//                    startActivity(i);

                }

            }
        });
        findViewById(R.id.signup_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(select.equals("hospital"))
                {

                    Intent i = new Intent(LoginActivity.this,SignupActivity_Hospital.class);
                    startActivity(i);

                }
                else if(select.equals("user"))
                {
                    Toast.makeText(LoginActivity.this, "check", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this,SignupActivity_User.class);
                    startActivity(i);

                }

            }
        });
        hospital_login = findViewById(R.id.hospital_login);
        user_login = findViewById(R.id.user_login);
        hospital_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hospital_login.setBackground(getResources().getDrawable(R.drawable.tab_left_selected));
                user_login.setBackground(getResources().getDrawable(R.drawable.tab_right));
                select="hospital";
            }
        });
        user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user_login.setBackground(getResources().getDrawable(R.drawable.tab_right_selected));
                hospital_login.setBackground(getResources().getDrawable(R.drawable.tab_left));
                select="user";
            }
        });

    }
    public void callhospapi()
    {

        AndroidNetworking.post(Api.ROOT_URL+"bloodmatch/hosp_login.php")
                .addBodyParameter("email", user.getText().toString())
                .addBodyParameter("password", pass.getText().toString())

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
                                Toast.makeText(LoginActivity.this, "Invalid email/password", Toast.LENGTH_SHORT).show();
                            }
                            else if (response.getString("error").equals("false")){
                                Intent i = new Intent(LoginActivity.this, Dashboard.class);
                                i.putExtra("id",response.getJSONObject("user").getString("id"));
                                Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
//                                name=response.getJSONObject("user").getString("fname")+"" +response.getJSONObject("user").getString("lname");
//                                city=response.getJSONObject("user").getString("city");
//                                contact=response.getJSONObject("user").getString("contact");
//                                Toast.makeText(LoginActivity.this, response+"", Toast.LENGTH_SHORT).show();
                                i.putExtra("email",response.getJSONObject("user").getString("email"));
                                i.putExtra("name",response.getJSONObject("user").getString("institute_name"));
                                i.putExtra("city",response.getJSONObject("user").getString("city"));
                                i.putExtra("gender",response.getJSONObject("user").getString("address"));
                                i.putExtra("contact",response.getJSONObject("user").getString("contact"));
                                i.putExtra("loginas","hospital");
                                startActivity(i);

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
                        Toast.makeText(LoginActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                });

    }
    public void callapi()
    {
        AndroidNetworking.post(Api.ROOT_URL+"bloodmatch/login.php")
                .addBodyParameter("email", user.getText().toString())
                .addBodyParameter("password", pass.getText().toString())

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
                                Toast.makeText(LoginActivity.this, "Invalid email/password", Toast.LENGTH_SHORT).show();
                            }
                            else if (response.getString("error").equals("false")){
                                Intent i = new Intent(LoginActivity.this, Dashboard.class);
                                i.putExtra("id",response.getJSONObject("user").getString("id"));
                                name=response.getJSONObject("user").getString("fname")+" " +response.getJSONObject("user").getString("lname");
                                city=response.getJSONObject("user").getString("city");
                                contact=response.getJSONObject("user").getString("contact");
                                Toast.makeText(LoginActivity.this, response+"", Toast.LENGTH_SHORT).show();
                                i.putExtra("email",response.getJSONObject("user").getString("email"));
                                i.putExtra("name",name);
                                i.putExtra("city",city);
                                i.putExtra("gender",response.getJSONObject("user").getString("gender"));
                                i.putExtra("contact",contact);
                                i.putExtra("loginas","user");
                                startActivity(i);

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
                        Toast.makeText(LoginActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                });
    }
//    @TargetApi(Build.VERSION_CODES.O)
//    private void disableAutofill() {
//        getWindow().getDecorView().setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS);
//    }
}