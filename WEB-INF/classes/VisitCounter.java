import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class VisitCounter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int count = 0;

        // 1. Lire les cookies envoyés par le navigateur
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("visitCount")) {
                    try {
                        count = Integer.parseInt(c.getValue());
                    } catch (NumberFormatException e) {
                        count = 0; // cookie corrompu : on repart de zéro
                    }
                }
            }
        }

        // 2. Incrémenter
        count++;

        // 3. Renvoyer le cookie mis à jour (AVANT d'écrire la réponse)
        Cookie visit = new Cookie("visitCount", String.valueOf(count));
        visit.setMaxAge(60 * 60 * 24 * 365); // dure 1 an
        response.addCookie(visit);

        // 4. Afficher
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><meta charset='UTF-8'>");
        out.println("<title>Compteur de visites</title></head><body>");
        if (count == 1) {
            out.println("<h2>Bienvenue, c'est votre première visite !</h2>");
        } else {
            out.println("<h2>Content de vous revoir !</h2>");
        }
        out.println("<p>Nombre de visites : <b>" + count + "</b></p>");
        out.println("</body></html>");
    }
}