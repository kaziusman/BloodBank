package com.example.bloodbank;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FindBlood extends Fragment {
    TextView hospital,user;
    Button find,postreq;
    ArrayList<Model_class> itemarrayList;
    ArrayList<Hospital_ModelClass> hosp_itemarrayList;
    public static String str_blood="none";
    public static String str_city="none";
    List<String> hosp_item_id=new ArrayList<String>();
    List<String> hosp_item_blood=new ArrayList<String>();
    List<String> hosp_item_name=new ArrayList<String>();
    List<String> hosp_item_city=new ArrayList<String>();
    List<String> hosp_item_contact=new ArrayList<String>();
    List<String> hosp_item_address=new ArrayList<String>();
    List<String> hosp_item_numofbottles=new ArrayList<String>();


    RecyclerView item_recyclerView;
    List<String> item_id=new ArrayList<String>();
    List<String> item_blood=new ArrayList<String>();
    List<String> item_name=new ArrayList<String>();
    List<String> item_city=new ArrayList<String>();
    List<String> item_contact=new ArrayList<String>();
    List<String> item_gender=new ArrayList<String>();
    List<String> item_age=new ArrayList<String>();
    List<String> spinnerblood =  new ArrayList<String>();
    List<String> spinnercity=  new ArrayList<String>();
    ArrayAdapter<String> adapter;
    String select;
    Spinner blood,city;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.findblood_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Dashboard.key="false";
    find=view.findViewById(R.id.find);
        postreq=view.findViewById(R.id.btn_post_req);
        item_recyclerView = view.findViewById(R.id.lv_available_blood);
        blood=view.findViewById(R.id.find_blood);
        city=view.findViewById(R.id.find_city);
        hospital = view.findViewById(R.id.hospital_login);
        user = view.findViewById(R.id.user_login);
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_blood="none";
                str_city="none";
                hospital.setBackground(getResources().getDrawable(R.drawable.tab_left_selected));
                user.setBackground(getResources().getDrawable(R.drawable.tab_right));
                select="hospital";
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Hospital_bloodfragment requestBlood = new Hospital_bloodfragment();
                ft.replace(R.id.blood_fragment,requestBlood );
                ft.addToBackStack(null);
                ft.commit();

            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_blood="none";
                str_city="none";
                user.setBackground(getResources().getDrawable(R.drawable.tab_right_selected));
                hospital.setBackground(getResources().getDrawable(R.drawable.tab_left));
                select="user";
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                User_bloodfragment requestBlood = new User_bloodfragment();
                ft.replace(R.id.blood_fragment,requestBlood );
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        spinnercity.add("***Select***");
        showallcities();

       // showallrequests();
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(select.equals("user")) {
                    if (!city.getSelectedItem().equals("***Select***") && select.equals("user")) {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        User_bloodfragment requestBlood = new User_bloodfragment();

                        str_blood=blood.getSelectedItem().toString();
                        str_city=city.getSelectedItem().toString();

                        ft.replace(R.id.blood_fragment,requestBlood );
                        ft.addToBackStack(null);
                        ft.commit();



//                        showrequestsbyfilter();
                    } else if (!city.getSelectedItem().equals("***Select***") && select.equals("hospital")) {

                    }
                }
                else if(select.equals("hospital"))
                {
                    if (!city.getSelectedItem().equals("***Select***") && select.equals("hospital")) {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Hospital_bloodfragment requestBlood = new Hospital_bloodfragment();

                        str_blood=blood.getSelectedItem().toString();
                        str_city=city.getSelectedItem().toString();

                        ft.replace(R.id.blood_fragment,requestBlood );
                        ft.addToBackStack(null);
                        ft.commit();
                    } else if (!city.getSelectedItem().equals("***Select***") && select.equals("hospital")) {

                    }
                }
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                RequestBlood requestBlood = new RequestBlood();
//                ft.replace(R.id.nav_host_fragment_content_dashboard,requestBlood );
//                ft.addToBackStack(null);
//                ft.commit();
            }
        });
        if(Dashboard.loginas.equals("hospital")) {
            postreq.setVisibility(View.INVISIBLE);
        }
        postreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                RequestBlood requestBlood = new RequestBlood();
                ft.replace(R.id.nav_host_fragment_content_dashboard,requestBlood );
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }
    public void showlist()
    {

        itemarrayList = new ArrayList<>();

        item_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        item_recyclerView.setItemAnimator(new DefaultItemAnimator());

        for (int i = 0; i < item_blood.size(); i++) {
            Model_class itemModel = new Model_class();

            itemModel.setBlood(item_blood.get(i));
            itemModel.setName( item_name.get(i));
            itemModel.setId( item_id.get(i));
            itemModel.setAge( item_age.get(i));
            itemModel.setContact(item_contact.get(i));
            itemModel.setCity(item_city.get(i));
            //add in array list
            itemarrayList.add(itemModel);
        }

        Blood_Req_Adapter winteritemuperadapter = new Blood_Req_Adapter(getActivity().getApplicationContext(), itemarrayList);
        item_recyclerView.setAdapter(winteritemuperadapter);
    }
    public void  showallcities()
    {
        AndroidNetworking.post(Api.ROOT_URL+"bloodmatch/getallregcities.php")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                            Toast.makeText(getContext(), response+"", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < response.getJSONArray("allcities").length(); i++)
                            {

                                spinnercity.add(response.getJSONArray("allcities").getJSONObject(i).getString("city"));
                            }

                            adapter= new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnercity);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            city.setAdapter(adapter);

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }
    public void showallrequests()
    {
        AndroidNetworking.post(Api.ROOT_URL+"bloodmatch/findalluser.php")


                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for (int i = 0; i < response.getJSONArray("allrequests").length(); i++)
                            {

                                if(response.getJSONArray("allrequests").getJSONObject(i).getString("status").equals("") && !response.getJSONArray("allrequests").getJSONObject(i).getString("id").equals(Dashboard.userid)) {
                                    String name=response.getJSONArray("allrequests").getJSONObject(i).getString("fname")+" "+response.getJSONArray("allrequests").getJSONObject(i).getString("lname");
                                    item_blood.add(response.getJSONArray("allrequests").getJSONObject(i).getString("blood"));
                                    item_name.add(name);
                                    item_age.add(response.getJSONArray("allrequests").getJSONObject(i).getString("age"));
                                    item_contact.add(response.getJSONArray("allrequests").getJSONObject(i).getString("contact"));
                                    item_id.add(response.getJSONArray("allrequests").getJSONObject(i).getString("id"));
                                    item_city.add(response.getJSONArray("allrequests").getJSONObject(i).getString("city"));
                                    item_gender.add(response.getJSONArray("allrequests").getJSONObject(i).getString("gender"));
                                }


                            }

                            showlist();
                            item_blood.clear();
                            item_name.clear();
                            item_age.clear();
                            item_contact.clear();
                            item_id.clear();
                            item_city.clear();
                            item_gender.clear();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    public void showhosplist()
    {

        hosp_itemarrayList = new ArrayList<>();

        item_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        item_recyclerView.setItemAnimator(new DefaultItemAnimator());

        for (int i = 0; i < hosp_item_blood.size(); i++) {
            Hospital_ModelClass itemModel = new Hospital_ModelClass();

            itemModel.setBlood(hosp_item_blood.get(i));
            itemModel.setName( hosp_item_name.get(i));
            itemModel.setId( hosp_item_id.get(i));
            itemModel.setNumofbottles( hosp_item_numofbottles.get(i));
            itemModel.setContact(hosp_item_contact.get(i));
            itemModel.setCity(hosp_item_city.get(i));
            itemModel.setAddress((hosp_item_address.get(i)));
            //add in array list
            hosp_itemarrayList.add(itemModel);
        }

        Hospital_adapter winteritemuperadapter = new Hospital_adapter(getActivity().getApplicationContext(), hosp_itemarrayList);
        item_recyclerView.setAdapter(winteritemuperadapter);
    }
    public void showrequestsbyfilterhosp()
    {
        AndroidNetworking.post(Api.ROOT_URL+"bloodmatch/gethopitalbyfilter.php")
                .addBodyParameter("blood", blood.getSelectedItem().toString())
                .addBodyParameter("city", city.getSelectedItem().toString())

                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getContext(), response+"", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < response.getJSONArray("allrequests").length(); i++)
                            {
                                if(response.getJSONArray("allrequests").getJSONObject(i).getString("id").equals(Dashboard.userid)) {
                                    hosp_item_blood.add(response.getJSONArray("allrequests").getJSONObject(i).getString("blood_group"));
                                    hosp_item_name.add(response.getJSONArray("allrequests").getJSONObject(i).getString("institute_name"));
                                    hosp_item_address.add(response.getJSONArray("allrequests").getJSONObject(i).getString("address"));
                                    hosp_item_contact.add(response.getJSONArray("allrequests").getJSONObject(i).getString("contact"));
                                    hosp_item_id.add(response.getJSONArray("allrequests").getJSONObject(i).getString("id"));
                                    hosp_item_city.add(response.getJSONArray("allrequests").getJSONObject(i).getString("city"));
                                    hosp_item_numofbottles.add(response.getJSONArray("allrequests").getJSONObject(i).getString("num_of_bottles"));
                                }



                            }

                            showhosplist();
                            hosp_item_blood.clear();
                            hosp_item_name.clear();
                            hosp_item_address.clear();
                            hosp_item_contact.clear();
                            hosp_item_id.clear();
                            hosp_item_city.clear();
                            hosp_item_numofbottles.clear();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }
