package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {
	
	/**  Modulo de conexao *. */
	// Parametros de conexao
	private String driver = "com.mysql.cj.jdbc.Driver";
	
	/** The url. */
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	
	/** The user. */
	private String user = "root";
	
	/** The password. */
	private String password = "r007";
	
	/**
	 * Conectar.
	 *
	 * @return the connection
	 */
	// Metodo de conexao
	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			System.out.println(e);
		}
		return con;
	}
	
	/**
	 * Inserir contato.
	 *
	 * @param contato the contato
	 */
	public void inserirContato(JavaBeans contato) {
		String sql = "insert into dbagenda.contatos (nome, fone, email) values (?, ?, ?)";
		try {
			// abre a conexao com o banco de dados
			Connection con = conectar();
			
			// prepara a query para execucao
			PreparedStatement pst = con.prepareStatement(sql);
			
			// substitui os (?) pelo conteudo das variaveis
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.executeUpdate();
			
			// fecha a conexao com o banco de dados
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Listar contatos.
	 *
	 * @return the array list
	 */
	public ArrayList<JavaBeans> listarContatos() {
		ArrayList<JavaBeans> contatos = new ArrayList<>();
		String sql = "select * from dbagenda.contatos order by nome";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				String idcon = rs.getString("idcon");
				String nome = rs.getString("nome");
				String fone = rs.getString("fone");
				String email = rs.getString("email");
				
				// populando o ArrayList
				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}
			con.close();
			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	/**
	 * Find by id.
	 *
	 * @param contato the contato
	 */
	public void findById(JavaBeans contato) {
		String sql = "select * from dbagenda.contatos where idcon = ?";
		try {
			// abre a conexão com o banco de dados
			Connection con = conectar();
			
			// busca os dados do contato
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();
			
			// setar as variaveis do contato
			while (rs.next()) {
				contato.setIdcon(rs.getString("idcon"));
				contato.setNome(rs.getString("nome"));
				contato.setFone(rs.getString("fone"));
				contato.setEmail(rs.getString("email"));
			}
			// fecha coexão com o banco de dados
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Atualizar contato.
	 *
	 * @param contato the contato
	 */
	public void atualizarContato(JavaBeans contato) {
		String sql = "update dbagenda.contatos set nome = ?, fone = ?, email = ? where idcon = ?";
		try {
			// abre a conexão com o banco de dados
			Connection con = conectar();
			
			// prepara a query para execução
			PreparedStatement pst = con.prepareStatement(sql);
			
			// substitui os (?) pelo conteudo das variaveis
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			pst.executeUpdate();
			
			// fecha a conexão com o banco de dados
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	

	/**
	 * Excluir contato.
	 *
	 * @param contato the contato
	 */
	public void excluirContato(JavaBeans contato) {
		String sql = "delete from dbagenda.contatos where idcon = ?";
		try {
			// abrir a conexão
			Connection con = conectar();
			
			// prepara a query para execução
			PreparedStatement pst = con.prepareStatement(sql);
			
			// substitui o (?) pela variavel idcon
			pst.setString(1, contato.getIdcon());
			pst.executeUpdate();
			
			// fecha a conexão com o banco de dados
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Testar conexao com o banco de dados.
	 */
	public void testarConexao() {
		try {
			Connection con = conectar();
			System.out.println(con);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
