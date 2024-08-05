<%@page import="logica.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="componentes/header.jsp"%>
<%@include file="componentes/body1.jsp"%>
<div class="text-center">
    <h1 class="h4 text-gray-900 mb-4">Editar Usuario</h1>
</div>
<%
    User usu = (User) request.getSession().getAttribute("usuEditar");
    if (usu == null) {
        // Manejar el caso cuando el usuario no está en la sesión, por ejemplo redirigir a una página de error
        response.sendRedirect("error.jsp");
        return;
    }
%>
<form class="user" action="SvEditarUser" method="POST">
    <div class="form-group row">
        <div class="col-sm-6 mb-3 mb-sm-0">
            <input type="text" class="form-control form-control-user" id="nombre" name="nombreEdit"
                   placeholder="Nombre" value="<%=usu.getNombre_usu()%>">
        </div>

        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user" id="apellido" name="apellidoEdit"
                   placeholder="Apellido" value="<%=usu.getApellido_usu()%>">
        </div> 

        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user" id="telefono" name="telefonoEdit"
                   placeholder="Teléfono" value="<%=usu.getTelefono()%>">
        </div> 

        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user" id="usuario" name="usuarioEdit"
                   placeholder="Usuario" value="<%=usu.getUser()%>">
        </div> 

        <div class="col-sm-6 mb-3">
            <input type="password" class="form-control form-control-user" id="password" name="passwordEdit"
                   placeholder="Contraseña" value="<%=usu.getPassword()%>">
        </div>
       
        <div class="col-sm-6 mb-3">
            <select class="form-control" id="rolEdit" name="rolEdit" >
                <option value="">Seleccione rol</option>
                <option value="admin" <%= "admin".equals(usu.getRol()) ? "selected" : ""%>>Administrador</option>
                <option value="user" <%= "user".equals(usu.getRol()) ? "selected" : ""%>>Usuario</option>
            </select>
        </div>
    </div>

    <button class="btn btn-primary btn-user btn-block" type="submit">
        Guardar
    </button>
</form>
<%@include file="componentes/body2.jsp"%>