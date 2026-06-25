package com.empresa.chamados.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "chamados_db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_CHAMADOS = "chamados";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITULO = "titulo";
    public static final String COLUMN_DATA = "data";
    public static final String COLUMN_DESCRICAO = "descricao";
    public static final String COLUMN_LOCAL = "local";
    public static final String COLUMN_TIPO = "tipo";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_SOLUCAO = "solucao";
    public static final String COLUMN_DATA_ATENDIMENTO = "data_atendimento";
    public static final String COLUMN_IMAGEM = "imagem";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_CHAMADOS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITULO + " TEXT NOT NULL, " +
            COLUMN_DATA + " TEXT NOT NULL, " +
            COLUMN_DESCRICAO + " TEXT NOT NULL, " +
            COLUMN_LOCAL + " TEXT NOT NULL, " +
            COLUMN_TIPO + " TEXT, " +
            COLUMN_STATUS + " TEXT DEFAULT 'Aberto', " +
            COLUMN_SOLUCAO + " TEXT, " +
            COLUMN_DATA_ATENDIMENTO + " TEXT, " +
            COLUMN_IMAGEM + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_CHAMADOS + " ADD COLUMN " + COLUMN_IMAGEM + " TEXT");
        }
    }
}
