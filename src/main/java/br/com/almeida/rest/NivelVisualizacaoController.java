package br.com.almeida.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.almeida.dao.NivelVisualizacaoDao;
import br.com.almeida.model.NivelVisualizacao;

@Path("NivelVisualizacao/")
public class NivelVisualizacaoController {

	NivelVisualizacaoDao nvd = new NivelVisualizacaoDao();
	NivelVisualizacao nv = null;
	String retorno = null;
	Gson gson = new Gson();
	List<NivelVisualizacao> lista = null;
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

				nv = new NivelVisualizacao();
				nv = (NivelVisualizacao) nvd.buscaPorId(id);

				if (nv != null) {

					retorno = gson.toJson(nv);

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
		listaObjeto = nvd.listar();

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
			listaObjeto = nvd.buscarPorNome(nome);

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

		nv = new NivelVisualizacao();
		Integer idInserido = null;

		try {

			nv = gson.fromJson(json, NivelVisualizacao.class);

			if (nv.getDescricao().length() > 3) {

				idInserido = nvd.incluir(nv); // Após incluir, recebo o novo ID incluso

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

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deletar/{id}")
	public Response deletar(@PathParam("id") int id) {

		try {

			if (nvd.deletar(id)) {
				retorno = "{\"Msg:\": Deletado com sucesso !}";
			} else {
				retorno = "{\"Msg:\": Não foi possível deletar, Verifique se i ID informado está cadastrado !}";
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return Response.status(200).entity(retorno).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("alterar/{id}")
	public Response alterar(String json, @PathParam("id") int id) {

		NivelVisualizacao nvRetorno = new NivelVisualizacao();
		nv = new NivelVisualizacao();
		boolean alterado = false;

		try {

			nv = gson.fromJson(json, NivelVisualizacao.class);
			nv.setId(id);

			if (nvd.alterar(nv)) {
				retorno = "{\"Msg\": Alterado com sucesso !}";

			} else {

				retorno = "{\"Msg\": Não foi possivel alterar, verifique os dados informados !}";

			}

		} catch (Exception e) {
			// TODO: handle exception
			retorno = "{\"id\": Erro ao tentar inerir o registro !}";
			e.printStackTrace();

		}

		return Response.status(200).entity(retorno).build();

	}

}
