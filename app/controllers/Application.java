package controllers;

import play.*;
import play.mvc.*;

import java.util.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

import models.*;
import java.util.Random;

public class Application extends Controller {

    public static void index() {
        render();
    }
    
	public static void sayHello(String myName) {

        //render(myName);
		
///////////
	try {
        
		String rand = new Integer(new Random().nextInt()).toString();
		Connection connection = getConnection();
      	Statement stmt = connection.createStatement();

		String query = "insert into public.hellonames (ID, Name) values(" + rand + ","  + myName + ")";
		render(query);
		stmt.executeQuery(query);
	}
		
		//String query = "";
		//int rowID = stmt.executeUpdate("Select count(*) from public.hellonames");
      	//stmt.executeQuery(query);
      	//ResultSet rs = stmt.executeQuery("SELECT * FROM public.hellonames");
		/*

      	String out = "Hello!\n";
	  	while (rs.next()) {
          	out += "Read from DB: " + rs.getString(2) + "\n";
			
      	}
		render(myName);
      
    	} */
		catch (Exception e) {
      	  //resp.getWriter().print("There was an error: " + e.getMessage());
			e.printStackTrace();
    	}
		
		

  }




    
	
    public static Connection getConnection() throws URISyntaxException, SQLException {
    	
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

    	String username = dbUri.getUserInfo().split(":")[0];
    	String password = dbUri.getUserInfo().split(":")[1];
    	String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

    	return DriverManager.getConnection(dbUrl, username, password);
     }

    
}
