


import java.sql.*;
import java.io.*;


class Student {

	static int studentId;

	static String studentName,studentAddress;

	static String doAgain = new String();

	StudentDatabaseConnection connect = new StudentDatabaseConnection();

	StudentQueries query = new StudentQueries();	

	static void menu(){

		System.out.println("-----------------------------------");
		System.out.println("Menu : ");
		System.out.println("1.Get List of students");
		System.out.println("2.add new student");
		System.out.println("3.edit student");
		System.out.println("4.delete student");
		System.out.println("-----------------------------------");
		System.out.print("Enter input : ");
	}

		
	 void list()throws SQLException,IOException{

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("ID	   Name	     Adress");

	           	Connection student_Database_Connection = connect.createConnection();

			query.getStudentList(student_Database_Connection);

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}


	void add(int studentID, String studentName, String studentAddress)throws SQLException, IOException{

	          	Connection student_Database_Connection = connect.createConnection();

			query.addStudent(student_Database_Connection, studentID, studentName, studentAddress);

	}


        void edit(int studentID, BufferedReader getInput)throws SQLException, IOException{

		Connection student_Database_Connection = connect.createConnection();
	
		System.out.println("enter what to edit : 1.id  2.name  3.adress");

		int editSelected = Integer.parseInt(getInput.readLine());

		query.editStudent(student_Database_Connection, editSelected, studentID, getInput);
	
	}


	 void delete(int studentID)throws SQLException, IOException{

                Connection databaseConnection = connect.createConnection();

		query.studentDelete(databaseConnection, studentID);
	}


		                      
	public static void main(String[] args)throws IOException,SQLException{


	Student student1 = new Student();

	BufferedReader getInput = new BufferedReader(new InputStreamReader(System.in));

	do{

		Student.menu();

		int selectedFromMenu = Integer.parseInt(getInput.readLine());

		if(selectedFromMenu < 1 || selectedFromMenu > 4){
			System.out.println("........Ending due to Wrong Selection..........");
			break;
		}

		switch(selectedFromMenu){

			case 1 :
			
 				student1.list();
				break;

			case 2 :

				System.out.print("Enter Student ID : ");

					Student.studentId = Integer.parseInt(getInput.readLine());

				System.out.print("Enter Student name : ");

					Student.studentName = getInput.readLine();

				System.out.print("Enter Student adress : ");

					Student.studentAddress = getInput.readLine();
				
				student1.add(Student.studentId, Student.studentName, studentAddress);

				break;

			case 3 :

				System.out.println("enter student ID to edit");

					Student.studentId = Integer.parseInt(getInput.readLine());
														
				student1.edit(Student.studentId, getInput);
	
				break;


			case 4 :

				System.out.print("enter student ID to delete : ");

				        Student.studentId = Integer.parseInt(getInput.readLine());
		
				student1.delete(Student.studentId);

				break;
		
		}
		
		System.out.print("Want to use again Yes/No : ");

		doAgain = getInput.readLine();

	}while(doAgain.equals("Yes"));

    }

}

		
		
