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
import br.com.apropal.model.Tecnico;

public class CadastrarTecnico extends AppCompatActivity {

    private Button btnSalvar, btnDeletar;
    private EditText edtNome, edtCpf,edtEmail,edtSenha, edtCrea, edtTelefone;
    private DatabaseReference reference;
    private Tecnico t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_tecnico);

        inicializarComponentes();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Cadastro de Tecnico");
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.azul)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String estado =  getIntent().getStringExtra("estado");

        if(estado.equals("editar")){
            Tecnico tecnico =(Tecnico) getIntent().getSerializableExtra("id");

            edtNome.setText(String.valueOf(tecnico.getNome()));
            edtCpf.setText(String.valueOf(tecnico.getCpf()));
            edtEmail.setText(String.valueOf(tecnico.getEmail()));
            edtSenha.setText(String.valueOf(tecnico.getSenha()));
            edtCrea.setText(String.valueOf(tecnico.getCrea()));
            edtTelefone.setText(String.valueOf(tecnico.getTelefone()));
        }else{
            edtNome.setText("");
            edtCpf.setText("");
            edtEmail.setText("");
            edtSenha.setText("");
            edtTelefone.setText("");
            edtCrea.setText("");
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(estado.equals("novo")){
                    salvarCadastro();
                }else{
                    try {
                        Tecnico tecnico =(Tecnico) getIntent().getSerializableExtra("id");
                        String id = String.valueOf(tecnico.getId());
                        atualizarCadastro(id);

                    }catch (Exception e ) {
                        e.printStackTrace();
                        Toast.makeText (getApplicationContext (), "Erro ao Atualizar", Toast . LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Tecnico tecnico =(Tecnico) getIntent().getSerializableExtra("id");
                    String id = String.valueOf(tecnico.getId());
                    deletarCadastro(id);
                }catch (Exception e ) {
                    e.printStackTrace();
                    Toast.makeText (getApplicationContext (), "Erro ao deletar", Toast . LENGTH_SHORT).show();
                }
            }
        });
    }
    void inicializarComponentes(){
        btnSalvar = findViewById(R.id.btnTecnicoSalvar);
        btnDeletar = findViewById(R.id.btnTecnicoDeletar);
        edtNome = findViewById(R.id.edtTecnicoNome);
        edtCpf = findViewById(R.id.edtTecnicoCPF);
        edtEmail = findViewById(R.id.edtTecnicoEmail);
        edtSenha = findViewById(R.id.edtTecnicoSenha);
        edtCrea = findViewById(R.id.edtTecnicoCrea);
        edtTelefone = findViewById(R.id.edtTecnicoTelefone);
    }


    void salvarCadastro(){
        String nome = edtNome.getText().toString();
        String cpf = edtCpf.getText().toString();
        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();
        String crea = edtCrea.getText().toString();
        String telefone = edtTelefone.getText().toString();

        if(!nome.isEmpty()){
            if(!cpf.isEmpty()){
                if (!crea.isEmpty()){
                    if (!telefone.isEmpty()){
                        if(!email.isEmpty()){
                            if (!senha.isEmpty()){
                                t = new Tecnico();
                                t.setId(UUID.randomUUID().toString());
                                t.setNome(nome);
                                t.setCpf(cpf);
                                t.setCrea(crea);
                                t.setTelefone(telefone);
                                t.setEmail(email);
                                t.setSenha(senha);

                                cadastroTecnico(t);
                            }else {
                                Toast.makeText(CadastrarTecnico.this, "Preencha o campo senha!", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(CadastrarTecnico.this, "Preencha o campo email!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(CadastrarTecnico.this, "Preencha o campo telefone!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CadastrarTecnico.this, "Preencha o campo crea!",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(CadastrarTecnico.this, "Preencha o campo cpf!",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(CadastrarTecnico.this, "Preencha o campo nome!",Toast.LENGTH_SHORT).show();
        }
    }

    void cadastroTecnico(final Tecnico tecnico){

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("tecnicos").child(tecnico.getId()).setValue(tecnico);
        Toast.makeText(CadastrarTecnico.this,"Cadastro com sucesso" , Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),ListaTec.class));
        finish();
    }

    void atualizarCadastro(String id){

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("tecnicos").child(id);
        HashMap<String, Object> updates = new HashMap<String,Object>();

        String nome = edtNome.getText().toString();
        String cpf = edtCpf.getText().toString();
        String crea = edtCrea.getText().toString();
        String telefone = edtTelefone.getText().toString();
        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();

        updates.put("nome",nome);
        updates.put("cpf", cpf);
        updates.put("crea", crea);
        updates.put("telefone", telefone);
        updates.put("email",email);
        updates.put("senha", senha);

        reference.child("tecnicos").child(id).updateChildren(updates);;
        Log.i("info",crea);
        finish();
    }

    void deletarCadastro(String id){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("tecnicos").child(id).removeValue();
        finish();
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), ListaTec.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}