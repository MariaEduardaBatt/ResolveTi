package com.empresa.chamados.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.empresa.chamados.models.Chamado;
import java.util.ArrayList;
import java.util.List;

public class ChamadoDAO {
    private SQLiteDatabase db;
    private DatabaseHelper helper;

    public ChamadoDAO(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void open() {
        db = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public long inserirChamado(Chamado chamado) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TITULO, chamado.getTitulo());
        values.put(DatabaseHelper.COLUMN_DATA, chamado.getData());
        values.put(DatabaseHelper.COLUMN_DESCRICAO, chamado.getDescricao());
        values.put(DatabaseHelper.COLUMN_LOCAL, chamado.getLocal());
        values.put(DatabaseHelper.COLUMN_TIPO, chamado.getTipo());
        values.put(DatabaseHelper.COLUMN_STATUS, chamado.getStatus());
        values.put(DatabaseHelper.COLUMN_IMAGEM, chamado.getImagem());
        return db.insert(DatabaseHelper.TABLE_CHAMADOS, null, values);
    }

    public List<Chamado> listarTodos() {
        List<Chamado> lista = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_CHAMADOS, null, null, null, null, null, DatabaseHelper.COLUMN_DATA + " DESC");
        if (cursor.moveToFirst()) {
            do {
                lista.add(cursorToChamado(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

    public List<Chamado> filtrar(String status, String dataInicio, String dataFim, String tipo) {
        List<Chamado> lista = new ArrayList<>();
        StringBuilder selection = new StringBuilder("1=1");
        List<String> args = new ArrayList<>();

        if (status != null && !status.isEmpty()) {
            selection.append(" AND ").append(DatabaseHelper.COLUMN_STATUS).append(" = ?");
            args.add(status);
        }
        if (tipo != null && !tipo.isEmpty()) {
            selection.append(" AND ").append(DatabaseHelper.COLUMN_TIPO).append(" = ?");
            args.add(tipo);
        }
        if (dataInicio != null && !dataInicio.isEmpty() && dataFim != null && !dataFim.isEmpty()) {
            selection.append(" AND ").append(DatabaseHelper.COLUMN_DATA).append(" BETWEEN ? AND ?");
            args.add(dataInicio);
            args.add(dataFim);
        }

        Cursor cursor = db.query(DatabaseHelper.TABLE_CHAMADOS, null, selection.toString(), args.toArray(new String[0]), null, null, DatabaseHelper.COLUMN_DATA + " DESC");
        if (cursor.moveToFirst()) {
            do {
                lista.add(cursorToChamado(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

    public int atualizarChamado(Chamado chamado) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_STATUS, chamado.getStatus());
        values.put(DatabaseHelper.COLUMN_SOLUCAO, chamado.getSolucao());
        values.put(DatabaseHelper.COLUMN_DATA_ATENDIMENTO, chamado.getDataAtendimento());
        return db.update(DatabaseHelper.TABLE_CHAMADOS, values, DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(chamado.getId())});
    }

    public Chamado buscarPorId(int id) {
        Cursor cursor = db.query(DatabaseHelper.TABLE_CHAMADOS, null, DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()) {
            Chamado c = cursorToChamado(cursor);
            cursor.close();
            return c;
        }
        cursor.close();
        return null;
    }

    public int contarTotal() {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_CHAMADOS, null);
        cursor.moveToFirst();
        int total = cursor.getInt(0);
        cursor.close();
        return total;
    }

    public int contarPorStatus(String status) {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_CHAMADOS + " WHERE " + DatabaseHelper.COLUMN_STATUS + " = ?", new String[]{status});
        cursor.moveToFirst();
        int total = cursor.getInt(0);
        cursor.close();
        return total;
    }

    private Chamado cursorToChamado(Cursor cursor) {
        Chamado c = new Chamado();
        c.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
        c.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITULO)));
        c.setData(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATA)));
        c.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRICAO)));
        c.setLocal(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LOCAL)));
        c.setTipo(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TIPO)));
        c.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_STATUS)));
        c.setSolucao(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SOLUCAO)));
        c.setDataAtendimento(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATA_ATENDIMENTO)));
        if (cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGEM) >= 0) {
            c.setImagem(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGEM)));
        }
        return c;
    }
}
