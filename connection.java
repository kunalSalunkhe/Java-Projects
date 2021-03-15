


import java.sql.*;

class StudentDatabaseConnection {

	String databaseAddress = "jdbc:mysql://localhost:3306/School";

	String username = "root";

	String userPassword = "123456789";

	Connection createConnection()throws SQLException{
 
		Connection myConnection = DriverManager.getConnection(databaseAddress,username,userPassword);
		
		return myConnection;

	}
}

