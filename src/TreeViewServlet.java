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
public class TreeViewServlet extends HttpServlet{
	private DatabaseOperation dbop;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		dbop=new DatabaseOperation();
		String term=request.getParameter("input");
		request.setAttribute("parameter", term);
		List<MeSHTreeNode> node=new ArrayList<MeSHTreeNode>();
		
		
		node=dbop.getMeSHTree(term);
		System.out.println(node);
		JSONObject d=new JSONObject();
		JSONObject d1=new JSONObject();
		for(MeSHTreeNode m:node) {
			d=m.getJSONObject();
		}
		
		System.out.println(d1);
		request.setAttribute("data", d);
		
		
		request.getRequestDispatcher("treeview.jsp").forward(request, response);
	}

}
