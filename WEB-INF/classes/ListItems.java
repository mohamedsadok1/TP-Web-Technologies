import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ListItems extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        afficher(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String newItem = request.getParameter("newItem");

        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        ArrayList<String> items = (ArrayList<String>) session.getAttribute("items");
        if (items == null) {
            items = new ArrayList<>();
            session.setAttribute("items", items);
        }
        if (newItem != null && !newItem.trim().isEmpty()) {
            items.add(newItem.trim());
        }
        afficher(request, response);
    }

    private void afficher(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        ArrayList<String> items = (ArrayList<String>) session.getAttribute("items");
        if (items == null) {
            items = new ArrayList<>();
        }

        // Extra : compter les occurrences de chaque item
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (String s : items) {
            counts.merge(s, 1, Integer::sum);
        }

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html lang='fr'><head><meta charset='UTF-8'>");
        out.println("<title>Liste des items</title></head><body>");
        out.println("<h2>Items en session (" + items.size() + ")</h2>");

        if (items.isEmpty()) {
            out.println("<p>Aucun item pour le moment.</p>");
        } else {
            out.println("<ul>");
            for (String s : items) {
                out.println("<li>" + escapeHtml(s) + "</li>");
            }
            out.println("</ul>");

            out.println("<h3>Nombre de saisies par item</h3>");
            out.println("<table border='1' cellpadding='8'>");
            out.println("<tr><th>Item</th><th>Fois</th></tr>");
            for (Map.Entry<String, Integer> e : counts.entrySet()) {
                out.println("<tr><td>" + escapeHtml(e.getKey()) + "</td><td>"
                        + e.getValue() + "</td></tr>");
            }
            out.println("</table>");
        }
        out.println("<p><a href='items.html'>Ajouter un item</a></p>");
        out.println("</body></html>");
    }

    private String escapeHtml(String v) {
        if (v == null) return "";
        return v.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
}