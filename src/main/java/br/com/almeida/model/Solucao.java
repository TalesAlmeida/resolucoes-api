package br.com.almeida.model;

public class Solucao {

	private int id;
	
	private String assunto;
	
	private String descricao;
	
	private Integer idSistema;
	
	private Integer idNivelVisualizacao;
	private Integer idEmpresa;
	
	private String sistema;
	
	private String empresa;

	public int getId() {
		return id;
	}

	
	
	public void setId(int id) {
		this.id = id;
	}

	
	
	
	public String getAssunto() {
		return assunto;
	}



	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}



	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getIdSistema() {
		return idSistema;
	}

	public void setIdSistema(Integer idSistema) {
		this.idSistema = idSistema;
	}

	public Integer getIdNivelVisualizacao() {
		return idNivelVisualizacao;
	}

	public void setIdNivelVisualizacao(Integer idNivelVisualizacao) {
		this.idNivelVisualizacao = idNivelVisualizacao;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}



	public String getSistema() {
		return sistema;
	}



	public void setSistema(String sistema) {
		this.sistema = sistema;
	}



	public String getEmpresa() {
		return empresa;
	}



	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	
		
}
