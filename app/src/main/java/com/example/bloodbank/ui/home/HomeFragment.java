package com.example.bloodbank.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.bloodbank.AddBlood;
import com.example.bloodbank.Api;
import com.example.bloodbank.BloodRequests;
import com.example.bloodbank.Dashboard;
import com.example.bloodbank.FindBlood;
import com.example.bloodbank.R;
import com.example.bloodbank.Requests_to_user;
import com.example.bloodbank.databinding.FragmentHomeBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {
    CardView donor,seeker,addblood;
    TextView tv_noti;
    ImageView noti;
    LinearLayout blood_add_view;
    public static String key;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Dashboard.key="true";
    donor=view.findViewById(R.id.donor);
        seeker=view.findViewById(R.id.seeker);
        noti=view.findViewById(R.id.notification);
        tv_noti=view.findViewById(R.id.tv_noti);
        addblood=view.findViewById(R.id.addblood);
        blood_add_view=view.findViewById(R.id.add_view);
        if(Dashboard.loginas.equals("user")) {
            blood_add_view.setVisibility(View.INVISIBLE);
        }



        checknotification();
        addblood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                AddBlood findBlood = new AddBlood();
                ft.replace(R.id.nav_host_fragment_content_dashboard,findBlood );
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        seeker.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          FragmentManager fm = getFragmentManager();
                                          FragmentTransaction ft = fm.beginTransaction();
                                          FindBlood findBlood = new FindBlood();
                                          ft.replace(R.id.nav_host_fragment_content_dashboard,findBlood );
                                          ft.addToBackStack(null);
                                          key="seeker";


                                          ft.commit();
                                      }
                                  }
        );
        donor.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          FragmentManager fm = getFragmentManager();
                                          FragmentTransaction ft = fm.beginTransaction();
                                          BloodRequests findBlood = new BloodRequests();
                                          ft.replace(R.id.nav_host_fragment_content_dashboard,findBlood );
                                          ft.addToBackStack(null);
                                          key="donor";
                                          ft.commit();
                                      }
                                  }
        );
        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Requests_to_user findBlood = new Requests_to_user();
                ft.replace(R.id.nav_host_fragment_content_dashboard,findBlood );
                ft.addToBackStack(null);
                key="noti";
                ft.commit();
            }
        });
    }
    public void checknotification()
    {
        AndroidNetworking.post(Api.ROOT_URL+"bloodmatch/notifications.php")
                .addBodyParameter("requestto", Dashboard.userid)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                        try {
                            if( response.getJSONArray("allrequests").length()>0)
                            {
                                noti.setImageResource(R.drawable.ic_notification_on);
                                tv_noti.setText(Integer.toString(response.getJSONArray("allrequests").length()));
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