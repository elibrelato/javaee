package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import model.JavaBeans;

/**
 * Servlet implementation class Controller.
 */
@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/findById", "/update", "/delete", "/report"})
public class Controller extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The dao. */
	DAO dao = new DAO();
	
	/** The contato. */
	JavaBeans contato = new JavaBeans();

	/**
	 * Instantiates a new controller.
	 *
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
	}
	
	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// response.getWriter().append("Served at: ").append(request.getContextPath());

//		// testa a conexao com o banco de dados
//		dao.testarConexao();

		String action = request.getServletPath();
		
		System.out.println(action);
		
		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")) {
			adicionarContato(request, response);
		} else if (action.equals("/findById")) {
			listarContato(request, response);
		} else if (action.equals("/update")) {
			editarContato(request, response);
		} else if (action.equals("/delete")) {
			excluirContato(request, response);
		} else if (action.equals("/report")) {
			gerarRelatorio(request, response);
		} else {
			response.sendRedirect("index.html");
		}
	}

	/**
	 * Pega a lista de contatos do banco de dados e devolve essa lista a classe 'agenda.jsp'
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void contatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// cria um ArrayList que ira receber os dados dos contatos
		ArrayList<JavaBeans> lista = dao.listarContatos();

//		// testa se estamos recebendo corretamente os contatos da classe DAO
//		System.out.println("Lista de Contatos (Servlet):");
//		lista.forEach(contato -> {
//			System.out.println(contato.toString());
//		});

		// encaminha a lista de contatos para o documento 'agenda.jsp'
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
	}

	/**
	 * Adicionar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void adicionarContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// imprime os dados do formulario recebido
		logarParametrosDoRequest(request, "Insert->");

		// seta as variaveis
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));

		// persiste os dados do contato no banco de dados
		dao.inserirContato(contato);

		// redireciona para o documento 'agenda.jsp'
		// usando dessa forma (redirect para "main" sem '/', o servlet vai processar novamente o metodo contatos() e trazer a lista atualizada.
		response.sendRedirect("main");
	}

	/**
	 * Listar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void listarContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// String idcon = request.getParameter("idcon"); // setado no agenda.jsp
		// System.out.println("idcon=" + idcon);
		
		contato.setIdcon(request.getParameter("idcon"));
		dao.findById(contato);
		
		// loga os dados do contato
		System.out.println("findById-> " + contato.toString());
		
		// seta os atributos do formulario com o conteudo JavaBeans
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		
		// encaminha para o documento  'editar.jsp'
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
	}
	
	/**
	 * Editar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void editarContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// loga os dados do formulario recebido
		logarParametrosDoRequest(request, "Update->");
		
		// seta as variaveis do contato
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));

		// invoca o metodo alterar contato
		dao.atualizarContato(contato);

		// redireciona para o documento 'agenda.jsp'
		// usando dessa forma (redirect para "main" sem '/', o servlet vai processar novamente o metodo contatos() e trazer a lista atualizada.
		response.sendRedirect("main");
	}
	
	/**
	 * Excluir contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void excluirContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// String idcon = request.getParameter("idcon"); // idcon foi setado no documento agenda.jsp
		// System.out.println("idcon=" + idcon);
		
		contato.setIdcon(request.getParameter("idcon"));
		
		// loga o contato a ser deletado (para confirmar que esta recebendo 'idcon' da classe 'confirmador.js')
		System.out.println("Delete-> " + contato.toString());
		
		dao.excluirContato(contato);
		
		// redireciona para o documento 'agenda.jsp'
		// usando dessa forma (redirect para "main" sem '/', o servlet vai processar novamente o metodo contatos() e trazer a lista atualizada.
		response.sendRedirect("main"); 
	}
	
	/**
	 *  Gerar relatorio em PDF.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Document documento = new Document();
		try {
			// tipo de conteudo
			response.setContentType("application/pdf");
			
			// nome do documento
			response.addHeader("Content-Disposition", "inline; filename=" + "contatos.pdf");
			
			// cria o documento
			PdfWriter.getInstance(documento, response.getOutputStream());
			
			// abre o documento -> conteudo
			documento.open();
			documento.add(new Paragraph("Lista de contatos:"));
			documento.add(new Paragraph(" "));
			
			// cria uma tabela
			PdfPTable tabela = new PdfPTable(3);
			
			// cabecalho da lista
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Telefone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("E-mail"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			
			// popula a tabela com os dados dos contatos
			ArrayList<JavaBeans> lista = dao.listarContatos();
			for (int i = 0; i < lista.size(); i++) {
				tabela.addCell(lista.get(i).getNome());
				tabela.addCell(lista.get(i).getFone());
				tabela.addCell(lista.get(i).getEmail());
			}
			
			// adiciona a tabela de contatos ao documento
			documento.add(tabela);
			documento.addCreationDate();
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			documento.close();
		}
	}
	
	/**
	 * Logar parametros do request.
	 *
	 * @param request the request
	 * @param descricao the descricao
	 */
	private void logarParametrosDoRequest(HttpServletRequest request, String descricao) {
		
//		// java 9+
//		request.getParameterNames().asIterator().forEachRemaining(parameterName -> {
//			System.out.println(parameterName + " = " + request.getParameter(parameterName));
//		});
		
		String s = descricao + " [";
		Enumeration<String> parameters = request.getParameterNames();
		while (parameters.hasMoreElements()) {
			String parameterName = parameters.nextElement();
			String parameterValue = request.getParameter(parameterName);
			s += parameterName + "=" + parameterValue;
			if (parameters.hasMoreElements()) {
				s += ", ";
			}
		}
		s += "]";
		System.out.println(s);
	}
}
