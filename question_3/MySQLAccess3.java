package mysql_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Scanner;

public class MySQLAccess3 {
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	static String databaseName = "exercise2";
	static String url = "jdbc:mysql://localhost:3306/" + databaseName
			+ "?useSSL = false&useUnicode=true&useJDBCCompliantTimezoneShift=true"
			+ "&useLegacyDatetimeCode=false&clientTimezone=Asia/Jerusalem";

	final private String user = "root";
	final private String passwd = "mg31101994";

	public void readDataBase() throws Exception {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection(url, user, passwd);
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM exercise2.family ORDER BY doctor_id");
			writeResultSet(resultSet);

		} catch (Exception e) {
			throw e;
		}
		close();

	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
		System.out.println("      doctor_id    |    patient_id      |     p_f_name       | p_l_name");
		System.out.println("___________________|____________________|____________________|___________");
		while (resultSet.next()) {
			String d_id = resultSet.getString("doctor_id");
			String p_id = resultSet.getString("patient_id");
			String p_f_name = resultSet.getString("p_f_name");
			String p_l_name = resultSet.getString("p_l_name");
			System.out.print("       " + d_id + "          ");
			System.out.print("      " + p_id + "          ");
			System.out.print("    " + p_f_name + "           ");
			System.out.print("    " + p_l_name + "\n");
		}
	}

// You need to close the resultSet
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

	public static void main(String[] args) throws Exception {
		MySQLAccess3 dao = new MySQLAccess3();
		dao.readDataBase();
	}

}
