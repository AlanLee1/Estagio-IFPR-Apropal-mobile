package br.com.apropal;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.UUID;

import br.com.apropal.model.Agricultor;
import br.com.apropal.model.Insumo;
import br.com.apropal.model.Tecnico;

public class CadastrarAgricultor extends AppCompatActivity {

    private Button btnAgricultorSalvar, btnAgricultorDeletar;
    private EditText edtAgricultorNome, edtAgricultorCpf,edtAgricultorEmail,edtAgricultorSenha,edtAgricultorCadpro,edtAgricultorTelefone;
    private DatabaseReference reference;
    private Agricultor a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_agricultor);

        inicializarComponentes();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Cadastro de Agricultor");
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.amarelo)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String estado =  getIntent().getStringExtra("estado");

        if(estado.equals("editar")){
            Agricultor agricultor =(Agricultor) getIntent().getSerializableExtra("id");

            edtAgricultorNome.setText(String.valueOf(agricultor.getNome()));
            edtAgricultorCpf.setText(String.valueOf(agricultor.getCpf()));
            edtAgricultorEmail.setText(String.valueOf(agricultor.getEmail()));
            edtAgricultorSenha.setText(String.valueOf(agricultor.getSenha()));
            edtAgricultorCadpro.setText(String.valueOf(agricultor.getCadpro()));
            edtAgricultorTelefone.setText(String.valueOf(agricultor.getTelefone()));
        }else{
            edtAgricultorNome.setText("");
            edtAgricultorCpf.setText("");
            edtAgricultorEmail.setText("");
            edtAgricultorSenha.setText("");
            edtAgricultorCadpro.setText("");
            edtAgricultorTelefone.setText("");
        }

        btnAgricultorSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(estado.equals("novo")){
                    salvarCadastro();
                }else{
                    try {
                        Agricultor agricultor =(Agricultor) getIntent().getSerializableExtra("id");
                        String id = String.valueOf(agricultor.getId());
                        atualizarCadastro(id);
                    }catch (Exception e ) {
                        e.printStackTrace();
                        Toast.makeText (getApplicationContext (), "Erro ao Atualizar", Toast . LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnAgricultorDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Agricultor agricultor =(Agricultor) getIntent().getSerializableExtra("id");
                    String id = String.valueOf(agricultor.getId());
                    deletarCadastro(id);
                }catch (Exception e ) {
                    e.printStackTrace();
                    Toast.makeText (getApplicationContext (), "Erro ao deletar", Toast . LENGTH_SHORT).show();
                }
            }
        });
    }


    void cadastroAgricultor(final Agricultor agricultor){

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("agricultores").child(agricultor.getId()).setValue(agricultor);
        Toast.makeText(CadastrarAgricultor.this,"Cadastro com sucesso" , Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),ListaAgricultor.class));
        finish();
    }

    void salvarCadastro(){
        String nome = edtAgricultorNome.getText().toString();
        String cpf = edtAgricultorCpf.getText().toString();
        String email = edtAgricultorEmail.getText().toString();
        String senha = edtAgricultorSenha.getText().toString();
        String cadpro = edtAgricultorCadpro.getText().toString();
        String telefone = edtAgricultorTelefone.getText().toString();

        if(!nome.isEmpty()){
            if(!cpf.isEmpty()){
                if (!cadpro.isEmpty()){
                    if (!telefone.isEmpty()){
                        if (!email.isEmpty()){
                            if (!senha.isEmpty()){
                                a = new Agricultor();
                                a.setId(UUID.randomUUID().toString());
                                a.setNome(nome);
                                a.setCpf(cpf);
                                a.setSenha(cadpro);
                                a.setSenha(telefone);
                                a.setEmail(email);
                                a.setSenha(senha);

                                cadastroAgricultor(a);
                            }else{
                                Toast.makeText(CadastrarAgricultor.this, "Preencha o campo senha!",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(CadastrarAgricultor.this, "Preencha o campo email!",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(CadastrarAgricultor.this, "Preencha o campo telefone!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CadastrarAgricultor.this, "Preencha o campo cadpro!",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(CadastrarAgricultor.this, "Preencha o campo cpf!",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(CadastrarAgricultor.this, "Preencha o campo nome!",Toast.LENGTH_SHORT).show();
        }
    }

    void atualizarCadastro(String id){

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("agricultores").child(id);
        HashMap<String, Object> updates = new HashMap<String,Object>();

        String nome = edtAgricultorNome.getText().toString();
        String cpf = edtAgricultorCpf.getText().toString();
        String email = edtAgricultorEmail.getText().toString();
        String senha = edtAgricultorSenha.getText().toString();
        String cadpro = edtAgricultorCadpro.getText().toString();
        String telefone = edtAgricultorTelefone.getText().toString();


        updates.put("nome",nome);
        updates.put("cpf", cpf);
        updates.put("cadpro",email);
        updates.put("telefone", senha);
        updates.put("email",email);
        updates.put("senha", senha);

        reference.child("agricultores").child(id).updateChildren(updates);;
        finish();
    }

    void deletarCadastro(String id){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("agricultores").child(id).removeValue();
        finish();
    }

    void inicializarComponentes(){
        btnAgricultorSalvar = findViewById(R.id.btnAgriSalvar);
        btnAgricultorDeletar = findViewById(R.id.btnAgriDeletar);
        edtAgricultorNome = findViewById(R.id.edtAgritNome);
        edtAgricultorCpf = findViewById(R.id.edtAgriCPF);
        edtAgricultorEmail = findViewById(R.id.edtAgriEmail);
        edtAgricultorSenha = findViewById(R.id.edtAgriSenha);
        edtAgricultorCadpro = findViewById(R.id.edtAgriCadpro);
        edtAgricultorTelefone = findViewById(R.id.edtAgriTelefone);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), ListaAgricultor.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

}