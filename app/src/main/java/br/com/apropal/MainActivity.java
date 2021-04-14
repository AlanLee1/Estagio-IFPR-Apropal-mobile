package br.com.apropal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import br.com.apropal.config.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {

    CardView cardTec, cardAgr, cardInsumo,cardFazenda, cardPresuncao, cardSair;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();
        auth = ConfiguracaoFirebase.getFirebaseAutenticacao();

        cardTec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listaTec =new Intent(MainActivity.this,ListaTec.class);
                startActivity(listaTec);
            }
        });
        cardAgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listaTec =new Intent(MainActivity.this,ListaAgricultor.class);
                startActivity(listaTec);
            }
        });
        cardInsumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listaTec =new Intent(MainActivity.this,ListaInsumo.class);
                startActivity(listaTec);
            }
        });
        cardFazenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listaTec =new Intent(MainActivity.this,ListaFazenda.class);
                startActivity(listaTec);
            }
        });
        cardPresuncao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listaTec =new Intent(MainActivity.this,ListaPresuncao.class);
                startActivity(listaTec);
            }
        });
        cardSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deslogarUsuario();
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }

    private void deslogarUsuario(){
        try {
            auth.signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void inicializarComponentes() {
        cardTec = findViewById(R.id.cardTec);
        cardAgr = findViewById(R.id.cardAgri);
        cardInsumo = findViewById(R.id.cardInsumo);
        cardFazenda = findViewById(R.id.cardFazenda);
        cardPresuncao = findViewById(R.id.cardPresuncao);
        cardSair = findViewById(R.id.cardSair);
    }
}