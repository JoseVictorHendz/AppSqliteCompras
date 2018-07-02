package com.example.jose_victor.totalcompras;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BancoDados extends SQLiteOpenHelper{

    private static final int VERSAO_BANCO = 1;
    private static final String BANCO_PRODUTO = "bd_produtos";

    private static final String TABELA_PRODUTO = "bd_produtos";

    private static final String COLUNA_CODIGO = "codigo";
    private static final String COLUNA_QTD = "qtd";
    private static final String COLUNA_PRECO = "preco";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_MARCA = "marca";

    public BancoDados(Context context) {
        super(context, BANCO_PRODUTO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY_COLUNA = "CREATE TABLE " + TABELA_PRODUTO + "("
                + COLUNA_CODIGO + " INTEGER PRIMARY KEY, "
                + COLUNA_QTD + " INTEGER, "
                + COLUNA_PRECO + " REAL, "
                + COLUNA_NOME + " TEXT, "
                + COLUNA_MARCA + " INTEGER)";

        db.execSQL(QUERY_COLUNA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    void addProduto (Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_QTD, produto.getQtd());
        values.put(COLUNA_PRECO, produto.getPreco());
        values.put(COLUNA_NOME, produto.getNome());
        values.put(COLUNA_MARCA, produto.getMarca());

        db.insert(TABELA_PRODUTO, null, values);

        db.close();
    }

    void apagarProduto(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABELA_PRODUTO, COLUNA_CODIGO + " = ?", new String[] { String.valueOf(produto.getCodigo())});

        db.close();
    }

    Produto selecionarProduto(int codigo) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_PRODUTO, new String[] {COLUNA_CODIGO,
        COLUNA_QTD, COLUNA_PRECO, COLUNA_NOME, COLUNA_MARCA},
                COLUNA_CODIGO + " = ?", new String[] {String.valueOf(codigo)},
                null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();
        }

        Produto produto = new Produto(Integer.parseInt(cursor.getString(0)),
                cursor.getInt(1), cursor.getDouble(2), cursor.getString(3), cursor.getString(4));

        return produto;
    }

    void atualizarProduto(Produto produto){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_QTD, produto.getQtd());
        values.put(COLUNA_PRECO, produto.getPreco());
        values.put(COLUNA_NOME, produto.getNome());
        values.put(COLUNA_MARCA, produto.getMarca());

        db.update(TABELA_PRODUTO, values, COLUNA_CODIGO + "=?",
                new String[] {String.valueOf(produto.getCodigo())});
    }

    public List<Produto> listaTodosProdutos() {
        List<Produto> listaProdutos = new ArrayList<Produto>();

        String query = " SELECT * FROM " + TABELA_PRODUTO;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()) {
            do {
                Produto produto = new Produto();
                produto.setCodigo(Integer.parseInt(c.getString(0)));
                produto.setQtd(Integer.parseInt(c.getString(1)));
                produto.setPreco(Double.parseDouble(c.getString(2)));
                produto.setNome(c.getString(3));
                produto.setMarca(c.getString(4));

                listaProdutos.add(produto);
            }while (c.moveToNext());
        }

        return listaProdutos;
    }
}
