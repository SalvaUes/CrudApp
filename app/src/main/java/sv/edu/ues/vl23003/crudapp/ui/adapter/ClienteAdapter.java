package sv.edu.ues.vl23003.crudapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import sv.edu.ues.vl23003.crudapp.R;
import sv.edu.ues.vl23003.crudapp.data.local.entity.ClienteEntity;
import sv.edu.ues.vl23003.crudapp.ui.clientes.ClienteDetalleActivity;

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

        ClienteEntity cliente = lista.get(position);

        holder.txtNombre.setText(cliente.nombre);
        holder.txtTelefono.setText(cliente.telefono);
        holder.txtCorreo.setText(cliente.email);

        holder.itemView.setOnClickListener(v -> {

            Intent intent =
                    new Intent(context,
                            ClienteDetalleActivity.class);

            intent.putExtra(
                    "idCliente",
                    cliente.id);

            context.startActivity(intent);
        });
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

    public void actualizarLista(List<ClienteEntity> nuevaLista) {
        this.lista = nuevaLista;
        notifyDataSetChanged();
    }
}