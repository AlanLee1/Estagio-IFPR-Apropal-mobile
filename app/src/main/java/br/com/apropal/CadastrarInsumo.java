package br.com.apropal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import br.com.apropal.model.Insumo;
import br.com.apropal.model.Tecnico;

public class CadastrarInsumo extends AppCompatActivity {

    private Button btnInsumoCadastrar, btnInsumoDeletar;
    private EditText edtDescricao, edtQuantidade;
    private DatabaseReference reference;
    private Insumo i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_insumo);

        inicializarComponentes();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Cadastro de Insumo");
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.verde)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String estado =  getIntent().getStringExtra("estado");


        if(estado.equals("editar")){
            Insumo i =(Insumo) getIntent().getSerializableExtra("id");

            edtDescricao.setText(String.valueOf(i.getDescricao()));
            edtQuantidade.setText(String.valueOf(i.getQuantidade()));

        }else{
            edtDescricao.setText("");
            edtQuantidade.setText("");
        }

        btnInsumoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(estado.equals("novo")) {
                    salvarCadastro();
                }else{
                    try {
                        Insumo insumo =(Insumo) getIntent().getSerializableExtra("id");
                        String id = String.valueOf(insumo.getId());
                        atualizarCadastro(id);
                    }catch (Exception e ) {
                        e.printStackTrace();
                        Toast.makeText (getApplicationContext (), "Erro ao Atualizar", Toast . LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnInsumoDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Insumo insumo =(Insumo) getIntent().getSerializableExtra("id");
                    String id = String.valueOf(insumo.getId());
                    deletarCadastro(id);
                }catch (Exception e ) {
                    e.printStackTrace();
                    Toast.makeText (getApplicationContext (), "Erro ao Deletar", Toast . LENGTH_SHORT).show();
                }
            }
        });
    }


    void cadastroInsumo(final Insumo insumo){

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("insumos").child(insumo.getId()).setValue(insumo);
        Toast.makeText(CadastrarInsumo.this,"Cadastro com sucesso" , Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),ListaInsumo.class));
        finish();
    }

    void salvarCadastro(){
        String descricao =edtDescricao.getText().toString();
        String quantidade = edtQuantidade.getText().toString();

        if(!descricao.isEmpty()){
            if(!quantidade.isEmpty()){

                i = new Insumo();
                i.setId(UUID.randomUUID().toString());
                i.setDescricao(descricao);
                i.setQuantidade(Integer.parseInt(quantidade));
                cadastroInsumo(i);
            }else{
                Toast.makeText(CadastrarInsumo.this, "Preencha o campo quantidade!",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(CadastrarInsumo.this, "Preencha o campo descrição!",Toast.LENGTH_SHORT).show();
        }
    }

    void atualizarCadastro(String id){

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("insumos").child(id);
        HashMap <String, Object> updates = new HashMap<String,Object>();
        
        String descricao = edtDescricao.getText().toString();
        int quantidade =  Integer.parseInt(edtQuantidade.getText().toString());

        updates.put("descricao",descricao);
        updates.put("quantidade", quantidade);

        reference.child("insumos").child(id).updateChildren(updates);;
        finish();
    }

    void deletarCadastro(String id){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("insumos").child(id).removeValue();
        finish();
    }

    void inicializarComponentes(){
        btnInsumoCadastrar = findViewById(R.id.btnInsumoSalvar);
        btnInsumoDeletar = findViewById(R.id.btnInsumoDeletar);
        edtDescricao = findViewById(R.id.edtCadastroDescricao);
        edtQuantidade = findViewById(R.id.edtCadastroQuantidade);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), ListaInsumo.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

}