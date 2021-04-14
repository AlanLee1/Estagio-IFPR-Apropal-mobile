package br.com.apropal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.apropal.adapter.AdaptadorAgricultor;;
import br.com.apropal.helper.RecyclerItemClickListener;
import br.com.apropal.model.Agricultor;

public class ListaAgricultor extends AppCompatActivity {

    private RecyclerView recyclerViewListaAgricultores;
    private AdaptadorAgricultor adapter;
    private List<Agricultor> listaAgricultores = new ArrayList<>();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private SearchView searchView;
    private Agricultor agricultor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_agricultor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Agricultores");

        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.amarelo)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerViewListaAgricultores = findViewById(R.id.recyclerAgricultores);
        searchView = findViewById(R.id.searchviewPrincipal);
        eventoClick();
    }
    void carreagarLista(final RecyclerView lista){
        listaAgricultores.clear();
        final DatabaseReference agricultorRef = referencia.child("agricultores");
        agricultorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dados : snapshot.getChildren()) {
                    agricultor = dados.getValue(Agricultor.class);

                    listaAgricultores.add(agricultor);
                }
                AdaptadorAgricultor adapter = new AdaptadorAgricultor(listaAgricultores);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                lista.setLayoutManager(layoutManager);
                lista.setHasFixedSize(true);
                lista.addItemDecoration(new DividerItemDecoration(ListaAgricultor.this,
                        LinearLayout.VERTICAL));

                lista.setAdapter(adapter);
                Log.d("TESTANDO", String.valueOf(listaAgricultores));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    void pesquisarAgricultor(String texto){
        List<Agricultor> listaAgricultorBusca = new ArrayList<>();

        for (Agricultor agricultor : listaAgricultores){
            String nome = agricultor.getNome();

            if(nome.contains(texto)){
                listaAgricultorBusca.add(agricultor);
            }
        }
        adapter = new AdaptadorAgricultor(listaAgricultorBusca);
        recyclerViewListaAgricultores.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    void eventoClick(){
        recyclerViewListaAgricultores.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerViewListaAgricultores,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(ListaAgricultor.this,CadastrarAgricultor.class);
                        intent.putExtra("id",listaAgricultores.get(position));
                        intent.putExtra("estado", "editar");

                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        ));
    }
    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        MenuItem item = menu.findItem(R.id.menuPesquisa);
        SearchView searchView= (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                pesquisarAgricultor(newText);

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuAdd:
                Intent intent  = new Intent(ListaAgricultor.this, CadastrarAgricultor.class);
                intent.putExtra("estado", "novo");
                startActivity(intent);
                break;
            case android.R.id.home:
                Intent intent1  = new Intent(ListaAgricultor.this, MainActivity.class);
                startActivity(intent1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListaAgricultor.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        carreagarLista(recyclerViewListaAgricultores);
        super.onResume();
    }
}