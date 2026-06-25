package com.empresa.chamados.activities;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.empresa.chamados.R;
import com.empresa.chamados.database.ChamadoDAO;
import com.empresa.chamados.models.Chamado;
import com.empresa.chamados.utils.DateUtils;

import java.io.File;

public class AtendimentoChamadoActivity extends AppCompatActivity {

    private TextView txtTitulo, txtInfo, txtDescricao;
    private EditText editSolucao;
    private Spinner spinnerStatus;
    private ImageView imgImagem;
    private Chamado chamado;
    private ChamadoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atendimento);

        dao = new ChamadoDAO(this);
        dao.open();

        int id = getIntent().getIntExtra("CHAMADO_ID", -1);
        chamado = dao.buscarPorId(id);

        txtTitulo = findViewById(R.id.atend_titulo);
        txtInfo = findViewById(R.id.atend_info);
        txtDescricao = findViewById(R.id.atend_descricao);
        editSolucao = findViewById(R.id.edit_solucao);
        spinnerStatus = findViewById(R.id.spinner_status);
        imgImagem = findViewById(R.id.atend_imagem);
        Button btnSalvar = findViewById(R.id.btn_salvar_atendimento);

        if (chamado != null) {
            txtTitulo.setText(chamado.getTitulo());
            String status = chamado.getStatus() != null ? chamado.getStatus() : "Aberto";
            txtInfo.setText(chamado.getLocal() + " | " + chamado.getData() + " | " + status);
            txtDescricao.setText(chamado.getDescricao());
            editSolucao.setText(chamado.getSolucao());

            if (chamado.getImagem() != null && !chamado.getImagem().isEmpty()) {
                imgImagem.setVisibility(ImageView.VISIBLE);
                Glide.with(this)
                        .load(Uri.fromFile(new File(chamado.getImagem())))
                        .centerCrop()
                        .into(imgImagem);
            }
        }

        String[] statusOpcoes = {"Aberto", "Em andamento", "Concluído"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusOpcoes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        for (int i = 0; i < statusOpcoes.length; i++) {
            if (statusOpcoes[i].equals(chamado.getStatus())) {
                spinnerStatus.setSelection(i);
                break;
            }
        }

        btnSalvar.setOnClickListener(v -> salvar());
    }

    private void salvar() {
        String solucao = editSolucao.getText().toString();
        if (solucao.isEmpty()) {
            editSolucao.setError("Campo obrigatório");
            return;
        }

        chamado.setSolucao(solucao);
        chamado.setStatus(spinnerStatus.getSelectedItem().toString());
        chamado.setDataAtendimento(DateUtils.getCurrentDate());

        if (dao.atualizarChamado(chamado) > 0) {
            Toast.makeText(this, "Atendimento salvo!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao salvar", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dao != null) {
            dao.close();
        }
    }
}
