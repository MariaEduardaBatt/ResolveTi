package com.empresa.chamados.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.empresa.chamados.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_novo_chamado).setOnClickListener(v -> {
            startActivity(new Intent(this, NovoChamadoActivity.class));
        });

        findViewById(R.id.btn_lista_chamados).setOnClickListener(v -> {
            startActivity(new Intent(this, ListaChamadosActivity.class));
        });

        findViewById(R.id.btn_filtrar_chamados).setOnClickListener(v -> {
            startActivity(new Intent(this, FiltrosChamadosActivity.class));
        });
    }
}
