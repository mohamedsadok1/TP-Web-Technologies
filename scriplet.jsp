<%@ page contentType="text/html; charset=UTF-8" import="java.util.Date" %>
<html><body>
<%
    out.println("<p>Heure actuelle : " + new Date() + "</p>");
    out.println("<p>request.getRemoteHost() : " + request.getRemoteHost() + "</p>");
    out.println("<p>Adresse IP du serveur : " + request.getLocalAddr() + "</p>");
%>
</body></html>