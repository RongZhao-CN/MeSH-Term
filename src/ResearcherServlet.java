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
public class ResearcherServlet extends HttpServlet{
	private DatabaseOperation dbop;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		dbop=new DatabaseOperation();
		String term=request.getParameter("input");
		
		List<List> database=new ArrayList<List>();
		List<List> website=new ArrayList<List>();
		List<List> software=new ArrayList<List>();
		List<Integer> type=new ArrayList();
		int threshold=10000;
		int limit=100000;
		List<Integer> d=new ArrayList();
		List<Integer> w=new ArrayList();
		List<Integer> s=new ArrayList();
		d.add(3);d.add(2);d.add(4);
		w.add(2);w.add(3);w.add(4);
		s.add(4);s.add(2);s.add(3);
		database=dbop.getResearcherMap(threshold,limit,d);
		software=dbop.getResearcherMap(threshold,limit,s);
		website=dbop.getResearcherMap(threshold,limit,w);
		int x=(int)(limit*0.85);
		 
//		int max=100;
//		int min=0;
//		for(int i=0;i<10;i++) {
//			List d=new ArrayList();
//			List w=new ArrayList();
//			List s=new ArrayList();
//			for(int j=0;j<4;j++) {
//				d.add((int)(Math.random()*(max-min)+min));
//				w.add((int)(Math.random()*(max-min)+min));
//				s.add((int)(Math.random()*(max-min)+min));
//			}
//			d.add("'database'");
//			w.add("'website'");
//			s.add("'software'");
//			database.add(d);
//			website.add(w);
//			software.add(s);
//		}
		System.out.println(database);
		request.setAttribute("database", database);
		request.setAttribute("website", website);
		request.setAttribute("software", software);
		request.setAttribute("parameter", term);
		request.setAttribute("x", x);
		request.setAttribute("size_min", threshold);
		request.setAttribute("size_max", limit);
		request.setAttribute("percentage", 15);
		request.getRequestDispatcher("researcher.jsp").forward(request, response);
	}
}
