
import java.io.IOException;
import java.util.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.*;
import DataFormat.*;
public class TermServlet extends HttpServlet{
	private DatabaseOperation dbop;
	public void init(ServletConfig config){
		dbop=new DatabaseOperation();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String term=request.getParameter("input");
		TermContent termInfo=new TermContent();
		termInfo=dbop.getInformation(term);
		int max=0;
		int min=0;
		List<Time> timeInfo=new ArrayList<Time>();
		List<TermContent> termlist=new ArrayList<TermContent>();
		termlist=dbop.getInformationLink(term);
		String limitTIme=termInfo.getDate().split("-")[0];
		
		int l=Integer.parseInt(limitTIme);
		timeInfo=dbop.getTimeInfo(limitTIme);
		for(int i=0;i<timeInfo.size();i++) {
			String s=timeInfo.get(i).getTime();
			int n=Integer.parseInt(s);
			if("1829".compareTo(s)>0)
				max=1000;
			if("1877".compareTo(s)>0)
				max=3000;
			if("1925".compareTo(s)>0)
				max=5000;
			if("1973".compareTo(s)>0)
				max=6500;
			if("2020".compareTo(s)>0)
				max=8500;
			if((n-l)<3)
				max=300;
			timeInfo.get(i).setNum((int)(Math.random()*(max-min)+min));
		}
		request.setAttribute("parameter", term);
		request.setAttribute("termInfo", termInfo);
		request.setAttribute("timeInfo", timeInfo);
		request.setAttribute("termlist", termlist);
		request.getRequestDispatcher("termInfo.jsp").forward(request, response);
	}
	
}
