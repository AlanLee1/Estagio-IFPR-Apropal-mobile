package br.com.apropal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.apropal.R;
import br.com.apropal.model.Insumo;

public class AdaptadorInsumo extends RecyclerView.Adapter<AdaptadorInsumo.MyViewHolder> {

    private List<Insumo> listaInsumos;

    public AdaptadorInsumo(List<Insumo> insumos) {
        this.listaInsumos = insumos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista_insumos, parent, false);

        return new AdaptadorInsumo.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Insumo i = listaInsumos.get(position);

        holder.descricao.setText(i.getDescricao());
        holder.quantidade.setText("Qtd:" + String.valueOf(i.getQuantidade()));
    }

    @Override
    public int getItemCount() {
        return listaInsumos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView descricao, quantidade;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            descricao=itemView.findViewById(R.id.txtListaDescricao);
            quantidade=itemView.findViewById(R.id.txtListaQuantidade);
        }
    }
}
