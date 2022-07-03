package com.example.bloodbank;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Hospital_adapter extends RecyclerView.Adapter<Hospital_adapter.viewHolder> {

    Context context;
    ArrayList<Hospital_ModelClass> arrayList;

    public Hospital_adapter(Context context, ArrayList<Hospital_ModelClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public Hospital_adapter.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.hosp_item_model, viewGroup, false);
        return new Hospital_adapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(Hospital_adapter.viewHolder viewHolder, int position) {
        viewHolder.name.setText(arrayList.get(position).getName());
        viewHolder.address.setText(arrayList.get(position).getAddress());
        viewHolder.blood.setText(arrayList.get(position).getBlood());
        viewHolder.contact.setText(arrayList.get(position).getContact());
        viewHolder.city.setText(arrayList.get(position).getCity());
        viewHolder.numofbottles.setText(arrayList.get(position).getNumofbottles());
        viewHolder.id.setText(arrayList.get(position).getId());
//        Glide.with(viewHolder.image)
//                .load(arrayList.get(position).getImageUrl())
//                .fitCenter()
//                .into(viewHolder.image);
//        viewHolder.name.setText(arrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView blood,name,address,numofbottles,city,contact,id;
        Button btn;
        CardView itemcard;

        public viewHolder(View itemView) {
            super(itemView);
            btn=itemView.findViewById(R.id.item_hosp_btn);
            blood = itemView.findViewById(R.id.item_blood_avail);
            id = itemView.findViewById(R.id.item_hospid);
            address = itemView.findViewById(R.id.item_address);
            name = itemView.findViewById(R.id.item_inst_name);
            numofbottles = itemView.findViewById(R.id.item_numofbottles);
            city = itemView.findViewById(R.id.item_hosp_city);
            contact = itemView.findViewById(R.id.item_hosp_contact);
            itemcard = itemView.findViewById(R.id.item_hospcard);
            itemcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Toast.makeText(context, ""+id.getText(), Toast.LENGTH_SHORT).show();

                }
            });
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                    builder.setTitle("Reason");
// I'm using fragment here so I'm using getView() to provide ViewGroup
// but you can provide here any other instance of ViewGroup from your Fragment / Activity
                    View viewInflated = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.text_input, null);
// Set up the input
                    final EditText input = (EditText) viewInflated.findViewById(R.id.input);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    builder.setView(viewInflated);

// Set up the buttons
                    builder.setPositiveButton("Ask", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            String reason = input.getText().toString();
                            AndroidNetworking.post(Api.ROOT_URL+"bloodmatch/requestbyuser.php")
                                    .addBodyParameter("reason", reason)
                                    .addBodyParameter("requestto", id.getText().toString())
                                    .addBodyParameter("requestby", Dashboard.userid)
                                    .addBodyParameter("name", LoginActivity.name)
                                    .addBodyParameter("city", LoginActivity.city)
                                    .addBodyParameter("blood", blood.getText().toString())
                                    .addBodyParameter("contact", LoginActivity.contact)
                                    .setTag("test")
                                    .setPriority(Priority.MEDIUM)
                                    .build()
                                    .getAsJSONObject(new JSONObjectRequestListener() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                if(response.getString("error").equals("false"))
                                                {
                                                    Toast.makeText(v.getContext(), "Asked.", Toast.LENGTH_SHORT).show();

//                                                    Intent intent = new Intent(v.getRootView().getContext(), LoginActivity.class);
//                                                    startActivity(intent);
                                                }
                                                else
                                                {
                                                    Toast.makeText(v.getContext(), "Some Error Occured", Toast.LENGTH_SHORT).show();
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
                    });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();

                }
            });

        }

    }



}

