package br.com.almeida.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.almeida.dao.EmpresaDao;
import br.com.almeida.model.Empresa;
import br.com.almeida.model.NivelVisualizacao;

@Path("empresa/")

public class EmpesaController {

	EmpresaDao empDao = new EmpresaDao();
	Empresa empresa = null;
	String retorno = null;
	Gson gson = new Gson();
	List<Empresa> lista = null;
	List<Object> listaObjeto = null;

	@GET
	@Path("teste")
	public Response teste() {

		return Response.status(200).entity("teste ok !").build();
	}

	@GET
	@Path("buscaid/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscaId(@PathParam("id") int id) {
		retorno = "";
		if (id > 0) {

			try {

				empresa = new Empresa();
				empresa = (Empresa) empDao.buscaPorId(id);

				if (empresa != null) {

					retorno = gson.toJson(empresa);

				} else {

					retorno = gson.toJson("Não encontrado !");
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		} else {
			retorno = gson.toJson("Digite um número maior que Zero !");
		}

		return Response.status(200).entity(retorno).build();
	}

	@GET
	@Path("listar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar() {
		retorno = "";
		// lista = new ArrayList<NivelVisualizacao>();
		listaObjeto = new ArrayList<Object>();
		listaObjeto = empDao.listar();

		if (listaObjeto.size() > 0) {
			retorno = gson.toJson(listaObjeto);
		} else {
			retorno = "{\"Msg\": Lista vazia !}";
		}

		return Response.status(200).entity(retorno).build();
	}

	@GET
	@Path("buscanome/{nome}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscaNome(@PathParam("nome") String nome) {
		retorno = "";

		if (nome.length() > 3) {
			listaObjeto = empDao.buscarPorNome(nome);

			if (listaObjeto.size() > 0) {

				retorno = gson.toJson(listaObjeto);
			} else {
				retorno = "Não encontrado !";
			}
		} else {
			retorno = "{\"Msg\": Informe um nome com mais de 3 caracteres !}";
		}

		return Response.status(200).entity(retorno).build();

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response incluir(String json) {
		empresa = new Empresa();
		Integer idInserido = null;

		try {

			empresa = gson.fromJson(json, Empresa.class);

			if (empresa.getNome().length() > 3) {
				idInserido = empDao.incluir(empresa); // Após incluir, recebo o novo ID incluso

				retorno = "{\"id\": " + idInserido + "}";
			} else {
				retorno = "{\"Msg\": Não foi possivel inserir. Informe um nome com mais de 3 caracteres !}";
			}

		} catch (Exception e) {
			// TODO: handle exception
			retorno = "{\"id\": Erro ao tentar inerir o registro !}";
			e.printStackTrace();

		}

		return Response.status(200).entity(retorno).build();
	}

}
