<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.Date" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <title> </title>
 <style type="text/css">
     *{ font-size: 16pt; font-weight: bold; }
	 a{ font-size: 16pt; text-decoration:none; color:blue ; font-family: Comic Sans MS; }
	 a:hover{ font-size: 18pt; text-decoration:underline; color:green ; font-family: Comic Sans MS; }
 </style>
</head>

<body>
 <%!
  Connection CN ; //DB서버정보및 user/pwd기억, CN참조해서 명령어생성
  Statement ST ; //정적인명령어 ST=CN.createStatement(X);
  PreparedStatement PST; //동적인명령어 PST=CN.prepareStatement(sql);
  CallableStatement CS ; //나중에 storedprocedure
  ResultSet RS ;//조회한결과기억 RS=ST.executeQuery(select~),RS.next()
	
  int Grn; //행번호		  
  int Gsabun, Gpay , Ghit; //사번,급여,조회수
  String Gname, Gtitle ; //이름,제목
  java.util.Date Gwdate ; //날짜
  String Gemail; //메일 
  
  int Gdata ; //int data 대신 
  int Atotal = 0 ; //전체조회레코드갯수  316
  int Gtotal = 0 ; //조회레코드갯수
  String msg ; //쿼리문기억하는 문자열 
  
   //guestreply테이블관련 댓글 전역변수
   int Rrn, Rnum, Rsabun;
   String Rwriter, Rcontent ;
 %>

 <%
   //첫번째 오라클드라이브 로드기술
  // try{
     Class.forName("oracle.jdbc.driver.OracleDriver");
     String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
     CN=DriverManager.getConnection(url,"system","1234");
     //System.out.println("오라클연결성공success 11:17 11:52 ");
   //}catch(Exception ex){
	 //System.out.println("에러: "+ ex.toString() ); 
   //}
 %>		
</body>
</html>








