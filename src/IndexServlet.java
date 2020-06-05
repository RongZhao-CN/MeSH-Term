import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexServlet extends HttpServlet{
 
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
         
        try {
        	request.getRequestDispatcher("search.html").forward(request, response);
//            response.getWriter().println("<h1>Hello Servlet!</h1>");
//            response.getWriter().println(new Date().toLocaleString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
     
}