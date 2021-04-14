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

import br.com.apropal.adapter.AdaptadorInsumo;
import br.com.apropal.helper.RecyclerItemClickListener;
import br.com.apropal.model.Insumo;

public class ListaInsumo extends AppCompatActivity {

    private RecyclerView recyclerViewListaInsumos;
    private AdaptadorInsumo adapter;
    private List<Insumo> listaInsumos = new ArrayList<>();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private SearchView searchView;
    private Insumo insumo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_insumo);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Insumos");
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.verde)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerViewListaInsumos = findViewById(R.id.recyclerInsumos);
        searchView = findViewById(R.id.searchviewPrincipal);
        eventoClick();
    }

    void carreagarLista(final RecyclerView lista){
        listaInsumos.clear();
        final DatabaseReference insumoRef = referencia.child("insumos");
        insumoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dados : snapshot.getChildren()) {
                    insumo = dados.getValue(Insumo.class);

                    listaInsumos.add(insumo);
                }
                AdaptadorInsumo adapter = new AdaptadorInsumo(listaInsumos);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                lista.setLayoutManager(layoutManager);
                lista.setHasFixedSize(true);
                lista.addItemDecoration(new DividerItemDecoration(ListaInsumo.this,
                        LinearLayout.VERTICAL));

                lista.setAdapter(adapter);
                Log.d("TESTANDO", String.valueOf(listaInsumos));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    void pesquisarInsumos(String texto){
        List<Insumo> listaInsumoBusca = new ArrayList<>();

        for (Insumo insumo : listaInsumos){
            String nome = insumo.getDescricao();

            if(nome.contains(texto)){
                listaInsumoBusca.add(insumo);
            }
        }
        adapter = new AdaptadorInsumo(listaInsumoBusca);
        recyclerViewListaInsumos.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    void eventoClick(){
        recyclerViewListaInsumos.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerViewListaInsumos,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(ListaInsumo.this,CadastrarInsumo.class);
                        intent.putExtra("id",listaInsumos.get(position));
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

        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_main,menu);
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
                pesquisarInsumos(newText);

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuAdd:
                Intent intent  = new Intent(ListaInsumo.this, CadastrarInsumo.class);
                intent.putExtra("estado", "novo");
                startActivity(intent);
                break;
            case android.R.id.home:
                Intent intent1  = new Intent(ListaInsumo.this, MainActivity.class);
                startActivity(intent1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListaInsumo.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        carreagarLista(recyclerViewListaInsumos);
        super.onResume();
    }
}