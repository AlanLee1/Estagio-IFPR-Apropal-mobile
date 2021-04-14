package br.com.apropal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.apropal.config.ConfiguracaoFirebase;
import br.com.apropal.model.Adm;

public class Login extends AppCompatActivity {

    private EditText edtEmail, edtSenha;
    private Button btnConfirmar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificacaoUsuarioLogado();
        inicializarCampos();
        auth = ConfiguracaoFirebase.getFirebaseAutenticacao();

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String senha = edtSenha.getText().toString();

                if(!email.isEmpty()){
                    if(!senha.isEmpty()){
                        Adm adm = new Adm();
                        adm.setEmail(email);
                        adm.setSenha(senha);
                        validarLogin(adm);
                    }else{
                        Toast.makeText(Login.this, "Preencha a senha!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Login.this, "Preencha o email!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void verificacaoUsuarioLogado(){
        auth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if (auth.getCurrentUser() != null){
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    void inicializarCampos(){
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnConfirmar = findViewById(R.id.btnConfirmar);
    }

    void validarLogin(Adm adm){

        auth.signInWithEmailAndPassword(
                adm.getEmail(),
                adm.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent  intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(Login.this,"Erro ao fazer Login",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}