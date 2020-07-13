package net.tis.day06;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class DBTest  {
//  Connection CN ; //DB서버정보및 user/pwd기억, CN참조해서 명령어생성
//  Statement ST ;  //정적인명령어 ST=CN.createStatement(X);
//  ResultSet RS ;  //조회한결과기억 RS=ST.executeQuery(select~),RS.next()
//  String msg="insert" ;  쿼리문 기억하는 문자열 
  
  

	public static void main(String[] args) {
		Connection CN; //DB서버정보및 user/pwd기억, CN참조해서 명령어생성
		Statement ST;  //정적인명령어 ST=CN.createStatement(X);
		 try{
	     Class.forName("oracle.jdbc.driver.OracleDriver"); //드라이브로드
	     String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
	     CN=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE","system","1234");
	     System.out.println("오라클연결성공success 월요일");
	   
	   //Scanner sc = new Scanner(System.in);
	   //SQL> delete from test where code > 6600 ; 
	   //SQL> commit ;
		 ST=CN.createStatement(); 
		 String msg= "insert into test values(8800, 'LG', 'LB' ,sysdate, 7)";
		 int OK = ST.executeUpdate(msg);
		 if(OK>0) { 
			  System.out.println("test테이블 저장성공success");
      	//저장성공후 전체출력
     	  System.out.println("=============================");
			}
		 }catch(Exception ex){System.out.println("에러: "+ ex.toString() );   }
	}//main end
}// class END
