package com.example.bloodbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodbank.ui.home.HomeFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodbank.databinding.ActivityDashboardBinding;

public class Dashboard extends AppCompatActivity {

    public static String key;
    Bundle b;
    public static String userid;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDashboardBinding binding;
    TextView name,email,city,gender,contact,name_title,gen;


    public static String loginas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        b=getIntent().getExtras();
        userid= b.getString("id");
        loginas= b.getString("loginas");

        Toast.makeText(Dashboard.this, ""+userid+" "+b.getString("email")+" "+b.getString("name")+" "+b.getString("city")+" "+b.getString("gender")+" "+b.getString("contact"), Toast.LENGTH_SHORT).show();
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarDashboard.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View headerView=navigationView.getHeaderView(0);
        name_title=headerView.findViewById(R.id.title);
        name=headerView.findViewById(R.id.name);
        email=headerView.findViewById(R.id.email);
        city=headerView.findViewById(R.id.city);
        gender=headerView.findViewById(R.id.gender);
        contact=headerView.findViewById(R.id.contact);
        gen=headerView.findViewById(R.id.gen);


       if(b.getString("loginas").equals("hospital"))
       {
           name_title.setText("Institute :");
           gen.setText("Address :");
       }

        name.setText(b.getString("name"));
        email.setText(b.getString("email"));
        city.setText(b.getString("city"));
        gender.setText(b.getString("gender"));
        contact.setText(b.getString("contact"));


//        Fragment docmain = new HomeFragment();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//// Replace whatever is in the fragment_container view with this fragment,
//// and add the transaction to the back stack
//        transaction.replace(R.id.nav_host_fragment_content_dashboard, docmain,"mainfragment");
//        transaction.addToBackStack(null);
//
//// Commit the transaction
//        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

//            HomeFragment fragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("mainfragment"); //"My_FRAGMENT" is its tag
            if (key.equals("true")) {
                Toast.makeText(this, "Use Drawer Logout", Toast.LENGTH_SHORT).show();
            }
            else
            {
                FindBlood.str_blood="none";
                FindBlood.str_city="none";
                super.onBackPressed();


            }

//            CapturedImageForPatFragment cap= (CapturedImageForPatFragment) getSupportFragmentManager().findFragmentByTag("captureimage_p"); //"My_FRAGMENT" is its tag
//            if (cap.isVisible()) {
//                Toast.makeText(this, "Use Buttons", Toast.LENGTH_SHORT).show();
//            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(Dashboard.this, "clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Dashboard.this,LoginActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}