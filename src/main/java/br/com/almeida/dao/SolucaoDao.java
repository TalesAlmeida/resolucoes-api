package br.com.almeida.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.almeida.model.NivelVisualizacao;
import br.com.almeida.model.Solucao;
import br.com.almeida.util.Conexao;

public class SolucaoDao implements Crud {

	Conexao con = new Conexao();
	PreparedStatement ps = null;
	Statement stm = null;
	ResultSet rs = null;
	String sql = "";
	Solucao solucao;
	List<Solucao> lista = null;
	List<Object> listaObjeto = null;

	@Override
	public Integer incluir(Object o) {
		sql = "insert into solucao (id,assunto, descricao, id_sistema,id_visualizacao,id_empresa) values(gen_id(gen_solucao_id,1),?,?, ?, ?,?)";
		solucao = new Solucao();
		int inserido = 0;

		solucao = (Solucao) o;

		System.out.println("sol: " + solucao.getDescricao());

		try {
			ps = con.getConnection().prepareStatement(sql);
			// ps.setString(1, "gen_id(gen_nivel_visualizacao_id,1");// usa o genarator do
			// banco
			ps.setString(1, solucao.getAssunto().toUpperCase());
			ps.setString(2, solucao.getDescricao());
			ps.setInt(3, solucao.getIdSistema());
			ps.setInt(4, 1);
			ps.setInt(5, solucao.getIdEmpresa());

			inserido = ps.executeUpdate();

			if (inserido == 1) {
				System.out.println("inserido com sucesso !");
				inserido = buscaUltimoIdIncluso();// após inserido, busca o ID para retornar
			} else {
				System.out.println("Não inserido !");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return inserido;
	}

	@Override
	public boolean alterar(Object o) {
		solucao = new Solucao();

		solucao = (Solucao) o;

		Solucao objetoEncontraNaBase = new Solucao();

		int linhasAlteradas = 0;

		boolean alterado = false;

		System.out.println(solucao.getDescricao());
		System.out.println(solucao.getId());

		objetoEncontraNaBase = (Solucao) buscaPorId(solucao.getId());// busca o objeto na base antes
																		// de alterar

		if (objetoEncontraNaBase.getId() > 0) {

			solucao = (Solucao) o;

			sql = "update nivel_visualizacao set assunto, descricao, id_sistema, id_empresa = '"
					+solucao.getAssunto().toUpperCase()+"," +solucao.getDescricao()+","+ solucao.getIdSistema()+","+solucao.getIdEmpresa()
					+ "' where id = " + solucao.getId();

			System.out.println(sql);
			try {
				stm = con.getConnection().createStatement();

				linhasAlteradas = stm.executeUpdate(sql);
				System.out.println(linhasAlteradas);

				if (linhasAlteradas > 0) {
					System.out.println("Alterado com sucesso !  -  Linhas alteradas: " + linhasAlteradas);
					alterado = true;

				} else {
					System.out.println("Não foi possivel alterar !  - Linhas alteradas: " + linhasAlteradas);
					alterado = false;
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				try {

					if (!ps.isClosed()) {
						ps.close();
						con.getConnection().close();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		} else {
			System.out.println("Id não encontrado na base: " + solucao.getId());
			alterado = false;
		}
		return alterado;
	}

	@Override
	public boolean deletar(int id) {
		boolean deletado = false;
		int linhasDeletadas = 0;

		try {
			solucao = (Solucao) buscaPorId(id);

			sql = "delete from solucao where id = " + solucao.getId();

			// Primeiro consulta na base, se encontrar deleta
			if (solucao != null & solucao.getId() > 0) {

				stm = con.getConnection().createStatement();

				linhasDeletadas = stm.executeUpdate(sql);

				System.out.println("Linhas deletadas: " + linhasDeletadas);

				if (linhasDeletadas > 0) {
					System.out.println("Deletado com sucesso !");
					return true;
				} else {
					System.out.println("Não deletado !");
					return false;
				}
			} else {
				System.out.println("Não encontrado na base !");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return deletado;
	}

	@Override
	public Object buscaPorId(int id) {
		sql = "select *from vsolucao where id = ?";
		try {
			ps = con.getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				solucao = new Solucao();
				solucao.setId(rs.getInt("id"));
				solucao.setAssunto(rs.getString("assunto"));
				solucao.setDescricao(rs.getString("descricao"));
				// solucao.setIdSistema(rs.getInt("id_sistema"));
				// solucao.setIdNivelVisualizacao(rs.getInt("id_visualizacao"));

				solucao.setEmpresa(rs.getString("nome_empresa"));
				solucao.setSistema(rs.getString("nome_sistema"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {

				if (!ps.isClosed()) {
					ps.close();
					con.getConnection().close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		return solucao;
	}

	@Override
	public List<Object> buscarPorNome(String nome) {
		sql = "select *from vsolucao where assunto like '%" + nome.toUpperCase() + "%'";

		// lista = new ArrayList<NivelVisualizacao>();
		listaObjeto = new ArrayList<Object>();
		try {
			ps = con.getConnection().prepareStatement(sql);
			// ps.setString(1, nome.toUpperCase());
			rs = ps.executeQuery();

			while (rs.next()) {
				solucao = new Solucao();
				solucao.setId(rs.getInt("id"));
				solucao.setAssunto(rs.getString("assunto"));
				solucao.setDescricao(rs.getString("descricao"));
				// solucao.setIdSistema(rs.getInt("id_sistema"));
				// solucao.setIdNivelVisualizacao(rs.getInt("id_visualizacao"));

				solucao.setEmpresa(rs.getString("nome_empresa"));
				solucao.setSistema(rs.getString("nome_sistema"));

				listaObjeto.add(solucao);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaObjeto;
	}

	@Override
	public List<Object> listar() {
		sql = "select *from vsolucao";

		// lista = new ArrayList<NivelVisualizacao>();
		listaObjeto = new ArrayList<Object>();
		try {
			ps = con.getConnection().prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				solucao = new Solucao();
				solucao.setId(rs.getInt("id"));
				solucao.setAssunto(rs.getString("assunto"));
				solucao.setDescricao(rs.getString("descricao"));
				// solucao.setIdSistema(rs.getInt("nome_sistema"));
				// solucao.setIdNivelVisualizacao(rs.getInt("id_visualizacao"));
				// solucao.setIdEmpresa(rs.getInt("nome_empresa"));
				solucao.setEmpresa(rs.getString("nome_empresa"));
				solucao.setSistema(rs.getString("nome_sistema"));
				listaObjeto.add(solucao);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaObjeto;
	}

	@Override
	public Integer buscaUltimoIdIncluso() {
		sql = "select max(id) from solucao";
		Integer ultimoIdNaBase = 0;
		try {
			ps = con.getConnection().prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next()) {
				solucao = new Solucao();
				ultimoIdNaBase = rs.getInt(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ultimoIdNaBase;
	}

	public List<Object> buscarPorEmpresa(int id) {
		sql = "select *from solucao where id_empresa = ?";

		// lista = new ArrayList<NivelVisualizacao>();
		listaObjeto = new ArrayList<Object>();
		try {
			ps = con.getConnection().prepareStatement(sql);
			// ps.setString(1, nome.toUpperCase());
			ps.setInt(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				solucao = new Solucao();
				solucao.setId(rs.getInt("id"));
				solucao.setAssunto(rs.getString("assunto"));
				solucao.setDescricao(rs.getString("descricao"));
				solucao.setIdSistema(rs.getInt("id_sistema"));
				solucao.setIdNivelVisualizacao(rs.getInt("id_visualizacao"));
				solucao.setIdEmpresa(rs.getInt("id_empresa"));

				listaObjeto.add(solucao);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaObjeto;
	}

}
