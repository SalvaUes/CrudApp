package sv.edu.ues.vl23003.crudapp.ui.servicios;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sv.edu.ues.vl23003.crudapp.R;
import sv.edu.ues.vl23003.crudapp.data.local.entity.ClienteEntity;
import sv.edu.ues.vl23003.crudapp.data.local.entity.ServicioEntity;
import sv.edu.ues.vl23003.crudapp.data.local.entity.database.AppDatabase;
import sv.edu.ues.vl23003.crudapp.ui.clientes.ClientesActivity;
import sv.edu.ues.vl23003.crudapp.ui.dashboard.DashboardActivity;
import sv.edu.ues.vl23003.crudapp.utils.Constantes;

public class ServiciosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppDatabase db;
    private List<ClienteEntity> clientes;
    private List<ServicioEntity> servicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        db = AppDatabase.getDatabase(this);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        });

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.nav_servicios);

        bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_servicios) {
                return true;
            }
            if (item.getItemId() == R.id.nav_home) {
                startActivity(new Intent(this, DashboardActivity.class));
                finish();
                return true;
            }
            if (item.getItemId() == R.id.nav_clientes) {
                startActivity(new Intent(this, ClientesActivity.class));
                finish();
                return true;
            }
            return false;
        });

        recyclerView = findViewById(R.id.recycler_servicios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fabAgregar = findViewById(R.id.fab_agregar_servicio);
        fabAgregar.setOnClickListener(v -> mostrarDialogoNuevoServicio());

        cargarServicios();
    }

    private void cargarServicios() {
        new Thread(() -> {
            servicios = db.servicioDao().getAll();
            clientes = db.clienteDao().getAll();
            runOnUiThread(() -> {
                ServicioAdapter adapter = new ServicioAdapter(servicios, clientes, this::eliminarServicio);
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }

    private void mostrarDialogoNuevoServicio() {
        new Thread(() -> {
            List<ClienteEntity> clientesCargados = db.clienteDao().getAll();

            if (clientesCargados.isEmpty()) {
                runOnUiThread(() ->
                    Toast.makeText(this, "No hay clientes registrados. Registre un cliente primero.", Toast.LENGTH_LONG).show()
                );
                return;
            }

            runOnUiThread(() -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View view = LayoutInflater.from(this).inflate(R.layout.dialog_nuevo_servicio, null);
                builder.setView(view);
                builder.setTitle("Nuevo Servicio");
                builder.setPositiveButton("Guardar", null);
                builder.setNegativeButton("Cancelar", null);
                AlertDialog dialog = builder.create();
                dialog.show();

                Spinner spCliente = view.findViewById(R.id.spinner_cliente);
                Spinner spTipo = view.findViewById(R.id.spinner_tipo_servicio);
                Spinner spEstado = view.findViewById(R.id.spinner_estado);
                EditText etPrecio = view.findViewById(R.id.et_precio);
                EditText etDescripcion = view.findViewById(R.id.et_descripcion);

                String[] nombresClientes = new String[clientesCargados.size()];
                for (int i = 0; i < clientesCargados.size(); i++) {
                    nombresClientes[i] = clientesCargados.get(i).getNombre();
                }


                ArrayAdapter<String> adapterClientes = new ArrayAdapter<>(this, R.layout.item_spinner, nombresClientes);
                adapterClientes.setDropDownViewResource(R.layout.item_spinner);
                spCliente.setAdapter(adapterClientes);

                ArrayAdapter<String> adapterTipos = new ArrayAdapter<>(this, R.layout.item_spinner, Constantes.TIPOS_SERVICIO);
                adapterTipos.setDropDownViewResource(R.layout.item_spinner);
                spTipo.setAdapter(adapterTipos);

                ArrayAdapter<String> adapterEstados = new ArrayAdapter<>(this, R.layout.item_spinner, Constantes.ESTADOS);
                adapterEstados.setDropDownViewResource(R.layout.item_spinner);
                spEstado.setAdapter(adapterEstados);

                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                    String precioStr = etPrecio.getText().toString().trim();
                    if (precioStr.isEmpty()) {
                        etPrecio.setError("Ingrese un precio");
                        return;
                    }

                    int selectedClienteIndex = spCliente.getSelectedItemPosition();
                    int clienteId = clientesCargados.get(selectedClienteIndex).getId();
                    String tipo = spTipo.getSelectedItem().toString();
                    String estado = spEstado.getSelectedItem().toString();
                    double precio = Double.parseDouble(precioStr);
                    String descripcion = etDescripcion.getText().toString().trim();
                    String fecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

                    ServicioEntity servicio = new ServicioEntity(clienteId, tipo, estado, precio, descripcion, fecha);

                    new Thread(() -> {
                        db.servicioDao().insert(servicio);
                        runOnUiThread(() -> {
                            cargarServicios();
                            dialog.dismiss();
                            Toast.makeText(this, "Servicio registrado", Toast.LENGTH_SHORT).show();
                        });
                    }).start();
                });
            });
        }).start();
    }

    private void eliminarServicio(ServicioEntity servicio) {
        if (servicio.getEstado().equals("En proceso")) {
            Toast.makeText(this, "No se puede eliminar un servicio en proceso", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(() -> {
            db.servicioDao().delete(servicio);
            runOnUiThread(() -> {
                cargarServicios();
                Toast.makeText(this, "Servicio eliminado", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}