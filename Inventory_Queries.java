

import java.sql.*;
import java.io.*;

class Inventory_Queries {

	String inventoryList = "select * from Inventory";

	String addStock = "insert into Inventory values(?,?,?,?,?,?,?)";

	String updateInventory_after_new_stock = "update Inventory set stocks_used = ?,stokcs_available = ?,total_stock = ? where stock_id = ?"; 



  String updateInventory_after_new_customer = "update Inventory set stocks_used = ?,stokcs_available = ?, total_Stocks = ? where stock_id = ?";

	static OwnerDatabaseConnection connect = new OwnerDatabaseConnection();

	void get_inventory_data(Connection inventory_database)throws SQLException{

		Statement inventory_Statement = inventory_database.createStatement();		

		ResultSet inventoryTable = inventory_Statement.executeQuery(inventoryList);

		while(inventoryTable.next()){

		try{

			System.out.print(inventoryTable.getInt(1)+"             ");	 //stock_id
			System.out.print(inventoryTable.getInt(2)+"       ");		 //stock_taken
			System.out.print(inventoryTable.getInt(3)+"            ");       //stock_used
			System.out.print(inventoryTable.getInt(4)+"           ");        //stock_available
			System.out.print(inventoryTable.getInt(5)+"            ");       //total_stock
			System.out.print(inventoryTable.getInt(6)+"      ");		 //value/stock	
		        System.out.print(inventoryTable.getDate(7)+"      ");            //date

			

			System.out.println();

		}catch(NullPointerException e){  System.out.println("No Stocks till now"); }

	   }
		inventory_database.close();
	}


   void add_new_stock(Connection inventory_database, int stock_id, int stock_taken)throws IOException, SQLException{

     Statement inventory_statement = inventory_database.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);

                ResultSet InventoryTable = inventory_statement.executeQuery(inventoryList);
                InventoryTable.last();


		PreparedStatement addStockStatement = inventory_database.prepareStatement(addStock);
		addStockStatement.setInt(1,stock_id);
		addStockStatement.setInt(2,stock_taken);
		addStockStatement.setInt(3,0);
		addStockStatement.setInt(4,InventoryTable.getInt(4) + stock_taken);
		addStockStatement.setInt(5,InventoryTable.getInt(5) + stock_taken);
		addStockStatement.setInt(6,50);
	
		addStockStatement.setDate(7,null);
	
		addStockStatement.executeUpdate();
		inventory_database.close();
	}

	int update_current_stock(Connection inventory_database, int stocks_require) throws SQLException{

        Statement InventoryStatement = inventory_database.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet InventoryTable = InventoryStatement.executeQuery(inventoryList);


		InventoryTable.last();

		int stock_id = InventoryTable.getInt(1);


			if(InventoryTable.getInt(4) < stocks_require)	//demand is more than supply, then program will terminate with
				return -1;					//some excuse message.

	
		PreparedStatement updateStockStatement = inventory_database.prepareStatement(updateInventory_after_new_customer);

		updateStockStatement.setInt(1,InventoryTable.getInt(3) + stocks_require);

		updateStockStatement.setInt(2,InventoryTable.getInt(4) - stocks_require);

		updateStockStatement.setInt(3,InventoryTable.getInt(5));

		updateStockStatement.setInt(4,stock_id);

		updateStockStatement.executeUpdate();
	
		inventory_database.close();

		
		return 0;

	}

	 int accessInventory_to_get_totalAmount(int stocks_taken)throws SQLException {

		Connection inventory_database = connect.createConnection();
		
	      Statement InventoryStatement = inventory_database.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		ResultSet InventoryTable = InventoryStatement.executeQuery(inventoryList); 

		InventoryTable.last();

		int totalAmount = stocks_taken*(InventoryTable.getInt(6)); //value per stock * stocks taken.

		inventory_database.close();

		return totalAmount;
		
	}
}










