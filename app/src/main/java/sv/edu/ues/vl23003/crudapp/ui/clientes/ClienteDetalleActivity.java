package sv.edu.ues.vl23003.crudapp.ui.clientes;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import sv.edu.ues.vl23003.crudapp.R;
import sv.edu.ues.vl23003.crudapp.data.local.entity.ClienteEntity;
import sv.edu.ues.vl23003.crudapp.data.local.entity.database.AppDatabase;

public class ClienteDetalleActivity extends AppCompatActivity {

    TextView txtNombre;
    TextView txtTelefono;
    TextView txtCorreo;
    TextView txtDireccion;
    TextView txtMunicipio;
    TextView txtNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_detalle);

        ImageButton btnVolver = findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(v -> finish());

        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtMunicipio = findViewById(R.id.txtMunicipio);
        txtNotas = findViewById(R.id.txtNotas);

        int id =
                getIntent().getIntExtra(
                        "idCliente",
                        0);

        ClienteEntity clienteEntity =
                AppDatabase.getDatabase(this)
                        .clienteDao()
                        .obtenerPorId(id);

        if (clienteEntity != null) {

            txtNombre.setText(clienteEntity.nombre);

            txtTelefono.setText(clienteEntity.telefono);

            txtCorreo.setText(clienteEntity.email);

            txtDireccion.setText(clienteEntity.direccion);

            txtMunicipio.setText(clienteEntity.municipio);

            txtNotas.setText(clienteEntity.notas);
        }
    }
}