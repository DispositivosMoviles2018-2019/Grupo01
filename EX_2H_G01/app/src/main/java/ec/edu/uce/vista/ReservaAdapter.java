package ec.edu.uce.vista;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ec.edu.uce.R;
import ec.edu.uce.modelo.Reserva;
import ec.edu.uce.modelo.Vehiculo;

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.VehiculoViewHolder> implements Filterable {

    private List<Reserva> reservas;
    private List<Reserva> reservasFiltradas;
    private Context context;
    private ItemClickListener itemClickListener;

    public ReservaAdapter(List<Reserva> reservas) {
        this.reservas = reservas;
        this.reservasFiltradas = reservas;
    }

    @NonNull
    @Override
    public VehiculoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_list_vehiculo, viewGroup, false);

        return new VehiculoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehiculoViewHolder holder, int i) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        NumberFormat nf = new DecimalFormat("###,##0.00");

        holder.numero.setText(String.valueOf(reservasFiltradas.get(i).getNumero()));
        holder.email.setText(reservasFiltradas.get(i).getEmail());
        holder.celular.setText(reservasFiltradas.get(i).getCelular());
        holder.fechaPrestamo.setText(df.format(reservasFiltradas.get(i).getFechaPrestamo()));
        holder.fechaEntrega.setText(df.format(reservasFiltradas.get(i).getFechaEntrega()));
        holder.valor.setText("USD " + nf.format(reservasFiltradas.get(i).getValor()));
        holder.foto.setImageBitmap(reservasFiltradas.get(i).getVehiculo().getFoto());
    }

    @Override
    public int getItemCount() {
        return reservasFiltradas.size();
    }

    public Reserva obtenerReserva(int posision) {
        return reservasFiltradas.get(posision);
    }

    public int eliminarReserva(int posision) {
        if (reservas == reservasFiltradas) {
            reservasFiltradas.remove(posision);
            notifyItemRemoved(posision);
            return posision;
        } else {
            int originalIndex = reservas.indexOf(reservasFiltradas.remove(posision));
            reservas.remove(originalIndex);
            notifyItemRemoved(posision);
            return originalIndex;
        }
    }

    public void restaurarVehiculo(Reserva reserva, int posision, int original) {
        if (reservas == reservasFiltradas) {
            reservasFiltradas.add(posision, reserva);
            notifyItemInserted(posision);
        } else {
            reservasFiltradas.add(posision, reserva);
            reservas.add(original, reserva);
            notifyItemInserted(posision);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    reservasFiltradas = reservas;
                } else {
                    List<Reserva> filteredList = new ArrayList<>();
                    for (Reserva row : reservas) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (String.valueOf(row.getNumero()).contains(charString.toLowerCase())
                                || row.getVehiculo().getPlaca().toLowerCase().contains(charString.toLowerCase())) {

                            filteredList.add(row);
                        }
                    }
                    reservasFiltradas = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = reservasFiltradas;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                reservasFiltradas = (ArrayList<Reserva>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class VehiculoViewHolder extends RecyclerView.ViewHolder {
        public TextView numero;
        public TextView email;
        public TextView celular;
        public TextView fechaPrestamo;
        public TextView fechaEntrega;
        public TextView valor;
        public CircleImageView foto;

        public VehiculoViewHolder(View itemView) {
            super(itemView);

            numero = itemView.findViewById(R.id.tvNumero);
            email = itemView.findViewById(R.id.tvEmail);
            celular = itemView.findViewById(R.id.tvCelular);
            fechaPrestamo = itemView.findViewById(R.id.tvPrestamo);
            fechaEntrega = itemView.findViewById(R.id.tvEntrega);
            valor = itemView.findViewById(R.id.tvValor);
            foto = itemView.findViewById(R.id.civFoto);
        }
    }
}
