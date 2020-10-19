package mysql_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

public class MySQLAccess {

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	static String databaseName = "exercise2";
	static String url = "jdbc:mysql://localhost:3306/" + databaseName+ "?useSSL = false&useUnicode=true&useJDBCCompliantTimezoneShift=true"
			+ "&useLegacyDatetimeCode=false&clientTimezone=Asia/Jerusalem";

	final private String user = "root";
	final private String passwd = "<your mysql password>";

	public void readDataBase(int doctor_id) throws Exception {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection(url, user, passwd);
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT queue.p_f_name, p_l_name, queue.time \r\n"
					+ "FROM queue join patients join queue_reserved\r\n"
					+ "WHERE queue_reserved.patient_id = patients.patient_id and queue.p_f_name = patients.p_f_name and doctor_id = "
					+ doctor_id + "\r\n" + "ORDER BY queue.time");
			writeResultSet(resultSet);

		} catch (Exception e) {
			throw e;
		}
			close();

	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
		System.out.println("      First Name    |    Last Name      |     Date");
		System.out.println("____________________|___________________|___________________");
		while (resultSet.next()) {
			String first_name = resultSet.getString("p_f_name");
			String last_name = resultSet.getString("p_l_name");
			Timestamp date = resultSet.getTimestamp("time");
			System.out.print("       " + first_name + "          ");
			System.out.print("      " + last_name + "          ");
			System.out.print("    " + date + "\n");
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
		MySQLAccess dao = new MySQLAccess();
		System.out.println("enter doctor id to see his meeting (1001/1002/1003): \n ");
		Scanner id = new Scanner(System.in);
		int docId = id.nextInt();
		dao.readDataBase(docId);
	}

}
