package br.fib.teste;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.fib.teste.model.Usuario;

public class Leilao {
	
    private String descricao;
    private List<Lance> lances;

    public Leilao(String descricao) {
        this.descricao = descricao;
        this.lances = new ArrayList<Lance>();
    }
    
    public String getDescricao() {
        return descricao;
    }

    public List<Lance> getLances() {
        return Collections.unmodifiableList(lances);
    }

    public void propoe(Lance lance) {
        if(lances.isEmpty() || podeDarLance(lance.getUsuario())) {
            lances.add(lance);
        }
    }

	private boolean podeDarLance(Usuario usuario) {
		return !ultimoLanceDado().getUsuario().equals(usuario) && 
        qtdDelancesDo(usuario) < 5;
	}
    
    private int qtdDelancesDo(Usuario usuario) {
        int total = 0;
        for(Lance lance : lances) {
            if(lance.getUsuario().equals(usuario)) total++;
        }
        return total;
    }

	private Lance ultimoLanceDado() {
		return lances.get(lances.size() - 1);
	}
}
