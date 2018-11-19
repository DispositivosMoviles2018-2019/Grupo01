package ec.edu.uce.vista;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import ec.edu.uce.R;
import ec.edu.uce.modelo.Vehiculo;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolderData> {

    private List<Vehiculo> vehiculos;
    private Context context;
    private ItemClickListener itemClickListener;

    public DataAdapter(List<Vehiculo> vehiculos, Context context) {
        this.vehiculos = vehiculos;
        this.context = context;
    }

    @Override
    public ViewHolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_data, parent, false);

        return new ViewHolderData(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderData holder, int position) {
        String datePattern = "dd MMMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);

        String numberPattern = "###,##0.00";
        NumberFormat nf = new DecimalFormat(numberPattern);

        holder.placa.setText(vehiculos.get(position).getPlaca());
        holder.marca.setText(vehiculos.get(position).getMarca());
        holder.fecha.setText(sdf.format(vehiculos.get(position).getFechaFabricacion()));
        holder.costo.setText(nf.format(vehiculos.get(position).getCosto()));
        holder.matriculado.setText(vehiculos.get(position).getMatriculado() ? "Si" : "No");
        holder.color.setText(vehiculos.get(position).getColor());
    }

    @Override
    public int getItemCount() {
        return vehiculos.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolderData extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView placa;
        TextView marca;
        TextView fecha;
        TextView costo;
        TextView matriculado;
        TextView color;

        public ViewHolderData(View itemView) {
            super(itemView);

            placa = itemView.findViewById(R.id.id_placa);
            marca = itemView.findViewById(R.id.id_marca);
            fecha = itemView.findViewById(R.id.id_fecha);
            costo = itemView.findViewById(R.id.id_costo);
            matriculado = itemView.findViewById(R.id.id_matriculado);
            color = itemView.findViewById(R.id.id_color);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return true;
        }
    }
}
