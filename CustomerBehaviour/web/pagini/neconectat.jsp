<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% if (request.getParameter("p") != null) {%>
<c:import url="pagini/formularInregistrare.jsp" />
<%} else {%>
<c:import url="pagini/formularConectare.jsp" />
<%}%>