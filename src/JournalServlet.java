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
public class JournalServlet extends HttpServlet{
	private DatabaseOperation dbop;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		dbop=new DatabaseOperation();
		Map<String,Integer> mp=dbop.getTimeInfo2();
		String term=request.getParameter("input");
		request.setAttribute("parameter", term);
		List<List> data=new ArrayList<List>();
		List l=new ArrayList();
		l.add("'ImpactFactor'");
		l.add("'amount'");
		l.add("'journal'");
		data.add(l);
		int max=50;
		int min=0;
		mp.forEach((key,value)->{
			try {
				List li=new ArrayList();
				li.add((int)(Math.random()*(max-min)+min));
				
				li.add(value);
				key=key.replaceAll("'", " ");
					
				
				li.add("'"+key+"'");
//				y.add(key);
				data.add(li);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		});

		request.setAttribute("data", data);
		request.getRequestDispatcher("journal.jsp").forward(request, response);
	}

}
