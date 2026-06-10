package sv.edu.ues.vl23003.crudapp.ui.servicios;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sv.edu.ues.vl23003.crudapp.R;
import sv.edu.ues.vl23003.crudapp.data.local.entity.ClienteEntity;
import sv.edu.ues.vl23003.crudapp.data.local.entity.ServicioEntity;

public class ServicioAdapter extends RecyclerView.Adapter<ServicioAdapter.ViewHolder> {

    private List<ServicioEntity> servicios;
    private List<ClienteEntity> clientes;
    private OnDeleteClickListener onDeleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(ServicioEntity servicio);
    }

    public ServicioAdapter(List<ServicioEntity> servicios, List<ClienteEntity> clientes, OnDeleteClickListener listener) {
        this.servicios = servicios;
        this.clientes = clientes;
        this.onDeleteClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servicio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServicioEntity servicio = servicios.get(position);

        String nombreCliente = "";
        for (ClienteEntity c : clientes) {
            if (c.getId() == servicio.getClienteId()) {
                nombreCliente = c.getNombre();
                break;
            }
        }

        holder.tvCliente.setText("Cliente: " + nombreCliente);
        holder.tvTipo.setText("Tipo: " + servicio.getTipoServicio());
        holder.tvEstado.setText("Estado: " + servicio.getEstado());
        holder.tvPrecio.setText("Precio: $" + String.format("%.2f", servicio.getPrecio()));
        holder.tvDescripcion.setText(servicio.getDescripcion());
        holder.tvFecha.setText("Fecha: " + servicio.getFecha());

        holder.btnEliminar.setOnClickListener(v -> onDeleteClickListener.onDeleteClick(servicio));
    }

    @Override
    public int getItemCount() {
        return servicios.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCliente, tvTipo, tvEstado, tvPrecio, tvDescripcion, tvFecha;
        Button btnEliminar;

        ViewHolder(View itemView) {
            super(itemView);
            tvCliente = itemView.findViewById(R.id.tv_cliente);
            tvTipo = itemView.findViewById(R.id.tv_tipo_servicio);
            tvEstado = itemView.findViewById(R.id.tv_estado);
            tvPrecio = itemView.findViewById(R.id.tv_precio);
            tvDescripcion = itemView.findViewById(R.id.tv_descripcion);
            tvFecha = itemView.findViewById(R.id.tv_fecha);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar);
        }
    }
}
