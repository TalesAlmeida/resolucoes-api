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
import br.com.almeida.dao.SistemaDao;
import br.com.almeida.model.Empresa;
import br.com.almeida.model.Sistema;
import br.com.almeida.model.ViewSistemasPorEmpresa;

@Path("sistema/")
public class SistemaController {

	SistemaDao sisDao = new SistemaDao();
	Sistema sis = null;
	String retorno = null;
	Gson gson = new Gson();
	List<Sistema> lista = null;
	List<Object> listaObjeto = null;
	List<ViewSistemasPorEmpresa> listaViewSistema = null;

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

				sis = new Sistema();
				sis = (Sistema) sisDao.buscaPorId(id);

				if (sis != null) {

					retorno = gson.toJson(sis);

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
		listaObjeto = sisDao.listar();

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
			listaObjeto = sisDao.buscarPorNome(nome);

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
		sis = new Sistema();
		Integer idInserido = null;

		try {

			sis = gson.fromJson(json, Sistema.class);

			if (sis.getNomeSistema().length() > 3) {
				idInserido = sisDao.incluir(sis); // Após incluir, recebo o novo ID incluso

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

	@GET
	@Path("sistemasporempresa/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sistemasPorEmpresa(@PathParam("id") int idEmpresa) {
		retorno = "";
		listaViewSistema = new ArrayList<ViewSistemasPorEmpresa>();

		listaViewSistema = sisDao.sistemasPorEmpresa(idEmpresa);

		if (listaViewSistema.size() > 0) {
			retorno = gson.toJson(listaViewSistema);
		} else {
			retorno = "{\"Msg\": Lista vazia !}";
		}

		return Response.status(200).entity(retorno).build();
	}

}
