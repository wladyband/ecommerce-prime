package br.com.integrator.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.ServletContext;

import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.integrator.dao.DaoGenerico;
import br.com.integrator.entities.Categoria;
import br.com.integrator.entities.Produto;
import br.com.integrator.util.FacesUtils;
import br.com.integrator.util.Redimensiona;

@Controller("produtoController")
@Scope("session")
public class ProdutoController {

	private Produto produto;

	/*
	 * Injetado pelo Spring
	 * 
	 */
	@Resource
	private DaoGenerico<Produto, Integer> produtoDao;

	/*
	 * Injetado pelo Spring
	 * 
	 */
	@Resource
	private DaoGenerico<Categoria, Integer> categoriaDao;

	public void setProdutoDao(DaoGenerico<Produto, Integer> produtoDao) {
		this.produtoDao = produtoDao;
	}

	public DaoGenerico<Produto, Integer> getProdutoDao() {
		return produtoDao;
	}

	public void setCategoriaDao(DaoGenerico<Categoria, Integer> categoriaDao) {
		this.categoriaDao = categoriaDao;
	}

	public DaoGenerico<Categoria, Integer> getCategoriaDao() {
		return categoriaDao;
	}

	// componente de upload do PrimeFaces
	private UploadedFile arquivo;

	private DataModel model;

	private Map<String, Object> categoriaItem = null;

	public ProdutoController() {
		// mostrar erro por não inicializar
		this.produto = new Produto();
		this.produto.setCat(new Categoria());

	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setArquivo(UploadedFile arquivo) {
		this.arquivo = arquivo;
	}

	public UploadedFile getArquivo() {
		return arquivo;
	}

	// preenche o combo com as categorias disponíveis
	// no formulário de cadastro de produtos
	public Map<String, Object> getCategorias() {

		categoriaItem = new LinkedHashMap<String, Object>();

		for (Iterator<?> iter = categoriaDao.todos().iterator(); iter.hasNext();) {
			Categoria c = (Categoria) iter.next();
			categoriaItem.put(c.getCatNome(), c.getId());// label,value
		}

		return categoriaItem;
	}

	public String novoProd() {
		this.produto = new Produto();
		this.produto.setCat(new Categoria());
		this.getCategorias();

		return "formProduto";
	}

	// mostra todos os produtos em um DataTable
	public DataModel getTodos() {
		model = new ListDataModel(produtoDao.todos());
		return model;
	}



	
	// salva o produto
	public String salvar() {

		try {

			FacesContext context = FacesContext.getCurrentInstance();

			// pega informações do contexto para
			// conseguir o caminho físico
			// necessário para fazer o upload de arquivo
			ServletContext sc = (ServletContext) context.getExternalContext()
					.getContext();



			// verifica se há um arquivo para salvar
			if (arquivo != null) {

				// o tipo de arquivo
				String tipoDeArquivo = arquivo.getContentType();			
				
				if (tipoDeArquivo.equals("image/jpeg")
						|| tipoDeArquivo.equals("image/pjpeg")
						|| tipoDeArquivo.equals("image/gif")) {

					InputStream stream = arquivo.getInputstream();

					int fSize = (int) arquivo.getSize();
					byte[] buffer = new byte[(int) fSize];
					String nomeArquivo = this.separaNomeImagem(arquivo
							.getFileName());

					// chama o método que salva o arquivo
					Boolean salvarImagem = this.salvarArquivo(buffer, stream,
							nomeArquivo, fSize, sc.getRealPath("/imagens"));

					if (!salvarImagem) {
						FacesUtils.mensErro("Problema no upload do arquivo");
					}

					// cria um thumb se a imagem foi salva
					if (salvarImagem) {
						// redimensiona a imagem
						Boolean salvarThumb = Redimensiona.redimensionar(
								nomeArquivo, sc.getRealPath("/imagens"), sc
										.getRealPath("/thumbs"), 100, 100, 70);

						if (!salvarThumb) {
							FacesUtils.mensErro("Problema ao salvar o Thumb");
						}

					}// fim do thumb

					produto.setImagem(nomeArquivo);

				}// fim do if tipoDeArquivo
				else {
					FacesUtils.mensErro("É aceito somente JPEG, PJPEG ou GIF.");
				}
			}

			// se não há um id,
			// estamos com um novo registro
			if (produto.getId() == null) {

				produtoDao.salvar(produto);
				FacesUtils.mensInfo("Cadastrado com sucesso");

			} else {
				// pega a categoria escolhida para atualização
				Categoria cat = categoriaDao.pesquisarPorId(produto.getCat()
						.getId());
				// altera
				produto.setCat(cat);
				// atualiza o produto
				produtoDao.atualizar(produto);
				FacesUtils.mensInfo("Atualizado com sucesso");

			}

		} catch (IOException e) {
			FacesUtils.mensErro("Um erro ocorreu");
			e.printStackTrace();
		}

		return "sucesso";

	}

	// salva o arquivo no disco
	public boolean salvarArquivo(byte[] buf, InputStream stream,
			String nomeArquivo, int size, String path)
			throws FileNotFoundException, IOException {

		nomeArquivo = this.separaNomeImagem(nomeArquivo);

		File file = new File(path + "/" + nomeArquivo);

		FileOutputStream output = new FileOutputStream(file);
		try {
			while (true) {
				int count = stream.read(buf, 0, size);
				if (count == -1)
					break;
				output.write(buf, 0, count);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

		finally {
			output.flush();
			output.close();
			stream.close();
		}
		return true;

	}

	// separa o caminho físico do nome do arquivo
	private String separaNomeImagem(String nomeImagem) {

		if (nomeImagem.lastIndexOf("\\") >= -1) {
			nomeImagem = nomeImagem.substring(nomeImagem.lastIndexOf("\\") + 1);
		} else if (nomeImagem.lastIndexOf("/") >= -1) {
			nomeImagem = nomeImagem.substring(nomeImagem.lastIndexOf("/") + 1);
		}

		return nomeImagem;
	}

	public Produto getProdutoParaEditarExcluir() {
		Produto produto = (Produto) model.getRowData();

		return produto;

	}

	public String editar() {
		setProduto(getProdutoParaEditarExcluir());
		return "formProduto";
	}

	public String excluir() {
		Produto produto = getProdutoParaEditarExcluir();

		produtoDao.excluir(produto);

		return "mostrarProdutos";
	}

}