//    public void showrequestsbyfilter()
//    {
//        AndroidNetworking.post(Api.ROOT_URL+"bloodmatch/finduserbyfilter.php")
//                .addBodyParameter("blood", blood.getSelectedItem().toString())
//                .addBodyParameter("city", city.getSelectedItem().toString())
//
//                .setTag("test")
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            Toast.makeText(getContext(), response+"", Toast.LENGTH_SHORT).show();
//                            for (int i = 0; i < response.getJSONArray("allrequests").length(); i++)
//                            {
//                                if(response.getJSONArray("allrequests").getJSONObject(i).getString("status").equals("") && !response.getJSONArray("allrequests").getJSONObject(i).getString("id").equals(Dashboard.userid)) {
//                                    String name=response.getJSONArray("allrequests").getJSONObject(i).getString("fname")+" "+response.getJSONArray("allrequests").getJSONObject(i).getString("lname");
//                                    item_blood.add(response.getJSONArray("allrequests").getJSONObject(i).getString("blood"));
//                                    item_name.add(name);
//                                    item_age.add(response.getJSONArray("allrequests").getJSONObject(i).getString("age"));
//                                    item_contact.add(response.getJSONArray("allrequests").getJSONObject(i).getString("contact"));
//                                    item_id.add(response.getJSONArray("allrequests").getJSONObject(i).getString("id"));
//                                    item_city.add(response.getJSONArray("allrequests").getJSONObject(i).getString("city"));
//                                    item_gender.add(response.getJSONArray("allrequests").getJSONObject(i).getString("gender"));
//                                }
//
//
//
//                            }
//
//                            showlist();
//                            item_blood.clear();
//                            item_name.clear();
//                            item_age.clear();
//                            item_contact.clear();
//                            item_id.clear();
//                            item_city.clear();
//                            item_gender.clear();
//                        }
//                        catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                    @Override
//                    public void onError(ANError error) {
//                        // handle error
//                    }
//                });
//
//    }


}