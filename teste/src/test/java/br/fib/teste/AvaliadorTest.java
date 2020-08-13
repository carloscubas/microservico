package br.fib.teste;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.fib.teste.model.Usuario;

public class AvaliadorTest {

	Avaliador avaliador;
	private Usuario joao;
	private Usuario jose;
	private Usuario maria;
	private Usuario steveJobs;
	private Usuario billGates;
	private Leilao leilao;

	@Before
	public void init() {
		avaliador = new Avaliador();
		joao = new Usuario("Joao");
		jose = new Usuario("José");
		maria = new Usuario("Maria");
		steveJobs = new Usuario("Steve Jobs");
		billGates = new Usuario("Bill Gates");
		leilao = new Leilao("Playstation 3 Novo");
	}

	@Test
	public void deveRetornarOMaioreoMenorLance() {
		Leilao leilao = new CriadorDeLeilao()
		.para("Computador Quantico")
		.lance(joao, 250.0)
		.lance(jose, 300.0)
		.lance(maria, 400.0)
		.constroi();
	
		avaliador.avalia(leilao);
		assertEquals(400, avaliador.getMaiorLance(), 0.00001);
		assertEquals(250, avaliador.getMenorLance(), 0.00001);
	}

	@Test
	public void deveEntenderLanceEmOrdemCrescenteComOutrosValores() {
		
		Leilao leilao = new CriadorDeLeilao()
		.para("Playstation 3 Novo")
		.lance(joao, 1000.0)
		.lance(jose, 2000.0)
		.lance(maria, 3000.0)
		.constroi();

		avaliador.avalia(leilao);

		assertEquals(3000, avaliador.getMaiorLance(), 0.00001);
		assertEquals(1000, avaliador.getMenorLance(), 0.00001);
	}

	@Test
	public void deveEntenderLanceEmOrdemDeCrescenteComOutrosValores() {

		Leilao leilao = new CriadorDeLeilao()
		.para("Playstation 3 Novo")
		.lance(joao, 3000.0)
		.lance(jose, 2000.0)
		.lance(maria, 1000.0)
		.constroi();

		avaliador.avalia(leilao);

		assertEquals(3000, avaliador.getMaiorLance(), 0.00001);
		assertEquals(1000, avaliador.getMenorLance(), 0.00001);
	}

	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
		Leilao leilao = new CriadorDeLeilao()
		.para("Playstation 3 Novo")
		.lance(joao, 1000.0)
		.constroi();
		
		avaliador.avalia(leilao);

		assertEquals(1000, avaliador.getMaiorLance(), 0.00001);
		assertEquals(1000, avaliador.getMenorLance(), 0.00001);
	}

	@Test
	public void deveEncontrarOsTresMaioresLances() {
		Leilao leilao = new CriadorDeLeilao()
		.para("Playstation 3 Novo")
		.lance(joao, 100.0)
		.lance(maria, 200.0)
		.lance(joao, 300.0)
		.lance(maria, 400.0)
		.constroi();

		avaliador.avalia(leilao);

		List<Lance> maiores = avaliador.getTresMaiores();

		assertEquals(3, maiores.size());
		assertEquals(400, maiores.get(0).getValor(), 0.00001);
		assertEquals(300, maiores.get(1).getValor(), 0.00001);
		assertEquals(200, maiores.get(2).getValor(), 0.00001);
	}

	@Test
	public void deveEncontrarOsTresMaioresLancesComAPenasumLance() {

		Leilao leilao = new CriadorDeLeilao()
		.para("Playstation 3 Novo")
		.lance(joao, 100.0)
		.lance(maria, 200.0)
		.constroi();

		avaliador.avalia(leilao);

		List<Lance> maiores = avaliador.getTresMaiores();

		assertEquals(2, maiores.size());
		assertEquals(200, maiores.get(0).getValor(), 0.00001);
		assertEquals(100, maiores.get(1).getValor(), 0.00001);
	}

	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
		Leilao leilao = new CriadorDeLeilao()
		.para("Playstation 3 Novo")
		.lance(steveJobs, 2000.0)
		.lance(steveJobs, 3000.0)
		.constroi();
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
	}

	@Test
	public void naoDeveAceitarMaisDoQue5LancesDeUmMesmoUsuario() {
		
		Leilao leilao = new CriadorDeLeilao()
		.para("Playstation 3 Novo")
		.lance(steveJobs, 2000)
		.lance(billGates, 3000)
		.lance(steveJobs, 4000)
		.lance(billGates, 5000)
		.lance(steveJobs, 6000)
		.lance(billGates, 7000)
		.lance(steveJobs, 8000)
		.lance(billGates, 9000)
		.lance(steveJobs, 10000)
		.lance(billGates, 11000)
	
		// deve ser ignorado
		.lance(steveJobs, 12000)
		.constroi();

		assertEquals(10, leilao.getLances().size());
		int ultimo = leilao.getLances().size() - 1;
		Lance ultimoLance = leilao.getLances().get(ultimo);
		assertEquals(11000.0, ultimoLance.getValor(), 0.00001);
	}

}
