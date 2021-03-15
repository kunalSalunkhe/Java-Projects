

import java.sql.*;

import java.io.*;

class Customer_Queries {

	String customerList = "select * from Customer";

	String addNewCustomer = "insert into Customer values(?,?,?,?,?,?)";

	String updateCustomer = "select * from Customer where customer_id = ?";

	String updateName = "update Customer set customer_name = ? where customer_id = ?";

	String updateStockTaken = "update Customer set stocks_taken = ? where customer_id = ?";

	String updateAmount = "update Customer set amount_paid = ?, amount_remaining = ? where customer_id = ?";

	String deleteCustomer = "delete from Customer where customer_id = ?";	


	

	void get_customer_data(Connection customer_database)throws SQLException{

		Statement customer_Statement = customer_database.createStatement();		

		ResultSet customerTable = customer_Statement.executeQuery(customerList);

		while(customerTable.next()){


	System.out.print(customerTable.getInt(1)+"      "+customerTable.getString(2)+"             "+customerTable.getInt(3)+"    ");

	System.out.print("      "+customerTable.getInt(4)+"            "+customerTable.getInt(5)+"            "+customerTable.getInt(6));

			System.out.println();


	   }
		customer_database.close();
	}

	void addCustomer(Connection customer_database, int customerId, String customerName, int stocksTaken, int totalAmount, int amountPaid,
				int remainingAmount)throws SQLException {

		PreparedStatement customerStatement = customer_database.prepareStatement(addNewCustomer);

		customerStatement.setInt(1,customerId);
		customerStatement.setString(2,customerName);
		customerStatement.setInt(3,stocksTaken);
		customerStatement.setInt(4,totalAmount);
		customerStatement.setInt(5,amountPaid);
		customerStatement.setInt(6,remainingAmount);


		customerStatement.executeUpdate();

		customer_database.close();
	}

	void editInfo(Connection customer_database, int customerID, int selectedFromEdit, BufferedReader getInput)throws SQLException,IOException{

		switch(selectedFromEdit){

			case 1 :
			
				System.out.print("Enter new Name : ");
				
				PreparedStatement customerEditName = customer_database.prepareStatement(updateName);

				customerEditName.setString(1,getInput.readLine());

				customerEditName.setInt(2,customerID);

				customerEditName.executeUpdate();
	
				break;

			case 2 :

				System.out.print("Enter new stocks taken : ");
				
				PreparedStatement customerEditStock = customer_database.prepareStatement(updateStockTaken);

				customerEditStock.setInt(1,Integer.parseInt(getInput.readLine()));

				customerEditStock.setInt(2,customerID);

				customerEditStock.executeUpdate();
	
				break;

			case 3 :

				System.out.print("Enter new amount : ");

			        int newAmount = Integer.parseInt(getInput.readLine());

				Statement customerStatement = customer_database.createStatement();

		ResultSet customerTable = customerStatement.executeQuery("select * from Customer where customer_id = " +customerID);

				customerTable.next();
				
				PreparedStatement customerEditAmount = customer_database.prepareStatement(updateAmount);

				customerEditAmount.setInt(1,customerTable.getInt(5) + newAmount);

				customerEditAmount.setInt(2,customerTable.getInt(6) - newAmount); 

				customerEditAmount.setInt(3,customerID);

				customerEditAmount.executeUpdate();
	
				break;

		  }

	}

	
	void delete_customer(Connection customer_database, int customerId)throws SQLException{

		PreparedStatement customerDelete = customer_database.prepareStatement(deleteCustomer);

		customerDelete.setInt(1,customerId);

		customerDelete.executeUpdate();

	}

}

