package sv.edu.ues.vl23003.crudapp.ui.clientes;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import sv.edu.ues.vl23003.crudapp.R;
import sv.edu.ues.vl23003.crudapp.data.local.entity.ClienteEntity;
import sv.edu.ues.vl23003.crudapp.ui.adapter.ClienteAdapter;
import sv.edu.ues.vl23003.crudapp.data.local.entity.database.AppDatabase;
import sv.edu.ues.vl23003.crudapp.ui.dashboard.DashboardActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ClientesActivity extends AppCompatActivity {

    private RecyclerView recyclerClientes;
    private ImageButton btnVolver;
    private ImageButton btnAgregar;

    private TextView txtSinClientes;
    private AppDatabase db;
    private EditText edtBuscar;

    private void buscarClientes(String texto){

        List<ClienteEntity> lista =
                db.clienteDao().buscarCliente(texto);

        ClienteAdapter adapter =
                new ClienteAdapter(this, lista);

        recyclerClientes.setAdapter(adapter);

        if(lista.isEmpty()){
            txtSinClientes.setText(
                    "No se encontraron clientes.");
            txtSinClientes.setVisibility(View.VISIBLE);
        }else{
            txtSinClientes.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        recyclerClientes = findViewById(R.id.recyclerClientes);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnVolver = findViewById(R.id.btnVolver);
        edtBuscar = findViewById(R.id.edtBuscar);
        txtSinClientes = findViewById(R.id.txtSinClientes);

        db = AppDatabase.getDatabase(this);

        BottomNavigationView bottomNavigation =
                findViewById(R.id.bottomNavigation);

        bottomNavigation.setSelectedItemId(R.id.nav_clientes);

        bottomNavigation.setOnItemSelectedListener(item -> {

            if(item.getItemId() == R.id.nav_clientes){
                return true;
            }

            if(item.getItemId() == R.id.nav_home){

                startActivity(
                        new Intent(this, DashboardActivity.class));

                finish();

                return true;
            }

            return false;
        });

        recyclerClientes.setLayoutManager(
                new LinearLayoutManager(this));

        cargarClientes();

        edtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buscarClientes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnVolver.setOnClickListener(v -> {

            Intent intent =
                    new Intent(ClientesActivity.this,
                            DashboardActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);

            finish();
        });

        btnAgregar.setOnClickListener(v -> mostrarDialogo());
    }

    public void cargarClientes() {

        List<ClienteEntity> lista =
                db.clienteDao().obtenerClientes();

        ClienteAdapter adapter =
                new ClienteAdapter(this, lista);

        recyclerClientes.setAdapter(adapter);

        if(lista.isEmpty()){
            txtSinClientes.setVisibility(View.VISIBLE);
            recyclerClientes.setVisibility(View.GONE);
        }else{
            txtSinClientes.setVisibility(View.GONE);
            recyclerClientes.setVisibility(View.VISIBLE);
        }
    }

    private void mostrarDialogo() {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        View view =
                getLayoutInflater().inflate(
                        R.layout.dialog_cliente,
                        null);

        builder.setView(view);

        EditText edtNombre = view.findViewById(R.id.edtNombre);
        EditText edtTelefono = view.findViewById(R.id.edtTelefono);
        EditText edtCorreo = view.findViewById(R.id.edtCorreo);
        EditText edtDireccion = view.findViewById(R.id.edtDireccion);
        EditText edtMunicipio = view.findViewById(R.id.edtMunicipio);
        EditText edtNotas = view.findViewById(R.id.edtNotas);

        AlertDialog dialog = builder.create();

        view.findViewById(R.id.btnGuardar)
                .setOnClickListener(v -> {

                    String nombre = edtNombre.getText().toString().trim();
                    String telefono = edtTelefono.getText().toString().trim();
                    String correo = edtCorreo.getText().toString().trim();
                    String direccion = edtDireccion.getText().toString().trim();
                    String municipio = edtMunicipio.getText().toString().trim();

                    if(nombre.isEmpty()){
                        edtNombre.setError("Ingrese el nombre");
                        return;
                    }

                    if(telefono.isEmpty()){
                        edtTelefono.setError("Ingrese teléfono");
                        return;
                    }

                    if(!telefono.matches("[0-9]{8}")){
                        edtTelefono.setError("Debe contener 8 dígitos");
                        return;
                    }

                    if(correo.isEmpty()){
                        edtCorreo.setError("Ingrese correo");
                        return;
                    }

                    if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                        edtCorreo.setError("Correo inválido");
                        return;
                    }

                    if(direccion.isEmpty()){
                        edtDireccion.setError("Ingrese dirección");
                        return;
                    }

                    if(municipio.isEmpty()){
                        edtMunicipio.setError("Ingrese municipio");
                        return;
                    }

                    ClienteEntity clienteEntity = new ClienteEntity();

                    clienteEntity.nombre = nombre;
                    clienteEntity.telefono = telefono;
                    clienteEntity.email = correo;
                    clienteEntity.direccion = direccion;
                    clienteEntity.municipio = municipio;
                    clienteEntity.notas = edtNotas.getText().toString().trim();

                    db.clienteDao().insertar(clienteEntity);

                    Toast.makeText(
                            this,
                            "Cliente agregado correctamente",
                            Toast.LENGTH_SHORT
                    ).show();

                    cargarClientes();

                    dialog.dismiss();
                });

        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarClientes();
    }
}