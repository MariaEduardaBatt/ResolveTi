package com.empresa.chamados.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.empresa.chamados.R;

public class FiltrosChamadosActivity extends AppCompatActivity {

    private Spinner spinnerStatus, spinnerTipo;
    private EditText editDataIni, editDataFim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros);

        spinnerStatus = findViewById(R.id.filter_spinner_status);
        spinnerTipo = findViewById(R.id.filter_spinner_tipo);
        editDataIni = findViewById(R.id.filter_data_inicio);
        editDataFim = findViewById(R.id.filter_data_fim);
        Button btnAplicar = findViewById(R.id.btn_aplicar_filtros);

        String[] statusOpcoes = {"", "Aberto", "Em Atendimento", "Concluído"};
        ArrayAdapter<String> adapterStatus = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusOpcoes);
        spinnerStatus.setAdapter(adapterStatus);

        String[] tipoOpcoes = {"", "Infraestrutura", "TI"};
        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tipoOpcoes);
        spinnerTipo.setAdapter(adapterTipo);

        btnAplicar.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListaChamadosActivity.class);
            intent.putExtra("filterStatus", spinnerStatus.getSelectedItem().toString());
            intent.putExtra("filterTipo", spinnerTipo.getSelectedItem().toString());
            intent.putExtra("filterDataIni", editDataIni.getText().toString());
            intent.putExtra("filterDataFim", editDataFim.getText().toString());
            startActivity(intent);
        });
    }
}
