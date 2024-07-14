<%@page import="logica.Accion"%>
<%@page import="java.util.List"%>
<%@page import="logica.Meta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="componentes/header.jsp"%>
<%@include file="componentes/body1.jsp"%>
<%    
    String error = (String) request.getAttribute("error");
    String success = (String) request.getAttribute("success");

    Meta meta = (Meta) request.getSession().getAttribute("meta");
    List<Accion> listaAccion = (List<Accion>) session.getAttribute("listaAccion");
%>
<% if (error != null) {%>
<div class="alert alert-danger" role="alert">
    <%= error%>
</div>
<% } else if (success != null) {%>
<div class="alert alert-success" role="alert">
    <%= success%>
</div>
<% }%>
<!-- Page Heading -->
<div class="d-sm-flex align-items-center justify-content-between mb-4">
    <h1 class="h3 mb-0 text-gray-800">Meta: <%=meta.getNombre_meta()%></h1>
    <a href="#" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm" data-toggle="modal" data-target="#crearAccion">
        <i class="fas fa-solid fa-plus fa-sm text-white-50"></i> Establecer Accion</a>
</div>

<div class="container-fluid">


    <!-- DataTales para   -->
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Acciones</h6>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                    <thead>
                        <tr>
                            <th>Accion</th>   
                            <th>Porcentaje de Cumplimiento</th> 
                            <th>Gestion</th>  
                        </tr>
                    </thead>
                    <!--<tfoot>
                        <tr>
                            <th>Tipo Meta</th> 
                            <th>Meta</th>
                            <th>Fecha limite</th>       
                            <th>Porcentaje de Cumpliemiento</th> 
                            <th>Usuario</th> 
                            <th style="width: 210px">Acciones</th> 
                        </tr>
                    </tfoot>-->

                    <tbody>
                        <%
                            if (listaAccion != null) {
                                for (Accion accion : listaAccion) {
                        %>
                        <tr>
                            <th><%=accion.getNombre()%></th>   
                            <th><%=accion.getPorcentajeCumplimiento()%></th> 

                            <td style="display: flex;">
                                <form name="ver" action="SvSubaccion" method="GET">    
                                    <button type="submit" class="btn btn-primary btn-user btn-block" style="background-color:green;margin-right:5px;">
                                        Ver
                                    </button>
                                    <input type="hidden" name="id" value="<%=accion.getId_accion()%>">
                                </form>
                                <form name="editar" action="SvEditAccion" method="GET">    
                                    <button type="submit" class="btn btn-primary btn-user btn-block" style="margin-left:5px;">
                                        <i class="fas fa-pencil-alt"></i> 
                                    </button>
                                    <input type="hidden" name="idAccion" value="<%=accion.getId_accion()%>">
                                </form>
                                <form name="eliminar" action="SvElimAccion" method="POST">    
                                    <button type="submit" class="btn btn-primary btn-user btn-block" style="background-color:red;margin-left:5px;">
                                        <i class="fas fa-trash-alt"></i> 
                                    </button>
                                    <input type="hidden" name="idAccion" value="<%=accion.getId_accion()%>">
                                    <input type="hidden" name="idMeta" value="<%=accion.getMetas().getId_meta() %>">
                                </form>

                            </td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="2">No hay acciones registradas para esta meta</td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Logout Modal Para crear metas-->
<div class="modal fade" id="crearAccion" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title" id="exampleModalLabel">Establecer Accion</h5>
                <button class="close text-white" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <form class="user" action="SvAccion" method="POST">
                <div class="modal-body">

                    <div class="form-group">
                        <input type="text" class="form-control" id="nombreAccion" name="nombreAccion" placeholder="Descripcion de la meta">
                    </div>

                    <div class="form-group">
                        <label>Porcentaje a dedicar basado en el 100%</label>
                        <input type="number" class="form-control" id="porcentaje" name="porcentaje" placeholder="Porcentaje">
                    </div>
                </div>

                <div class="modal-footer">
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancelar</button>
                    <button class="btn btn-primary" type="submit">Guardar</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- jQuery Script -->

<script>
    $(document).ready(function () {
        $('#crearAccion').on('hidden.bs.modal', function () {
            $(this).find('form')[0].reset();
        });


    });
</script>
<%@include file="componentes/body2.jsp"%>