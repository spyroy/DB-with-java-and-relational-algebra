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

import com.mysql.cj.jdbc.CallableStatement;

public class MySQLAccess2 {
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	static String databaseName = "exercise2";
	static String url = "jdbc:mysql://localhost:3306/" + databaseName + "?useSSL = false&useUnicode=true&useJDBCCompliantTimezoneShift=true"
			+ "&useLegacyDatetimeCode=false&serverTimezone=Asia/Jerusalem";

	final private String user = "root";
	final private String passwd = "<your mysql password>";

	public void readDataBase2(int doctor_id, String p_name) throws Exception {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection(url, user, passwd);
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select queue_reserved.queue_id from queue_reserved join patients\r\n"
					+ "where queue_reserved.patient_id = patients.patient_id and patients.p_f_name = '" + p_name + "'\r\n"
					+ "and doctor_id = " + doctor_id + " and queue_reserved.patient_id = patients.patient_id");
			int queue_id = 0;
			while (resultSet.next()) {
				queue_id = resultSet.getInt("queue_id");
			}
			String query = "{ call update_date(?,?) }";
			ResultSet rs;
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			try (java.sql.CallableStatement stmt = connect.prepareCall(query)) {
				stmt.setInt(1, queue_id);
				stmt.setTimestamp(2, timestamp);
				rs = stmt.executeQuery();
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
			System.out.println(queue_id + " time changed ");

		} catch (Exception e) {
			throw e;
		}
		close();

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
		MySQLAccess2 dao = new MySQLAccess2();
		System.out.println("enter doctor id: \n ");
		Scanner id = new Scanner(System.in);
		int docId = id.nextInt();
		System.out.println("enter patient name: \n ");
		Scanner name = new Scanner(System.in);
		String p_name = name.next();
		dao.readDataBase2(docId, p_name);
	}

}
