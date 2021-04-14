package br.com.apropal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.apropal.R;
import br.com.apropal.model.Agricultor;

public class AdaptadorAgricultor extends RecyclerView.Adapter<AdaptadorAgricultor.MyViewHolder> {

    private List<Agricultor> listaAgricultor;
    private MyViewHolder holder;
    private int position;

    public AdaptadorAgricultor(List<Agricultor> agricultores){
        this.listaAgricultor = agricultores;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista_agricultores, parent, false);

        return new AdaptadorAgricultor.MyViewHolder(itemLista);

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Agricultor a = listaAgricultor.get(position);

        holder.nome.setText(a.getNome());
        holder.cpf.setText("CPF: " + a.getCpf());
        holder.email.setText("Email: "+a.getEmail());
    }

    @Override
    public int getItemCount() {
        return listaAgricultor.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nome, cpf, email;
        public  MyViewHolder(@NonNull View itemView){
            super(itemView);

            nome = itemView.findViewById(R.id.txtListaAgricultorNome);
            cpf = itemView.findViewById(R.id.txtListaAgricultorCpf);
            email = itemView.findViewById(R.id.txtListaAgricultorEmail);
        }
    }
}
