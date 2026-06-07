package sv.edu.ues.vl23003.crudapp.ui.clientes;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageButton;

import android.app.AlertDialog;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
    Button btnEditar;
    Button btnEliminar;

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
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);

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

            btnEditar.setOnClickListener(v -> mostrarDialogoEditar(clienteEntity));

            txtDireccion.setText(clienteEntity.direccion);

            txtMunicipio.setText(clienteEntity.municipio);

            txtNotas.setText(clienteEntity.notas);

            btnEliminar.setOnClickListener(v -> {



                new AlertDialog.Builder(this)
                        .setTitle("Eliminar Cliente")
                        .setMessage("¿Desea eliminar este cliente?")
                        .setPositiveButton("Eliminar",(dialog,which)->{

                            AppDatabase.getDatabase(this)
                                    .clienteDao()
                                    .eliminar(clienteEntity);
                            Toast.makeText(
                                    this,
                                    "Cliente eliminado correctamente",
                                    Toast.LENGTH_SHORT
                            ).show();

                            finish();

                        })
                        .setNegativeButton("Cancelar",null)
                        .show();
            });
        }
    }
    private void mostrarDialogoEditar(ClienteEntity cliente){

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        View view =
                getLayoutInflater()
                        .inflate(R.layout.dialog_cliente,null);

        builder.setView(view);

        EditText edtNombre =
                view.findViewById(R.id.edtNombre);

        EditText edtTelefono =
                view.findViewById(R.id.edtTelefono);

        EditText edtCorreo =
                view.findViewById(R.id.edtCorreo);

        EditText edtDireccion =
                view.findViewById(R.id.edtDireccion);

        EditText edtMunicipio =
                view.findViewById(R.id.edtMunicipio);

        EditText edtNotas =
                view.findViewById(R.id.edtNotas);

        edtNombre.setText(cliente.nombre);
        edtTelefono.setText(cliente.telefono);
        edtCorreo.setText(cliente.email);
        edtDireccion.setText(cliente.direccion);
        edtMunicipio.setText(cliente.municipio);
        edtNotas.setText(cliente.notas);

        AlertDialog dialog = builder.create();

        view.findViewById(R.id.btnGuardar)
                .setOnClickListener(v -> {

                    cliente.nombre =
                            edtNombre.getText().toString();

                    cliente.telefono =
                            edtTelefono.getText().toString();

                    cliente.email =
                            edtCorreo.getText().toString();

                    cliente.direccion =
                            edtDireccion.getText().toString();

                    cliente.municipio =
                            edtMunicipio.getText().toString();

                    cliente.notas =
                            edtNotas.getText().toString();

                    AppDatabase.getDatabase(this)
                            .clienteDao()
                            .actualizar(cliente);

                    recreate();

                    dialog.dismiss();
                });

        dialog.show();
    }
}