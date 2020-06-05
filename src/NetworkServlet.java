import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import Model.*;
import DataFormat.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
public class NetworkServlet extends HttpServlet{
	private DatabaseOperation dbop;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		dbop=new DatabaseOperation();
		String term=request.getParameter("input");
		request.setAttribute("parameter", term);
		
		//termnetwork.jsp
		
		request.getRequestDispatcher("termnetwork.jsp").forward(request, response);
	}
}