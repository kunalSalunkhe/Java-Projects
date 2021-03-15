import java.io.*;
import java.sql.*;

class Inventory {

	int selected_from_inventory;

	int stock_id, stock_taken;

	Date date_of_stock_taken;

	static OwnerDatabaseConnection connect = new OwnerDatabaseConnection();

	static Inventory_Queries InventoryQuery = new Inventory_Queries();

	static BufferedReader getInput = new BufferedReader(new InputStreamReader(System.in));

	Inventory()throws IOException, SQLException {

		if((selected_from_inventory = Inventory_Menu()) != 0){

			switch (selected_from_inventory) {

				case 1 :

					Inventory_list();
					break;
				
				case 2 :
		
					System.out.print("Enter stock ID : ");
							
						stock_id = Integer.parseInt(getInput.readLine());

					System.out.print("Enter stock taken : ");
							
						stock_taken = Integer.parseInt(getInput.readLine());

					//System.out.print("Enter Date in yyyy/mm/dd format : ");
							
						//date_of_stock_taken = SimpleDateFormat("yyyy/mm/dd").parse(getInput.readLine());

					Inventory_add(stock_id, stock_taken);

					break;
			}				


		}else
			Owner.terminate = -1;
	}
	
	 static int Inventory_Menu()throws IOException, SQLException{

                System.out.println("Menu");
                System.out.println("------------------------------------------------");
                System.out.println("1.All Inventory Information");
                System.out.println("2.add a Stock");
                System.out.println("-------------------------------------------------");
                System.out.println("select anyone : ");

                int selected_from_inventory =  Integer.parseInt(getInput.readLine());

                if(selected_from_inventory < 1 || selected_from_inventory > 4)

                        return 0;
                else
                        return selected_from_inventory;


        }

        static void Inventory_list()throws IOException,SQLException {

                System.out.println("----------------------------------------------------------");

                System.out.println("Stock_id| Stocks_Taken|Stocks_used|Stock_Remaiming|Total_Stocks|value/stock(Rs.)|Date  ");

                        Connection Inventory_connection = connect.createConnection();

                        InventoryQuery.get_inventory_data(Inventory_connection);

                System.out.println("----------------------------------------------------------");

        }


	void Inventory_add(int stock_id, int stock_taken)throws IOException, SQLException{

		Connection inventory_database = connect.createConnection();

		InventoryQuery.add_new_stock(inventory_database, stock_id, stock_taken);
	
	}

	
	static int updateStocks(int stocks_taken)throws IOException, SQLException{

		Connection inventory_database = connect.createConnection();

		return (InventoryQuery.update_current_stock(inventory_database, stocks_taken));
	}

}

class Customer {

	int selected_from_customers;

	int customer_id;

	String customer_name;

	int amountPaid;

	static OwnerDatabaseConnection connect = new OwnerDatabaseConnection();

	static Customer_Queries CustomerQuery = new Customer_Queries();
	
	static BufferedReader getInput = new BufferedReader(new InputStreamReader(System.in));

	Customer()throws IOException,SQLException {

		if((selected_from_customers = Customer_Menu()) != 0) {	
		
			switch(selected_from_customers) {

				case 1 :

					Customers_list();
					break;

				case 2 :

					System.out.println("Enter Customer ID : ");
		
						int new_customer_id = Integer.parseInt(getInput.readLine());

					System.out.println("Enter Customer name : ");
		
						String new_customer_name = getInput.readLine();

					System.out.println("Enter stocks taken : ");
		
						int new_stocks_taken = Integer.parseInt(getInput.readLine());

				
						if((Inventory.updateStocks(new_stocks_taken)) == -1){
								System.out.println("Stock unavailable, try after some time");
								Owner.terminate = -1;
								break;
						}

					
			int new_total_amount = Inventory.InventoryQuery.accessInventory_to_get_totalAmount(new_stocks_taken);
		
					System.out.println("Total amount : " +new_total_amount+ " Rs.");

					System.out.println("Enter Amount to Pay : ");
		
						int new_amount_paid = Integer.parseInt(getInput.readLine());

					int new_remaining_amount = new_total_amount - new_amount_paid;

				 
		addNewCustomer(new_customer_id, new_customer_name, new_stocks_taken, new_total_amount, new_amount_paid, new_remaining_amount);			
				break;		 

			case 3 :

				System.out.print("enter customerID to edit : ");
		
					customer_id = Integer.parseInt(getInput.readLine());

				System.out.print("enter what to edit : 1.customer Name  2.StocksTaken  3.Amount paid : ");
		
					int selectedFromEdit = Integer.parseInt(getInput.readLine());

				editCustomer(customer_id, selectedFromEdit);

				break;

			

			case 4 :

				System.out.print("Enter CustomerID to delete : ");

					customer_id = Integer.parseInt(getInput.readLine());	

				deleteCustomer(customer_id);
			
				break;
			}
		
		} else
			Owner.terminate = -1;

   	}

	
	static int Customer_Menu() throws IOException {

		System.out.println("Menu");
		System.out.println("------------------------------------------------");
		System.out.println("1.All Customers Information");
		System.out.println("2.add a Customer");
		System.out.println("3.edit customer");
		System.out.println("4.delete customer");
		
		System.out.println("-------------------------------------------------");
		System.out.println("select anyone : ");
	
		int selected_from_customers =  Integer.parseInt(getInput.readLine());
	
		if(selected_from_customers < 1 || selected_from_customers > 4)
		
			 return 0;
		else
			return selected_from_customers;
			
	}

	static void Customers_list()throws IOException,SQLException{

		System.out.println("----------------------------------------------------------");

		System.out.println("Customer_id|    Name      | Stocks_Taken| Total_amount(Rs.)| Paid(Rs.)| Remaiming(Rs.)|");      
		
			Connection Customer_connection = connect.createConnection();

			CustomerQuery.get_customer_data(Customer_connection);

		System.out.println("----------------------------------------------------------");
		
	}

void addNewCustomer(int customerId, String customerName, int stocksTaken, int totalAmount, int amountPaid, int amountRemaining)throws SQLException, IOException{

		Connection customer_database = connect.createConnection();

		CustomerQuery.addCustomer(customer_database, customerId, customerName, stocksTaken, totalAmount, amountPaid, amountRemaining);

	}

	void editCustomer(int customerID, int selectedFromEdit)throws SQLException, IOException {

		Connection customer_database = connect.createConnection();

		CustomerQuery.editInfo(customer_database, customerID, selectedFromEdit, getInput);

		}
	

	void deleteCustomer(int customerId)throws SQLException{

		Connection customer_database = connect.createConnection();

		CustomerQuery.delete_customer(customer_database, customerId);
	}


}

class Owner {

	static int owner_want_info_of;

	static String doAgain;

	static int terminate;

	public static void main(String[] args)throws IOException,SQLException {

		BufferedReader getInput = new BufferedReader(new InputStreamReader(System.in));

		do {
			System.out.println("select to get data : 1.customers   2.inventory");
			
			int owner_want_info_of = Integer.parseInt(getInput.readLine());

			if(owner_want_info_of == 1){

				new Customer();
		
			}
			else if(owner_want_info_of == 2){
			
				new Inventory();
			}
			else{
			
			    System.out.println("wrong selection");
				break;
		       
			}

			if(terminate == -1){
				System.out.println("ended due to wrong selection");
					break;	
			}

	   
	   	 System.out.print("Want to use again yes/no : ");
	 	   doAgain = getInput.readLine();

		}while(doAgain.equals("yes"));

    }
}

