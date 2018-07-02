package com.example.jose_victor.totalcompras;

public class Produto {

    int codigo;
    int qtd;
    double preco;
    String nome;
    String marca;

    public Produto() {

    }

    public Produto(int _codigo, int _qtd, double _preco, String _nome, String _marca) {
        this.codigo = _codigo;
        this.qtd = _qtd;
        this.preco = _preco;
        this.nome = _nome;
        this.marca = _marca;
    }

    public Produto(int _qtd, double _preco, String _nome, String _marca) {
        this.qtd = _qtd;
        this.preco = _preco;
        this.nome = _nome;
        this.marca = _marca;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
