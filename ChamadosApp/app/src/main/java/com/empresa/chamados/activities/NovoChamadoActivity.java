package com.empresa.chamados.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.empresa.chamados.R;
import com.empresa.chamados.database.ChamadoDAO;
import com.empresa.chamados.models.Chamado;
import com.empresa.chamados.utils.DateUtils;
import com.parse.ParseObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NovoChamadoActivity extends AppCompatActivity {

    private EditText editTitulo, editDescricao, editLocal;
    private Spinner spinnerStatus;
    private ImageView imgPreview;
    private ChamadoDAO dao;
    private String fotoPath;

    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null
                    && result.getData().getExtras() != null
                    && result.getData().getExtras().get("data") != null) {

                Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                imgPreview.setVisibility(ImageView.VISIBLE);
                imgPreview.setImageBitmap(bitmap);

                try {
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                    File storageDir = new File(getFilesDir(), "chamados");
                    storageDir.mkdirs();
                    File fotoFile = new File(storageDir, "chamado_" + timeStamp + ".jpg");
                    fotoPath = fotoFile.getAbsolutePath();
                    FileOutputStream fos = new FileOutputStream(fotoFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos);
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Foto não capturada", Toast.LENGTH_SHORT).show();
            }
        }
    );

    private final ActivityResultLauncher<String> permissionLauncher = registerForActivityResult(
        new ActivityResultContracts.RequestPermission(),
        granted -> {
            if (granted) {
                abrirCamera();
            } else {
                Toast.makeText(this, "Permissão de câmera necessária. Vá em Configurações > Apps > Resolve TI > Permissões e ative Câmera.", Toast.LENGTH_LONG).show();
            }
        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_chamado);

        dao = new ChamadoDAO(this);
        dao.open();

        editTitulo = findViewById(R.id.edit_titulo);
        editDescricao = findViewById(R.id.edit_descricao);
        editLocal = findViewById(R.id.edit_local);
        spinnerStatus = findViewById(R.id.spinner_status);
        imgPreview = findViewById(R.id.img_preview);
        TextView txtData = findViewById(R.id.txt_data_auto);
        Button btnCadastrar = findViewById(R.id.btn_cadastrar);
        Button btnFoto = findViewById(R.id.btn_foto);

        txtData.setText("Data: " + DateUtils.getCurrentDate());

        String[] statusOpcoes = {"Aberto", "Em andamento", "Concluído"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusOpcoes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        btnFoto.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                abrirCamera();
            } else {
                permissionLauncher.launch(Manifest.permission.CAMERA);
            }
        });
        btnCadastrar.setOnClickListener(v -> cadastrar());
    }

    private void abrirCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(this, "Câmera não disponível", Toast.LENGTH_SHORT).show();
            return;
        }
        cameraLauncher.launch(intent);
    }

    private void cadastrar() {
        String titulo = editTitulo.getText().toString().trim();
        String descricao = editDescricao.getText().toString().trim();
        String local = editLocal.getText().toString().trim();
        String status = spinnerStatus.getSelectedItem().toString();

        if (titulo.isEmpty()) {
            editTitulo.setError("Campo obrigatório");
            return;
        }
        if (descricao.isEmpty()) {
            editDescricao.setError("Campo obrigatório");
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
        c.setStatus(status);
        c.setData(DateUtils.getCurrentDate());
        c.setImagem(fotoPath);

        long id = dao.inserirChamado(c);
        if (id > 0) {
            c.setId((int) id);
            new Thread(() -> {
                try {
                    ParseObject po = new ParseObject("Chamado");
                    po.put("titulo", c.getTitulo());
                    po.put("descricao", c.getDescricao());
                    po.put("local", c.getLocal());
                    po.put("status", c.getStatus());
                    po.put("dataCadastro", c.getData());
                    po.put("imagem", c.getImagem() != null ? c.getImagem() : "");
                    po.save();
                    Log.i("ChamadosApp", "Salvo na nuvem com sucesso");
                } catch (Exception e) {
                    Log.e("ChamadosApp", "Erro ao salvar nuvem", e);
                }
            }).start();
            Toast.makeText(this, "Chamado cadastrado!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao cadastrar", Toast.LENGTH_SHORT).show();
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
