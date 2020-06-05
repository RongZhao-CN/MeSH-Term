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
public class TimeServlet extends HttpServlet{
	private DatabaseOperation dbop;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		dbop=new DatabaseOperation();
		TreeMap<String,Integer> mp=dbop.getTimeInfo1();
		String term=request.getParameter("input");
		request.setAttribute("parameter", term);
		List y=new ArrayList();
		List data=new ArrayList();
		
		mp.forEach((key,value)->{
			try {
				y.add(key);
				data.add(value);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		});
		request.setAttribute("year", y);
		request.setAttribute("data", data);
//		request.setAttribute("termlist", termlist);
		request.getRequestDispatcher("year.jsp").forward(request, response);
	}
	
}
