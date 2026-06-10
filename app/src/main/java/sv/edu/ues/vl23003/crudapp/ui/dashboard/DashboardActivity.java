package sv.edu.ues.vl23003.crudapp.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import sv.edu.ues.vl23003.crudapp.R;
import sv.edu.ues.vl23003.crudapp.data.local.entity.ServicioEntity;
import sv.edu.ues.vl23003.crudapp.data.local.entity.database.AppDatabase;
import sv.edu.ues.vl23003.crudapp.ui.clientes.ClientesActivity;
import sv.edu.ues.vl23003.crudapp.ui.servicios.ServiciosActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    private AppDatabase db;
    private TextView tvTotalClientes, tvTotalServicios, tvMontoTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        db = AppDatabase.getDatabase(this);

        tvTotalClientes = findViewById(R.id.tv_total_clientes);
        tvTotalServicios = findViewById(R.id.tv_total_servicios);
        tvMontoTotal = findViewById(R.id.tv_monto_total);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.nav_home);

        bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                return true;
            }
            if (item.getItemId() == R.id.nav_clientes) {
                startActivity(new Intent(this, ClientesActivity.class));
                finish();
                return true;
            }
            if (item.getItemId() == R.id.nav_servicios) {
                startActivity(new Intent(this, ServiciosActivity.class));
                finish();
                return true;
            }
            return false;
        });

        cargarDatos();
    }

    private void cargarDatos() {
        new Thread(() -> {
            int totalClientes = db.clienteDao().getAll().size();
            List<ServicioEntity> listaServicios = db.servicioDao().getAll();
            int totalServicios = listaServicios.size();
            double montoTotal = 0;
            for (ServicioEntity s : listaServicios) {
                montoTotal += s.getPrecio();
            }

            double finalMonto = montoTotal;
            runOnUiThread(() -> {
                tvTotalClientes.setText(String.valueOf(totalClientes));
                tvTotalServicios.setText(String.valueOf(totalServicios));
                tvMontoTotal.setText("$" + String.format("%.2f", finalMonto));
            });
        }).start();
    }
}
