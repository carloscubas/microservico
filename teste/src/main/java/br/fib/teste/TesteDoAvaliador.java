package br.fib.teste;

import br.fib.teste.model.Usuario;

public class TesteDoAvaliador {

	public static void main(String[] args) {

		Usuario joao = new Usuario("Joao");
		Usuario jose = new Usuario("José");
		Usuario maria = new Usuario("Maria");
		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 300.0));
		leilao.propoe(new Lance(jose, 400.0));
		leilao.propoe(new Lance(maria, 250.0));

		Avaliador avaliador = new Avaliador();

		avaliador.avalia(leilao);

		System.out.println(400 == avaliador.getMaiorLance()); // imprime 400.0
		System.out.println(250 == avaliador.getMenorLance()); // imprime 250.0
	}
}
