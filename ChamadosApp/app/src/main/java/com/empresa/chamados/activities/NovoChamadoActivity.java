package com.empresa.chamados.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.empresa.chamados.R;
import com.empresa.chamados.database.ChamadoDAO;
import com.empresa.chamados.models.Chamado;
import com.empresa.chamados.utils.DateUtils;

public class NovoChamadoActivity extends AppCompatActivity {

    private EditText editTitulo, editDescricao, editLocal;
    private Spinner spinnerTipo;
    private ChamadoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_chamado);

        dao = new ChamadoDAO(this);
        dao.open();

        editTitulo = findViewById(R.id.edit_titulo);
        editDescricao = findViewById(R.id.edit_descricao);
        editLocal = findViewById(R.id.edit_local);
        spinnerTipo = findViewById(R.id.spinner_tipo);
        TextView txtData = findViewById(R.id.txt_data_auto);
        Button btnCadastrar = findViewById(R.id.btn_cadastrar);

        String dataAtual = DateUtils.getCurrentDate();
        txtData.setText("Data: " + dataAtual);

        String[] tipos = {"Infraestrutura", "TI"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tipos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapter);

        btnCadastrar.setOnClickListener(v -> cadastrar());
    }

    private void cadastrar() {
        String titulo = editTitulo.getText().toString();
        String descricao = editDescricao.getText().toString();
        String local = editLocal.getText().toString();
        String tipo = spinnerTipo.getSelectedItem().toString();

        if (titulo.length() < 5) {
            editTitulo.setError("Mínimo 5 caracteres");
            return;
        }
        if (descricao.length() < 10) {
            editDescricao.setError("Mínimo 10 caracteres");
            return;
        }
        if (local.isEmpty()) {
            editLocal.setError("Campo obrigatório");
            return;
        }

        Chamado c = new Chamado();
        c.setTitulo(titulo);
        c.setDescricao(descricao);
        c.setLocal(local);
        c.setTipo(tipo);
        c.setData(DateUtils.getCurrentDate());
        c.setStatus("Aberto");

        if (dao.inserirChamado(c) > 0) {
            Toast.makeText(this, "Chamado cadastrado!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao cadastrar", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dao.close();
    }
}
