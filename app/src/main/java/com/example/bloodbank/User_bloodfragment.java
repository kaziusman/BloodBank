package com.example.bloodbank;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class User_bloodfragment extends Fragment {
    ArrayList<Model_class> itemarrayList;
    RecyclerView item_recyclerView;
    List<String> item_id=new ArrayList<String>();
    List<String> item_blood=new ArrayList<String>();
    List<String> item_name=new ArrayList<String>();
    List<String> item_city=new ArrayList<String>();
    List<String> item_contact=new ArrayList<String>();
    List<String> item_gender=new ArrayList<String>();
    List<String> item_age=new ArrayList<String>();
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.user_bloodfragment, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        item_recyclerView = view.findViewById(R.id.lv_available_blood);
        if(!FindBlood.str_city.equals("none") && !FindBlood.str_blood.equals("none"))
        {
            showrequestsbyfilter();
        }
        else
        {
            showallrequests();
        }



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
    public void showrequestsbyfilter()
    {
        AndroidNetworking.post(Api.ROOT_URL+"bloodmatch/finduserbyfilter.php")
                .addBodyParameter("blood", FindBlood.str_blood)
                .addBodyParameter("city", FindBlood.str_city)

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
}
