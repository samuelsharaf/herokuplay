package controllers;

import play.*;
import play.mvc.*;

import java.util.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }
    public static void sayHello(String myName) {
        render(myName);
    }
	
    private Connection getConnection() throws URISyntaxException, SQLException {
    	URI dbUri = new URI(System.getenv("DATABASE_URL"));

    	String username = dbUri.getUserInfo().split(":")[0];
    	String password = dbUri.getUserInfo().split(":")[1];
    	String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

    	return DriverManager.getConnection(dbUrl, username, password);
     }

    private void showDatabase(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    	try {
      	Connection connection = getConnection();

      Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
      stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
      ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

      String out = "Hello!\n";
      while (rs.next()) {
          out += "Read from DB: " + rs.getTimestamp("tick") + "\n";
      }

      resp.getWriter().print(out);
    } catch (Exception e) {
      resp.getWriter().print("There was an error: " + e.getMessage());
    }
  }
}
