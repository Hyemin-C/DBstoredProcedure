package net.tis.day06;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class DBTest  {
//  Connection CN ; //DB���������� user/pwd���, CN�����ؼ� ��ɾ����
//  Statement ST ;  //�����θ�ɾ� ST=CN.createStatement(X);
//  ResultSet RS ;  //��ȸ�Ѱ����� RS=ST.executeQuery(select~),RS.next()
//  String msg="insert" ;  ������ ����ϴ� ���ڿ� 
  
  

	public static void main(String[] args) {
		Connection CN; //DB���������� user/pwd���, CN�����ؼ� ��ɾ����
		Statement ST;  //�����θ�ɾ� ST=CN.createStatement(X);
		 try{
	     Class.forName("oracle.jdbc.driver.OracleDriver"); //����̺�ε�
	     String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
	     CN=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE","system","1234");
	     System.out.println("����Ŭ���Ἲ��success ������");
	   
	   //Scanner sc = new Scanner(System.in);
	   //SQL> delete from test where code > 6600 ; 
	   //SQL> commit ;
		 ST=CN.createStatement(); 
		 String msg= "insert into test values(8800, 'LG', 'LB' ,sysdate, 7)";
		 int OK = ST.executeUpdate(msg);
		 if(OK>0) { 
			  System.out.println("test���̺� ���强��success");
      	//���强���� ��ü���
     	  System.out.println("=============================");
			}
		 }catch(Exception ex){System.out.println("����: "+ ex.toString() );   }
	}//main end
}// class END
