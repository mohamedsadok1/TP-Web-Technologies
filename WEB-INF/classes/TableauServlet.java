import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TableauServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String birthDay = request.getParameter("birthDay");
        String sex = request.getParameter("sex");

        addCookie(request, response, "firstName", firstName);
        addCookie(request, response, "lastName", lastName);
        addCookie(request, response, "birthDay", birthDay);
        addCookie(request, response, "sex", sex);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='fr'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Informations</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Informations de l'utilisateur</h1>");
        out.println("<table border='1' cellpadding='10'>");
        out.println("<tr><th>Prénom</th><th>Nom</th><th>Date de naissance</th><th>Sexe</th></tr>");
        out.println("<tr>");
        out.println("<td>" + escapeHtml(firstName) + "</td>");
        out.println("<td>" + escapeHtml(lastName) + "</td>");
        out.println("<td>" + escapeHtml(birthDay) + "</td>");
        out.println("<td>" + escapeHtml(sex) + "</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("<p><a href='ReadCookie'>Lire les cookies</a></p>");
        out.println("<p><a href='VisitCounter'>Compteur avec cookie</a></p>");
        out.println("<p><a href='ShowSession'>Compteur avec session</a></p>");
        out.println("</body>");
        out.println("</html>");
    }

    private void addCookie(HttpServletRequest request, HttpServletResponse response,
                           String name, String value) {
        String safe = URLEncoder.encode(value == null ? "" : value, StandardCharsets.UTF_8);
        Cookie c = new Cookie(name, safe);
        c.setMaxAge(3600);                        // 1 heure
        c.setPath(request.getContextPath());      // "/Lab1", toujours la bonne casse
        response.addCookie(c);
    }

    private String escapeHtml(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}