<%@page import="java.text.SimpleDateFormat"%>
<%@page import="logica.Meta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="componentes/header.jsp"%>
<%@include file="componentes/body1.jsp"%>
<div class="text-center">
    <h1 class="h4 text-gray-900 mb-4">Editar Meta</h1>
</div>
<%
    Meta meta = (Meta) request.getSession().getAttribute("usuMeta");
    String fecha = (String) request.getSession().getAttribute("fechaLimite");
    
%>
<form class="user" action="SvEditMeta" method="POST">
    <div class="form-group row">
        <div class="col-sm-6 mb-3 mb-sm-0">
            <select class="form-control" id="id_odonto" name="tipoMeta" >
                <option value="">Seleccione tipo de meta</option>
                <option value="Meta Personal" <%= "Meta Personal".equals(meta.getTipo()) ? "selected" : ""%>>Meta Personal</option>
                <option value="Meta Profesional" <%= "Meta Profesional".equals(meta.getTipo()) ? "selected" : ""%>>Meta Profesional</option>
                <option value="Meta Relaciones" <%= "Meta Relaciones".equals(meta.getTipo()) ? "selected" : ""%>>Meta Relaciones</option>
                <option value="Meta Mundo" <%= "Meta Mundo".equals(meta.getTipo()) ? "selected" : ""%>>Meta Mundo</option>
                <option value="Meta Legado" <%= "Meta Legado".equals(meta.getTipo()) ? "selected" : ""%>>Meta Legado</option>
            </select>
        </div>

        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control" id="nombreMeta" name="nombreMeta" placeholder="Nombre de la meta"
                   value="<%=meta.getNombre_meta() %>">

        </div> 

        <div class="col-sm-6 mb-3">
            <label>Fecha límite</label>
            <input type="date" class="form-control" id="fechaLimite" name="fechaLimite" placeholder="Fecha Limite"
                   value="<%=fecha %>">

        </div> 

    </div>

    <button class="btn btn-primary btn-user btn-block" type="submit">
        Guardar
    </button>
</form>
<%@include file="componentes/body2.jsp"%>
