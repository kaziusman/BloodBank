package com.example.bloodbank;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

public class BloodRequests extends Fragment {
    ArrayList<Model_class> itemarrayList;
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
    Button find;
    Spinner blood,city;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_blood_requests, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Dashboard.key="false";


        find=view.findViewById(R.id.find_request);
        item_recyclerView = view.findViewById(R.id.lv_blood_requests);
        blood=view.findViewById(R.id.blood_request);
        city=view.findViewById(R.id.city_request);

        spinnercity.add("***Select***");
        showallcities();

        showallrequests();
//        showlist();
//        item_blood.add("A-");
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!city.getSelectedItem().equals("***Select***"))
                {
                    showrequestsbyfilter();
                }
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                RequestBlood requestBlood = new RequestBlood();
//                ft.replace(R.id.nav_host_fragment_content_dashboard,requestBlood );
//                ft.addToBackStack(null);
//                ft.commit();
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
        AndroidNetworking.post(Api.ROOT_URL+"bloodmatch/getallcities.php")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getContext(), response+"", Toast.LENGTH_SHORT).show();
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
        AndroidNetworking.post(Api.ROOT_URL+"bloodmatch/allrequests.php")


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
                                if(response.getJSONArray("allrequests").getJSONObject(i).getString("status").equals("") && !response.getJSONArray("allrequests").getJSONObject(i).getString("userid").equals(Dashboard.userid)) {
                                    item_blood.add(response.getJSONArray("allrequests").getJSONObject(i).getString("blood"));
                                    item_name.add(response.getJSONArray("allrequests").getJSONObject(i).getString("name"));
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
public void showrequestsbyfilter()
{
    AndroidNetworking.post(Api.ROOT_URL+"bloodmatch/requestsbyfilter.php")
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
                            if(response.getJSONArray("allrequests").getJSONObject(i).getString("status").equals("")&& !response.getJSONArray("allrequests").getJSONObject(i).getString("userid").equals(Dashboard.userid)) {
                                item_blood.add(response.getJSONArray("allrequests").getJSONObject(i).getString("blood"));
                                item_name.add(response.getJSONArray("allrequests").getJSONObject(i).getString("name"));
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


}
