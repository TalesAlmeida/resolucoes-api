package br.com.almeida.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Soundbank;

import br.com.almeida.model.Empresa;
import br.com.almeida.model.NivelVisualizacao;
import br.com.almeida.util.Conexao;

public class NivelVisualizacaoDao implements Crud {

	Conexao con = new Conexao();
	PreparedStatement ps = null;
	Statement stm = null;
	ResultSet rs = null;
	String sql = "";
	NivelVisualizacao nivelVisualizacao;
	List<NivelVisualizacao> lista = null;
	List<Object> listaObjeto = null;

	@Override
	public Integer incluir(Object o) {
		sql = "insert into nivel_visualizacao (id, descricao) values(gen_id(gen_nivel_visualizacao_id,1),?)";
		nivelVisualizacao = new NivelVisualizacao();
		int inserido = 0;

		nivelVisualizacao = (NivelVisualizacao) o;

		try {
			ps = con.getConnection().prepareStatement(sql);
			// ps.setString(1, "gen_id(gen_nivel_visualizacao_id,1");// usa o genarator do
			// banco
			ps.setString(1, nivelVisualizacao.getDescricao().toUpperCase());
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

		nivelVisualizacao = new NivelVisualizacao();

		nivelVisualizacao = (NivelVisualizacao) o;

		NivelVisualizacao objetoEncontraNaBase = new NivelVisualizacao();

		int linhasAlteradas = 0;

		boolean alterado = false;

		System.out.println(nivelVisualizacao.getDescricao());
		System.out.println(nivelVisualizacao.getId());

		objetoEncontraNaBase = (NivelVisualizacao) buscaPorId(nivelVisualizacao.getId());// busca o objeto na base antes
																							// de alterar

		if (objetoEncontraNaBase.getId() > 0) {

			nivelVisualizacao = (NivelVisualizacao) o;
			
			sql = "update nivel_visualizacao set descricao = '" + nivelVisualizacao.getDescricao().toUpperCase()
					+ "' where id = " + nivelVisualizacao.getId();

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
			System.out.println("Id não encontrado na base: " + nivelVisualizacao.getId());
			alterado = false;
		}
		return alterado;

	}

	@Override
	public boolean deletar(int id) {

		// sql = "delete from nivel_visualizacao where id = 2 and descricao 'CLIENTE'";

		sql = "delete from nivel_visualizacao where id = ?'";

		int linhasDeletadas = 0;
		boolean del = false;
		nivelVisualizacao = new NivelVisualizacao();
		try {

			nivelVisualizacao = (NivelVisualizacao) buscaPorId(id);
			sql = "delete from nivel_visualizacao where id = " + nivelVisualizacao.getId();

			// Primeiro consulta na base, se encontrar deleta
			if (nivelVisualizacao != null & nivelVisualizacao.getId() > 0) {

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

		return false;
	}

	@Override
	public Object buscaPorId(int id) {

		sql = "select *from nivel_visualizacao where id = ?";
		try {
			ps = con.getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				nivelVisualizacao = new NivelVisualizacao();
				nivelVisualizacao.setId(rs.getInt(1));
				nivelVisualizacao.setDescricao(rs.getString(2));

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

		return nivelVisualizacao;
	}

	@Override
	public List<Object> buscarPorNome(String nome) {
		// TODO Auto-generated method stub
		sql = "select *from nivel_visualizacao where descricao like '%" + nome.toUpperCase() + "%'";

		// lista = new ArrayList<NivelVisualizacao>();
		listaObjeto = new ArrayList<Object>();
		try {
			ps = con.getConnection().prepareStatement(sql);
			// ps.setString(1, nome.toUpperCase());
			rs = ps.executeQuery();

			while (rs.next()) {
				nivelVisualizacao = new NivelVisualizacao();
				nivelVisualizacao.setId(rs.getInt(1));
				nivelVisualizacao.setDescricao(rs.getString(2));
				listaObjeto.add(nivelVisualizacao);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaObjeto;
	}

	@Override
	public List<Object> listar() {
		// TODO Auto-generated method stub
		sql = "select *from nivel_visualizacao";

		// lista = new ArrayList<NivelVisualizacao>();
		listaObjeto = new ArrayList<Object>();
		try {
			ps = con.getConnection().prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				nivelVisualizacao = new NivelVisualizacao();
				nivelVisualizacao.setId(rs.getInt(1));
				nivelVisualizacao.setDescricao(rs.getString(2));
				listaObjeto.add(nivelVisualizacao);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaObjeto;

	}

	@Override
	public Integer buscaUltimoIdIncluso() {
		sql = "select max(id) from nivel_visualizacao";
		Integer ultimoIdNaBase = 0;
		try {
			ps = con.getConnection().prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next()) {
				nivelVisualizacao = new NivelVisualizacao();
				ultimoIdNaBase = rs.getInt(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ultimoIdNaBase;
	}

}
