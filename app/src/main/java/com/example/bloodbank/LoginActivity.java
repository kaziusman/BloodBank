package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
                callapi();
//                Intent i=new Intent(LoginActivity.this,Dashboard.class);
//                startActivity(i);
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
                                Toast.makeText(LoginActivity.this, response+"", Toast.LENGTH_SHORT).show();
//                            i.putExtra("email",response.getJSONObject("user").getString("email"));
//                            i.putExtra("name",response.getJSONObject("user").getString("name"));
//                            i.putExtra("city",response.getJSONObject("user").getString("city"));
//                            i.putExtra("gender",response.getJSONObject("user").getString("gender"));
//                            i.putExtra("contact",response.getJSONObject("user").getString("contact"))
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
                    }
                });
    }
}