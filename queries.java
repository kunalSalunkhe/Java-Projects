
import java.sql.*;
import java.io.*;

class StudentQueries {

	 String listOfStudents = "select * from Student";

	 String addNewStudent = "insert into Student(id,name,adress) values(?,?,?)";

	 String updateStudentID = "update Student set id = ? where id = ?";

	 String updateStudentName = "update Student set name = ? where id = ?";

	 String updateStudentAddress = "update Student set adress = ? where id = ?";

	 String deleteStudent = "delete from Student where id = ?";

	
	void getStudentList(Connection databaseConnection)throws IOException,SQLException{

		  Statement statementOfList = databaseConnection.createStatement();

                        ResultSet studentTable = statementOfList.executeQuery(listOfStudents);

                        while(studentTable.next()){

                                System.out.print(studentTable.getInt(1)+"    "+studentTable.getString(2)+"    "+studentTable.getString(3));

                                System.out.println();
                        }

                        databaseConnection.close();

	}

	void addStudent(Connection databaseConnection, int id, String name, String address)throws IOException,SQLException{

                   PreparedStatement addStatement = databaseConnection.prepareStatement(addNewStudent);

                                addStatement.setInt(1,id);
                                addStatement.setString(2,name);
                                addStatement.setString(3,address);


                        addStatement.executeUpdate();

                        databaseConnection.close();
	}


  void editStudent(Connection databaseConnection, int editSelected, int newStudentID, BufferedReader getInput)throws IOException,SQLException{

		switch(editSelected){

                        case 1 :

                                System.out.print("enter new ID : ");

                                PreparedStatement editStatement1 = databaseConnection.prepareStatement(updateStudentID);

                                editStatement1.setInt(1,Integer.parseInt(getInput.readLine()));

                                editStatement1.setInt(2,newStudentID);

                                editStatement1.executeUpdate();

                                break;

                        case 2 :

                                System.out.print("enter new name : ");
                             
                                PreparedStatement editStatement2 = databaseConnection.prepareStatement(updateStudentName);

                                editStatement2.setString(1,getInput.readLine());

                                editStatement2.setInt(2,newStudentID);

                                editStatement2.executeUpdate();

                                break;

                        case 3 :

                                System.out.print("enter new adress : ");
                               
                                PreparedStatement editStatement3 = databaseConnection.prepareStatement(updateStudentAddress);

                                editStatement3.setString(1,getInput.readLine());

                                editStatement3.setInt(2,newStudentID);

                                editStatement3.executeUpdate();

                                break;

                        default :

                                System.out.println("wrong choice");
                                break;
                }

                databaseConnection.close();

	}

	void studentDelete(Connection databaseConnection, int studentID)throws IOException, SQLException{

                PreparedStatement deleteStatement = databaseConnection.prepareStatement(deleteStudent);

                deleteStatement.setInt(1,studentID);
                deleteStatement.executeUpdate();

                databaseConnection.close();

	}

}
