<%@page import="controllers.MainController"%>
<div class="row">
    <div class="col-lg-6">

        <h1 class="page-header">
            Inregistrare
        </h1>
        <form role="form" action="index.jsp?p=inregistrare" method="post">
            <div class="form-group">
                <label>Nume</label>
                <input type="text" class="form-control" name="user">                               
            </div>
            <div class="form-group">
                <label>Parola</label>
                <input type="password" class="form-control" name="parola">                               
            </div>
            <div class="form-group">
                <label>Mail</label>
                <input class="form-control">                               
            </div>
            <div class="form-group">
                <label>CNP</label>
                <input class="form-control">                               
            </div>
            <div class="form-group">
                <label>Cod inregistrare</label>
                <input class="form-control">                               
            </div>
            <button type="submit" class="btn btn-default" name="inregistrare">Inregistrare</button>
        </form>
    </div>
</div>

<%
    if (request.getParameter("inregistrare") != null) {
        String user = request.getParameter("user");
        String parola = request.getParameter("parola");
        boolean b = MainController.getInstance().inregistrare(user, parola);
        if (b) {
            out.println("<h3>User Inregistrat<h3>");
        } else {
            out.println("<h3>Eroare User Existent<h3>");
        }
    }
%>