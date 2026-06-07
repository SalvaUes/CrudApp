package sv.edu.ues.vl23003.crudapp.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import sv.edu.ues.vl23003.crudapp.R;
import sv.edu.ues.vl23003.crudapp.ui.clientes.ClientesActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNavigation = findViewById(R.id.bottomNavigation);

        // Marcar Inicio como seleccionado
        bottomNavigation.setSelectedItemId(R.id.nav_home);

        bottomNavigation.setOnItemSelectedListener(item -> {

            if(item.getItemId() == R.id.nav_home){
                return true;
            }

            if(item.getItemId() == R.id.nav_clientes){

                startActivity(
                        new Intent(this, ClientesActivity.class));

                finish();

                return true;
            }

            return false;
        });
    }
}