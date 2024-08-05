
<%@page import="logica.Accion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="componentes/header.jsp"%>
<%@include file="componentes/body1.jsp"%>

<div class="text-center">
    <h1 class="h4 text-gray-900 mb-4">Editar Meta</h1>
</div>
<%
    Accion accion = (Accion) request.getSession().getAttribute("accion");
    
%>
<form class="user" action="SvEditAccion" method="POST">
    <div class="form-group row">
        

        <div class="col-sm-6 mb-3">
            <label>Accion</label>
            <input type="text" class="form-control" id="nombreAccion" name="nombreAccion" placeholder="Accion"
                   value="<%=accion.getNombre() %>">

        </div> 

        <div class="col-sm-6 mb-3">
            <label>Porcentaje %</label>
            <input type="number" class="form-control" id="porcentaje" name="porcentaje" placeholder="Porcentaje"
                   value="<%=accion.getPorcentajeCumplimiento() %>">

        </div> 

    </div>

    <button class="btn btn-primary btn-user btn-block" type="submit">
        Guardar
    </button>
</form>
<%@include file="componentes/body2.jsp"%>
