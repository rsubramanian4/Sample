package demo.data.time.sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class JDBCConnection {
	
	 private static final String DB_DRIVER = "org.h2.Driver";
	 private static final String DB_CONNECTION = "jdbc:h2:~/test";
	 private static final String DB_USER = "sa";
	 private static final String DB_PASSWORD = "";
	 
	  private static Connection getDBConnection() {
		  
		//   System.setProperty("user.timezone", "UTC");
		  
	        Connection dbConnection = null;
	        try {
	            Class.forName(DB_DRIVER);
	        } catch (ClassNotFoundException e) {
	            System.out.println(e.getMessage());
	        }
	        try {
	            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
	                    DB_PASSWORD);
	            return dbConnection;
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        return dbConnection;
	    }
	  
	  private static void printCurrentTimestamp() {
		  System.currentTimeMillis();
		  String strDateFormat = new SimpleDateFormat("yyyy/MM/dd HH.mm.ss.SSS").format(System.currentTimeMillis());
		  System.out.println(strDateFormat);
	  }
	  
	  private static void convertOffset(Timestamp timestamp) {
		  
		 OffsetDateTime offsetDateTime =  OffsetDateTime.of(timestamp.toLocalDateTime(), ZoneOffset.UTC);
		 System.out.println("inserted at:"+offsetDateTime.format(DateTimeFormatter.ISO_DATE_TIME));
		 
		 OffsetDateTime offsetDateTimeAdded = offsetDateTime.plusSeconds(900);
		 System.out.println("Added:"+offsetDateTimeAdded.format(DateTimeFormatter.ISO_DATE_TIME));
		 
		 OffsetDateTime offsetDateTimeNow = OffsetDateTime.now(ZoneOffset.UTC);
		 System.out.println("Now:"+offsetDateTimeNow.format(DateTimeFormatter.ISO_DATE_TIME)); 
		 
		 System.out.println("insert flag:"+offsetDateTimeNow.isAfter(offsetDateTimeAdded));
		  
		/*  System.out.println("ZoneOffset.systemDefault():"+ZoneOffset.systemDefault());
		  
		 LocalDateTime localDateTime = LocalDateTime.ofInstant(timestamp.toInstant(), ZoneOffset.UTC);
		 System.out.println( "localDateTime formated:" +localDateTime.format(DateTimeFormatter.ISO_DATE_TIME));
		 LocalDateTime localDateTimeAdded = localDateTime.plusSeconds(1200);
		 System.out.println( "localDateTimeAdded formated:" +localDateTimeAdded.format(DateTimeFormatter.ISO_DATE_TIME));
		 
		 LocalDateTime localDateTimeNow = LocalDateTime.now(ZoneOffset.UTC);
		 System.out.println( "localDateTimeNow formated:" +localDateTimeNow.format(DateTimeFormatter.ISO_DATE_TIME));
		 
		System.out.println("Insert Flag :" +localDateTimeAdded.isAfter(localDateTimeNow));*/
		 
		/*OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(timestamp.toInstant(), ZoneOffset.UTC);
		System.out.println( "offsetDateTime formated:" +offsetDateTime.format(DateTimeFormatter.ISO_DATE_TIME));
		
		 OffsetDateTime offsetDateTimeUTC = OffsetDateTime.now(ZoneId.of("UTC"));
		 System.out.println("Current offsetDateTime UTC "+offsetDateTimeUTC.format(DateTimeFormatter.ISO_DATE_TIME));*/
		  
		/*LocalDateTime localDateTimeNow = LocalDateTime.now();
		 System.out.println("localDateTimeNow formated:" +localDateTimeNow.format(DateTimeFormatter.ISO_DATE_TIME))*/;
		 
		// System.out.println(localDateTimeNow.isAfter(localDateTime));
		  
		/*long now= OffsetDateTime.now(ZoneId.of("UTC")).toEpochSecond();
		 
		// OffsetDateTime.now(ZoneId.of("UTC")).toEpochSecond();
		 
		 OffsetDateTime offsetDateTime = OffsetDateTime.now(ZoneId.of("UTC"));
		 System.out.println("Current offsetDateTime UTC "+offsetDateTime.format(DateTimeFormatter.ISO_DATE_TIME));
		
		 
		// OffsetDateTime  offsetDateTime2 = offsetDateTime.plusHours(2);
		 
		 System.out.println(timestamp.after(new Timestamp(now)));
		 
		// System.out.println(offsetDateTime2.isBefore(offsetDateTime));
*/		 
		  
		  
		  
	  }
	  
	  
	  public static void main(String[]args) {
		  
		 // System.setProperty("user.timezone", "UTC");
		  
		//  printCurrentTimestamp();
		  
		   //System.setProperty(key, value)
		  //  Connection connection = getDBConnection();
		   try {
			   //System.out.println(connection.getMetaData());
			 // insert();
			   get();
			  System.out.println("ZoneOffset.systemDefault():"+ZoneOffset.systemDefault());
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		   
		 // printCurrentTimestamp();
			  
	  }
	  
	  private static void insert() throws Exception {
		  
		  Connection connection = null;
		  PreparedStatement insertStmt = null;
		  
		  try {
		  String sql = 
		  "INSERT INTO DEMO_TIMESTAMP (DATA, CURR_TIMESTAMP) VALUES (?, SYSTIMESTAMP)";
		  
		  connection = getDBConnection();
		  insertStmt =  connection.prepareStatement(sql);
		  insertStmt.setString(1,"UTC_NEW");
		  insertStmt.execute();
		  } catch(SQLException s) {
			  s.printStackTrace();
		  }finally {
			  if(insertStmt!=null) {
				  insertStmt.close();
			  }
			  if(connection!=null) {
				  connection.close();
			  }
	  }
	  }
	  
	  private static void get() throws Exception {
		  
		  Connection connection = null;
		  PreparedStatement selectStmt = null;
		  ResultSet rs  =null;
		  try {
		  String sql = 
		  "SELECT DATA, CURR_TIMESTAMP FROM DEMO_TIMESTAMP WHERE DATA=?";
		  
		  connection = getDBConnection();
		  selectStmt =  connection.prepareStatement(sql);
		  selectStmt.setString(1,"UTC_NEW");
		  rs = selectStmt.executeQuery();
		 
		  while (rs.next()) {
			  String data = rs.getString("DATA");
			  Timestamp currTimestamp = rs.getTimestamp("CURR_TIMESTAMP");
			 // String currTimestamp = rs.getString("CURR_TIMESTAMP");

				System.out.println("DATA : " + data);
				System.out.println("CURR_TIMESTAMP : " + currTimestamp);
				convertOffset(currTimestamp);
		  }
		  
		  
		  } catch(SQLException s) {
			  s.printStackTrace();
		  }finally {
			  
			  if(rs!=null) {
				  rs.close();
			  }
			  
			  if(selectStmt!=null) {
				  selectStmt.close();
			  }
			  if(connection!=null) {
				  connection.close();
			  }
	  }
	  }
	  
	  
	  
	

}
