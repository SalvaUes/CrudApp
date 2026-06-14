package sv.edu.ues.vl23003.crudapp.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import sv.edu.ues.vl23003.crudapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    Toolbar toolbar;

    // Las 3 variables para tus tarjetas ejecutivas
    TextView tvCantClientes, tvCantServicios, tvMontoTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // 1. Configurar la barra superior elegante
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Dashboard Ejecutivo");
        }

        // 2. Vincular los textos donde se verán los números de tus tarjetas
        tvCantClientes = findViewById(R.id.tv_cant_clientes);
        tvCantServicios = findViewById(R.id.tv_cant_servicios);
        tvMontoTotal = findViewById(R.id.tv_monto_total);

        // 3. Insertar los números de prueba en tus tarjetas ejecutivas
        mostrarDatosDashboard();

        // 4. Controlar la barra de abajo de forma segura
        bottomNavigation = findViewById(R.id.bottomNavigation);
        if (bottomNavigation != null) {

            // LA LÍNEA MÁGICA: Le dice a la barra que el botón seleccionado al arrancar es "Clientes"
            // (Para que coincida con el menú que crearon tus compañeros y no explote)
            bottomNavigation.setSelectedItemId(R.id.nav_clientes);

            bottomNavigation.setOnItemSelectedListener(item -> {
                int id = item.getItemId();

                // Si presionan Clientes y ya estamos en el Dashboard, evitamos el bucle
                if (id == R.id.nav_clientes) {
                    // Nos quedamos aquí viendo tu hermoso diseño ejecutivo de forma segura
                    return true;
                } else if (id == R.id.nav_servicios) {
                    Toast.makeText(this, "Abriendo Gestión de Servicios...", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return true;
            });
        }
    }

    /**
     * Pinta los datos numéricos ejecutivos dentro de tus tarjetas visuales
     */
    private void mostrarDatosDashboard() {
        if (tvCantClientes != null) tvCantClientes.setText("42");
        if (tvCantServicios != null) tvCantServicios.setText("12");
        if (tvMontoTotal != null) tvMontoTotal.setText("$2,850.75");
    }
}