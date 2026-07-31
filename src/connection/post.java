package connection;
import java.sql.*;
public class post {
	private String URL = "jdbc:postgresql://localhost:5432/TopDecor";
	public Connection con;
	public boolean conC() {
		try {
		con = DriverManager.getConnection(URL, "postgres", "");
		return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
