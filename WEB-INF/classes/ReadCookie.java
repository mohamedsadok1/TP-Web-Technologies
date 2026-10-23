import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ReadCookie extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");

        Cookie[] cookies = request.getCookies();

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang='fr'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Lecture des cookies</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Cookies reçus</h1>");

        if (cookies == null || cookies.length == 0) {
            out.println("<p>Aucun cookie trouvé.</p>");
            out.println("<p>Remplis d'abord le formulaire pour en créer un.</p>");
        } else {
            out.println("<table border='1' cellpadding='10'>");
            out.println("<tr><th>N°</th><th>Nom du cookie</th><th>Valeur</th></tr>");

            int numero = 1;
            for (Cookie cookie : cookies) {
                String valeur = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);

                out.println("<tr>");
                out.println("<td>" + numero++ + "</td>");
                out.println("<td>" + escapeHtml(cookie.getName()) + "</td>");
                out.println("<td>" + escapeHtml(valeur) + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
        }

        out.println("<br>");
        out.println("<a href='form.html'>Retour au formulaire</a>");
        out.println("</body>");
        out.println("</html>");
    }

    private String escapeHtml(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}