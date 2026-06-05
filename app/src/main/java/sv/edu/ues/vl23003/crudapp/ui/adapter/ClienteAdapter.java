package sv.edu.ues.vl23003.crudapp.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import sv.edu.ues.vl23003.crudapp.R;
import sv.edu.ues.vl23003.crudapp.data.local.entity.ClienteEntity;
import sv.edu.ues.vl23003.crudapp.ui.clientes.ClienteDetalleActivity;
import sv.edu.ues.vl23003.crudapp.ui.clientes.ClientesActivity;
import sv.edu.ues.vl23003.crudapp.data.local.entity.database.AppDatabase;

import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolder>{

    List<ClienteEntity> lista;
    Context context;

    public ClienteAdapter(Context context, List<ClienteEntity> lista){
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cliente,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ClienteEntity clienteEntity = lista.get(position);

        holder.txtNombre.setText(clienteEntity.nombre);
        holder.txtTelefono.setText(clienteEntity.telefono);
        holder.txtCorreo.setText(clienteEntity.email);

        holder.itemView.setOnClickListener(v -> {

            Intent intent =
                    new Intent(context, ClienteDetalleActivity.class);

            intent.putExtra("idCliente", clienteEntity.id);

            context.startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(v -> {

            PopupMenu menu = new PopupMenu(context,v);

            menu.getMenu().add("Editar");
            menu.getMenu().add("Eliminar");

            menu.setOnMenuItemClickListener(item -> {

                if(item.getTitle().equals("Editar")){
                    editarCliente(clienteEntity);
                }

                if(item.getTitle().equals("Eliminar")){

                    // Antes de eliminar, verificar si el cliente tiene servicios asociados
                    int servicios = AppDatabase.getDatabase(context)
                            .servicioDao()
                            .contarPorCliente(clienteEntity.id);

                    if(servicios > 0){
                        new AlertDialog.Builder(context)
                                .setTitle("Eliminar Cliente")
                                .setMessage("No se puede eliminar el cliente porque tiene servicios asociados.")
                                .setPositiveButton("Aceptar", null)
                                .show();
                    }else{
                        new AlertDialog.Builder(context)
                                .setTitle("Eliminar Cliente")
                                .setMessage("¿Desea eliminar este cliente?")
                                .setPositiveButton("Sí",(dialog,which)->{

                                    AppDatabase.getDatabase(context)
                                            .clienteDao()
                                            .eliminar(clienteEntity);

                                    Toast.makeText(
                                            context,
                                            "Cliente eliminado correctamente",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    if(context instanceof ClientesActivity){
                                        ((ClientesActivity) context).cargarClientes();
                                    }

                                })
                                .setNegativeButton("Cancelar",null)
                                .show();
                    }
                }

                return true;
            });

            menu.show();

            return true;
        });
    }

    private void editarCliente(ClienteEntity clienteEntity){

        AlertDialog.Builder builder =
                new AlertDialog.Builder(context);

        View view = LayoutInflater.from(context)
                .inflate(R.layout.dialog_cliente,null);

        builder.setView(view);

        EditText edtNombre = view.findViewById(R.id.edtNombre);
        EditText edtTelefono = view.findViewById(R.id.edtTelefono);
        EditText edtCorreo = view.findViewById(R.id.edtCorreo);
        EditText edtDireccion = view.findViewById(R.id.edtDireccion);
        EditText edtMunicipio = view.findViewById(R.id.edtMunicipio);
        EditText edtNotas = view.findViewById(R.id.edtNotas);

        edtNombre.setText(clienteEntity.nombre);
        edtTelefono.setText(clienteEntity.telefono);
        edtCorreo.setText(clienteEntity.email);
        edtDireccion.setText(clienteEntity.direccion);
        edtMunicipio.setText(clienteEntity.municipio);
        edtNotas.setText(clienteEntity.notas);

        AlertDialog dialog = builder.create();

        view.findViewById(R.id.btnGuardar)
                .setOnClickListener(v -> {

                    String nombre = edtNombre.getText().toString().trim();
                    String telefono = edtTelefono.getText().toString().trim();
                    String correo = edtCorreo.getText().toString().trim();

                    if(nombre.isEmpty()){
                        edtNombre.setError("Ingrese nombre");
                        return;
                    }

                    if(!telefono.matches("[0-9]{8}")){
                        edtTelefono.setError("Teléfono inválido");
                        return;
                    }

                    if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                        edtCorreo.setError("Correo inválido");
                        return;
                    }

                    clienteEntity.nombre = nombre;
                    clienteEntity.telefono = telefono;
                    clienteEntity.email = correo;
                    clienteEntity.direccion = edtDireccion.getText().toString().trim();
                    clienteEntity.municipio = edtMunicipio.getText().toString().trim();
                    clienteEntity.notas = edtNotas.getText().toString().trim();

                    AppDatabase.getDatabase(context)
                            .clienteDao()
                            .actualizar(clienteEntity);

                    notifyDataSetChanged();

                    dialog.dismiss();
                });

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtNombre, txtTelefono, txtCorreo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
            txtCorreo = itemView.findViewById(R.id.txtCorreo);
        }
    }
}