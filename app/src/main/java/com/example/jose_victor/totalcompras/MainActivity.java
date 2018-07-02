package com.example.jose_victor.totalcompras;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editCodigo, editQtd, editPreco, editNome, editMarca, editTotal;
    Button btnLimpar, btnSalvar, btnExcluir;
    ListView listViewProdutos;

    BancoDados db = new BancoDados(this);

    ArrayAdapter<String> adapter;

    ArrayList<String> arrayList;

    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCodigo = (EditText)findViewById(R.id.editCodigo);
        editQtd = (EditText)findViewById(R.id.editQtd);
        editPreco = (EditText)findViewById(R.id.editPreco);
        editNome = (EditText)findViewById(R.id.editNome);
        editMarca = (EditText)findViewById(R.id.editMarca);
        editTotal = (EditText)findViewById(R.id.editTotal);

        btnLimpar = (Button)findViewById(R.id.btnLimpar);
        btnSalvar = (Button)findViewById(R.id.btnSalvar);
        btnExcluir = (Button)findViewById(R.id.btnExcluir);

        imm = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);

        listViewProdutos = (ListView)findViewById(R.id.listViewProdutos);

        listarProdutos();

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpaCampos();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String codigo = editCodigo.getText().toString();
                String qtd = (editQtd.getText().toString());
                String preco = editPreco.getText().toString();
                String nome = editNome.getText().toString();
                String marca = editMarca.getText().toString();

                if(preco.isEmpty()) {

                    editPreco.setError("Este campo é obrigatório");

                } else if(codigo.isEmpty()) {

                    db.addProduto(new Produto(Integer.parseInt(qtd), Double.parseDouble(preco), nome, marca));

                    Toast.makeText(MainActivity.this, "Produto adicionado com sucesso", Toast.LENGTH_LONG).show();

                    limpaCampos();
                    listarProdutos();
                    escondeTeclado();


                } else {

                    db.atualizarProduto(new Produto(Integer.parseInt(codigo), Integer.parseInt(qtd), Double.parseDouble(preco), nome, marca));
                    Toast.makeText(MainActivity.this, "Produto atualizado com sucesso", Toast.LENGTH_LONG).show();

                    limpaCampos();
                    listarProdutos();
                    escondeTeclado();
                }



            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigo = editCodigo.getText().toString();

                if(codigo.isEmpty()) {

                    Toast.makeText(MainActivity.this, "Nenhum produto esta selecionado", Toast.LENGTH_LONG).show();
                    escondeTeclado();
                } else {
                    Produto produto = new Produto();
                    produto.setCodigo(Integer.parseInt(codigo));
                    db.apagarProduto(produto);

                }

                limpaCampos();
                listarProdutos();
                listarProdutos();
                escondeTeclado();            }
        });

        listViewProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String conteudo = (String) listViewProdutos.getItemAtPosition(position);
                String codigo = conteudo.substring(0, conteudo.indexOf("-"));

                Produto produto = db.selecionarProduto(Integer.parseInt(codigo));
                editCodigo.setText(String.valueOf(produto.getCodigo()));
                editQtd.setText(String.valueOf(produto.getQtd()));
                editPreco.setText(String.valueOf(produto.getPreco()));
                editNome.setText(produto.getNome());
                editMarca.setText(produto.getMarca());

            }
        });
    }

    void escondeTeclado() {
        imm.hideSoftInputFromWindow(editQtd.getWindowToken(), 0);
    }

    void limpaCampos() {
        editCodigo.setText("");
        editQtd.setText("");
        editPreco.setText("");
        editNome.setText("");
        editMarca.setText("");

        editNome.requestFocus();
    }

    public void listarProdutos() {
        List<Produto> produtos = db.listaTodosProdutos();

        double valorTotal = 0;

        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);

        listViewProdutos.setAdapter(adapter);

        for(Produto p : produtos) {

            arrayList.add(p.getCodigo() + "-" + "Nome: " +
                    p.getNome() + " Marca: " +
                    p.getMarca() + "\nQuantidade: " +
                    p.getQtd() + " Preco: " +
                    p.getPreco());


            adapter.notifyDataSetChanged();

            valorTotal += p.getPreco();
        }

        editTotal.setText("Valor total: " + valorTotal);


    }
}
