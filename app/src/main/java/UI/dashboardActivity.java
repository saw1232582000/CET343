package UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.assignment.LoginActivity;
import com.example.assignment.R;
import com.example.assignment.SignupActivity;
import com.google.android.material.navigation.NavigationView;

import android.view.Gravity;
import android.view.MenuItem;

public class dashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private String username;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // supportActionBar?.hide();
        setContentView(R.layout.dashboard);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        username=bundle.getString("username");
        password=bundle.getString("password");
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_Fragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }
 @Override
 public boolean onNavigationItemSelected(@NonNull MenuItem item) {
     switch (item.getItemId()){
         case R.id.nav_home:
             getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_Fragment()).commit();
             drawerLayout.closeDrawer(Gravity.LEFT);
             break;
         case R.id.nav_property:
             //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Property_Fragment()).commit();
             Property_Fragment pfragment=new Property_Fragment();
             Bundle args = new Bundle();
             args.putString("username", username);
             args.putString("password", password);
             pfragment.setArguments(args);
             FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
             transaction.replace(R.id.fragment_container, pfragment); // or replace()
             transaction.commit();
             drawerLayout.closeDrawer(Gravity.LEFT);
             break;
         case R.id.nav_logout:
             Intent intent=new Intent(dashboardActivity.this, LoginActivity.class);
             startActivity(intent);
             break;
     }
    return true;
 }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
