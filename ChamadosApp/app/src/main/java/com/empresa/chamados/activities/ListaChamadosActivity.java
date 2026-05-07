package com.empresa.chamados.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.empresa.chamados.R;
import com.empresa.chamados.adapters.ChamadosAdapter;
import com.empresa.chamados.database.ChamadoDAO;
import com.empresa.chamados.models.Chamado;
import java.util.List;

public class ListaChamadosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChamadoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_chamados);

        dao = new ChamadoDAO(this);
        dao.open();

        recyclerView = findViewById(R.id.recycler_chamados);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.fab_novo_chamado).setOnClickListener(v -> {
            startActivity(new Intent(this, NovoChamadoActivity.class));
        });

        carregarLista();
    }

    private void carregarLista() {
        // Verifica se veio de filtros
        String status = getIntent().getStringExtra("filterStatus");
        String dataIni = getIntent().getStringExtra("filterDataIni");
        String dataFim = getIntent().getStringExtra("filterDataFim");
        String tipo = getIntent().getStringExtra("filterTipo");

        List<Chamado> lista;
        if (status != null || dataIni != null || tipo != null) {
            lista = dao.filtrar(status, dataIni, dataFim, tipo);
        } else {
            lista = dao.listarTodos();
        }

        ChamadosAdapter adapter = new ChamadosAdapter(lista);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dao.close();
    }
}
