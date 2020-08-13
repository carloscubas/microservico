package br.fib.teste.model;

public class Usuario {
	
    private int id;
    private String nome;

    public Usuario(String nome) {
        this.nome = nome;
    }

    public Usuario(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
