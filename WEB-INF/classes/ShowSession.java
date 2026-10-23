import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class ShowSession extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        Integer visits = (Integer) session.getAttribute("visits");

        String message;
        if (visits == null) {
            visits = 1;
            message = "Welcome on my site";
        } else {
            visits++;
            message = "Welcome back";
        }

        session.setAttribute("visits", visits);

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><meta charset='UTF-8'>");
        out.println("<title>Session</title></head><body>");
        out.println("<h2>" + message + "</h2>");
        out.println("<p>Nombre de visites : <b>" + visits + "</b></p>");
        out.println("</body></html>");
    }
}