package br.com.almeida.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.almeida.model.Empresa;
import br.com.almeida.model.NivelVisualizacao;
import br.com.almeida.util.Conexao;

public class EmpresaDao implements Crud {

	Conexao con = new Conexao();
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = "";
	Empresa empresa;
	List<Empresa> lista = null;
	List<Object> listaObjeto = null;

	@Override
	public Integer incluir(Object o) {
		sql = "insert into empresa (id, nome) values(gen_id(gen_empresa_id,1),?)";
		empresa = new Empresa();
		int inserido = 0;

		empresa = (Empresa) o;

		try {
			ps = con.getConnection().prepareStatement(sql);
			// ps.setString(1, "gen_id(gen_empresa_id,1");// usa o genarator do
			// banco
			ps.setString(1, empresa.getNome().toUpperCase());
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
		sql = "select *from empresa where id = ?";
		try {
			ps = con.getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				empresa = new Empresa();
				empresa.setId(rs.getInt(1));
				empresa.setNome(rs.getString(2));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return empresa;
	}

	@Override
	public List<Object> buscarPorNome(String nome) {
		sql = "select *from empresa where nome like '%" + nome.toUpperCase() + "%'";

		// lista = new ArrayList<NivelVisualizacao>();
		listaObjeto = new ArrayList<Object>();
		try {
			ps = con.getConnection().prepareStatement(sql);
			// ps.setString(1, nome.toUpperCase());
			rs = ps.executeQuery();

			while (rs.next()) {
				empresa = new Empresa();
				empresa.setId(rs.getInt(1));
				empresa.setNome(rs.getString(2));
				listaObjeto.add(empresa);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaObjeto;

	}

	@Override
	public List<Object> listar() {
		sql = "select *from empresa";

		// lista = new ArrayList<NivelVisualizacao>();
		listaObjeto = new ArrayList<Object>();
		try {
			ps = con.getConnection().prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				empresa = new Empresa();
				empresa.setId(rs.getInt(1));
				empresa.setNome(rs.getString(2));
				listaObjeto.add(empresa);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaObjeto;
	}

	@Override
	public Integer buscaUltimoIdIncluso() {
		sql = "select max(id) from empresa";
		Integer ultimoIdNaBase = 0;
		try {
			ps = con.getConnection().prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next()) {
				empresa = new Empresa();
				ultimoIdNaBase = rs.getInt(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ultimoIdNaBase;
	}

	
	
	
	
}
