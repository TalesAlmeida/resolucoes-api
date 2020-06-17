package br.com.almeida.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.almeida.model.Empresa;
import br.com.almeida.model.NivelVisualizacao;
import br.com.almeida.model.Sistema;
import br.com.almeida.model.ViewSistemasPorEmpresa;
import br.com.almeida.util.Conexao;

public class SistemaDao implements Crud {

	Conexao con = new Conexao();
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = "";
	Sistema sistema;
	List<Sistema> lista = null;
	List<Object> listaObjeto = null;
	ViewSistemasPorEmpresa viewSistema = null;
	List<ViewSistemasPorEmpresa> listaViewSistema = null;

	@Override
	public Integer incluir(Object o) {
		sql = "insert into sistema (id, nome_sistema, id_empresa) values(gen_id(gen_sistema_id,1), ?, ?)";
		sistema = new Sistema();
		int inserido = 0;

		sistema = (Sistema) o;

		try {
			ps = con.getConnection().prepareStatement(sql);
			ps.setString(1, sistema.getNomeSistema().toUpperCase());
			ps.setInt(2, sistema.getIdEmpresa());
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletar(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object buscaPorId(int id) {
		sql = "select *from sistema where id = ?";
		try {
			ps = con.getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				sistema = new Sistema();
				sistema.setId(rs.getInt("id"));
				sistema.setNomeSistema(rs.getString("nome_sistema"));
				sistema.setIdEmpresa(rs.getInt("id_empresa"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sistema;
	}

	@Override
	public List<Object> buscarPorNome(String nome) {
		sql = "select *from sistema where nome_sistema like '%" + nome.toUpperCase() + "%'";

		// lista = new ArrayList<NivelVisualizacao>();
		listaObjeto = new ArrayList<Object>();
		try {
			ps = con.getConnection().prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				sistema = new Sistema();
				sistema.setId(rs.getInt("id"));
				sistema.setNomeSistema(rs.getString("nome_sistema"));
				sistema.setIdEmpresa(rs.getInt("id_empresa"));
				listaObjeto.add(sistema);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaObjeto;
	}

	@Override
	public List<Object> listar() {
		sql = "select *from vsistema_por_empresa";

		// lista = new ArrayList<NivelVisualizacao>();
		listaObjeto = new ArrayList<Object>();
		try {
			ps = con.getConnection().prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				sistema = new Sistema();
				sistema.setId(rs.getInt("id_sistema"));
				sistema.setNomeSistema(rs.getString("nome_sistema"));
				sistema.setIdEmpresa(rs.getInt("id_empresa"));
				//viewSistema.setNomeEmpresa(rs.getString("nome_empresa"));
				listaObjeto.add(sistema);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaObjeto;
	}

	@Override
	public Integer buscaUltimoIdIncluso() {
		sql = "select max(id) from sistema";
		Integer ultimoIdNaBase = 0;
		try {
			ps = con.getConnection().prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next()) {
				sistema = new Sistema();
				ultimoIdNaBase = rs.getInt(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ultimoIdNaBase;
	}

	public List<ViewSistemasPorEmpresa> sistemasPorEmpresa(int id_empresa) {
		sql = "select *from vsistema_por_empresa where id_empresa = ?";

		listaViewSistema = new ArrayList<ViewSistemasPorEmpresa>();
		// listaObjeto = new ArrayList<Object>();
		try {
			ps = con.getConnection().prepareStatement(sql);
			ps.setInt(1, id_empresa);
			rs = ps.executeQuery();

			while (rs.next()) {
				viewSistema = new ViewSistemasPorEmpresa();
				viewSistema.setId(rs.getInt("id_sistema"));
				viewSistema.setNomeSistema(rs.getString("nome_sistema"));
				viewSistema.setIdEmpresa(rs.getInt("id_empresa"));
				viewSistema.setNomeEmpresa(rs.getString("nome_empresa"));
				listaViewSistema.add(viewSistema);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaViewSistema;
	}
}
