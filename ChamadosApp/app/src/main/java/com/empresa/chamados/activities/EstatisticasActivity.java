package com.empresa.chamados.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.empresa.chamados.R;
import com.empresa.chamados.database.ChamadoDAO;

public class EstatisticasActivity extends AppCompatActivity {

    private ChamadoDAO dao;
    private TextView txtTotal, txtAbertos, txtAndamento, txtConcluidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatisticas);

        dao = new ChamadoDAO(this);
        dao.open();

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        txtTotal = findViewById(R.id.txt_total);
        txtAbertos = findViewById(R.id.txt_abertos);
        txtAndamento = findViewById(R.id.txt_andamento);
        txtConcluidos = findViewById(R.id.txt_concluidos);

        carregarEstatisticas();
    }

    private void carregarEstatisticas() {
        int total = dao.contarTotal();
        int abertos = dao.contarPorStatus("Aberto");
        int andamento = dao.contarPorStatus("Em andamento");
        int concluidos = dao.contarPorStatus("Concluído");

        txtTotal.setText(String.valueOf(total));
        txtAbertos.setText(String.valueOf(abertos));
        txtAndamento.setText(String.valueOf(andamento));
        txtConcluidos.setText(String.valueOf(concluidos));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dao != null) {
            dao.close();
        }
    }
}
