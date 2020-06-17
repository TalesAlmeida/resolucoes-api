package br.com.almeida.dao;

import java.util.List;

public interface Crud {

	public Integer incluir(Object o);
	
	public boolean alterar(Object o);
	
	public boolean deletar(int id);
	
	public Object buscaPorId(int id);
	
	public List<Object> buscarPorNome(String nome);
	
	public List<Object> listar();
	
	public Integer buscaUltimoIdIncluso();
	
}

