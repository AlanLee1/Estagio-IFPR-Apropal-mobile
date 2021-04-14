package br.com.apropal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.apropal.R;
import br.com.apropal.model.Tecnico;

public class AdapterTecnico extends RecyclerView.Adapter<AdapterTecnico.MyViewHolder> {

    private List<Tecnico> listaTecnicos;
    private MyViewHolder holder;
    private int position;

    public AdapterTecnico(List<Tecnico> tecnicos){
        this.listaTecnicos = tecnicos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista_tecnicos, parent, false);

        return new AdapterTecnico.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Tecnico t = listaTecnicos.get(position);

        holder.nome.setText(t.getNome());
        holder.cpf.setText("CPF: " + t.getCpf());
        holder.email.setText("Email: "+t.getEmail());
    }

    @Override
    public int getItemCount() {
        return listaTecnicos.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView nome, cpf, email;
        public  MyViewHolder(@NonNull View itemView){
            super(itemView);

            nome = itemView.findViewById(R.id.txtListaTecnicoNome);
            cpf = itemView.findViewById(R.id.txtListaTecnicoCpf);
            email = itemView.findViewById(R.id.txtListaTecnicoEmail);
        }
    }
}
