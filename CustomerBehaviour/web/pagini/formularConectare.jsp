<div class="row">
    <div class="col-lg-6">

        <h1 class="page-header">
            Conectare
        </h1>
        <form role="form" action="index.jsp" method="post">
            <div class="form-group">
                <label>User</label>
                <input type="text" class="form-control" name="user">                               
            </div>
            <div class="form-group">
                <label>Parola</label>
                <input type="password" class="form-control" name="parola">                               
            </div>
            <button type="submit" class="btn btn-default" name="conectare">Conectare</button>
        </form>



    </div>
</div>

<%
    if (request.getParameter("conectare") != null) {
        out.println("<h2>User sau parola gresite</h2>");
    }
%>