package br.com.almeida.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

	private static Connection con =null;
	
	public static Connection getConnection() {
		
		try {
			
			Class.forName("org.firebirdsql.jdbc.FBDriver");
			
			con = DriverManager.getConnection(
		               "jdbc:firebirdsql:localhost/3050:D:/database/resolucoes.fdb",
		               "sysdba",
		               "masterkey");
			System.out.println("Conectado !");
			
		} catch (Exception e) {
			System.out.println("Erro na conexão:\n"+e.getMessage());
			e.printStackTrace();
		}
		
		return con;
	}
	
	
}
