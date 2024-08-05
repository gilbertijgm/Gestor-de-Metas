<%@page import="java.text.SimpleDateFormat"%>
<%@page import="logica.SubAccion"%>
<%@page import="java.util.List"%>
<%@page import="logica.Accion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="componentes/header.jsp"%>
<%@include file="componentes/body1.jsp"%>
<%    String error = (String) request.getAttribute("error");
    String success = (String) request.getAttribute("success");
    
    Accion accion = (Accion) request.getSession().getAttribute("accion");
    List<SubAccion> listaSubaccion = (List<SubAccion>) session.getAttribute("listaSubaccion");
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
    <h1 class="h3 mb-0 text-gray-800">Accion:  <%=accion.getNombre() %></h1>
    <a href="#" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm" data-toggle="modal" data-target="#crearSubaccion">
        <i class="fas fa-solid fa-plus fa-sm text-white-50"></i> Establecer Subaccion</a>
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
                            <th>Subaccion</th>   
                            <th>Completada</th> 
                            <th>Fecha</th> 
                            <!--<th>Gestion</th>-->  
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
                            if (listaSubaccion != null) {
                                for (SubAccion suba : listaSubaccion) {
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                String fechaFormateada = sdf.format(suba.getFecha());
                        %>
                        <tr>
                            <th><%=suba.getNombre_sub() %></th>   
                            <th><%= suba.isCompletada() ? "Completo" : "Incompleto" %></th> 
                            <th><%=fechaFormateada %></th> 
                           <!-- <td style="display: flex;">
                                <form name="ver" action="SvSubaccion" method="GET">    
                                    <button type="submit" class="btn btn-primary btn-user btn-block" style="background-color:green;margin-right:5px;">
                                        Ver
                                    </button>
                                    <input type="hidden" name="id" value="">
                                </form>
                                <form name="editar" action="SvEditSubaccion" method="GET">    
                                    <button type="submit" class="btn btn-primary btn-user btn-block" style="margin-left:5px;">
                                        <i class="fas fa-pencil-alt"></i> 
                                    </button>
                                    <input type="hidden" name="idSubaccion" value="">
                                </form>
                                <form name="eliminar" action="SvElimSubaccion" method="POST">    
                                    <button type="submit" class="btn btn-primary btn-user btn-block" style="background-color:red;margin-left:5px;">
                                        <i class="fas fa-trash-alt"></i> 
                                    </button>
                                    <input type="hidden" name="idSubaccion" value="">
                                    <input type="hidden" name="idAccion" value="">
                                </form>

                            </td>--> 
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="2">No hay subacciones registradas para esta meta</td>
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
<div class="modal fade" id="crearSubaccion" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title" id="exampleModalLabel">Establecer Subaccion</h5>
                <button class="close text-white" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <form class="user" action="SvSubaccion" method="POST">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="nombreSubaccion">Subacción</label>
                        <input type="text" class="form-control" id="nombreSubaccion" name="nombreSubaccion" placeholder="Descripción de la subacción">
                    </div>

                    <div class="form-group">
                        <label for="completud">Indique completo/incompleto</label>
                        <select class="form-control" id="completud" name="completud">
                            <option value="">Seleccione</option>
                            <option value="true">Completado</option>
                            <option value="false">Incompleto</option>
                        </select>
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
        $('#crearSubaccion').on('hidden.bs.modal', function () {
            $(this).find('form')[0].reset();
        });


    });
</script>


<%@include file="componentes/body2.jsp"%>