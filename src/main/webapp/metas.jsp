<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="logica.Meta"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="componentes/header.jsp"%>
<%@include file="componentes/body1.jsp"%>
<%
    String error = (String) request.getAttribute("error");
    String success = (String) request.getAttribute("success");
    
    List<Meta> listaMeta = (List<Meta>) request.getSession().getAttribute("listaMeta");
    Map<String, List<Meta>> metasPorTipo = new HashMap<>();
    if (listaMeta != null) {
        for (Meta meta : listaMeta) {
            String tipo = meta.getTipo();
            if (!metasPorTipo.containsKey(tipo)) {
                metasPorTipo.put(tipo, new ArrayList<>());
            }
            metasPorTipo.get(tipo).add(meta);
        }
    }
    
   
%>
<% if (error != null) {%>
<div class="alert alert-danger" role="alert">
    <%= error%>
</div>
<% } else if (success != null) {%>
<div class="alert alert-success" role="alert">
    <%= success%>
</div>
<% } %>
<!-- Page Heading -->
<div class="d-sm-flex align-items-center justify-content-between mb-4">
    <h1 class="h3 mb-0 text-gray-800">Metas</h1>
    <a href="#" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm" data-toggle="modal" data-target="#crearMeta">
        <i class="fas fa-solid fa-plus fa-sm text-white-50"></i> Establecer Meta</a>
</div>

<div class="container-fluid">
    <%
        String[] tipos = {"Meta Personal", "Meta Profesional", "Meta Relaciones", "Meta Mundo", "Meta Legado"};
        for (String tipo : tipos) {
            List<Meta> metas = metasPorTipo.get(tipo);
            if (metas != null) {
    %>

    <!-- DataTales para <%= tipo %> -->
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Metas <%= tipo%></h6>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                    <thead>
                        <tr>
                            <th>Tipo Meta</th> 
                            <th>Meta</th>
                            <th>Fecha Inicio</th>   
                            <th>Fecha limite</th>       
                            <th>Porcentaje de Cumplimiento</th> 
                            <th>Usuario</th>
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
                            for (Meta meta : metas) {
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                String fechaInicioFormateada = sdf.format(meta.getFechaInicio());
                                String fechaLimiteFormateada = sdf.format(meta.getFechaLimite());
                        %>
                        <tr>
                            <th><%=meta.getTipo() %></th>
                            <th><%=meta.getNombre_meta() %> </th>     
                            <th><%=fechaInicioFormateada %></th> 
                            <th><%=fechaLimiteFormateada %></th> 
                            <th><%=meta.getPorcentajeCumplimiento() %></th> 
                            <th><%=meta.getUser().getNombre_usu() %></th> 
                            <td style="display: flex; width: 230px">
                                <form name="eliminar" action="SvAccion" method="GET">    
                                    <button type="submit" class="btn btn-primary btn-user btn-block" style="background-color:green;margin-right:5px;">
                                        Ver
                                    </button>
                                    <input type="hidden" name="id" value="<%=meta.getId_meta() %>">
                                </form>
                                <form name="editar" action="SvEditMeta" method="GET">    
                                    <button type="submit" class="btn btn-primary btn-user btn-block" style="margin-left:5px;">
                                        <i class="fas fa-pencil-alt"></i> 
                                    </button>
                                    <input type="hidden" name="id" value="<%=meta.getId_meta() %>">
                                </form>
                                <form name="eliminar" action="SvElimMeta" method="POST">    
                                    <button type="submit" class="btn btn-primary btn-user btn-block" style="background-color:red;margin-left:5px;">
                                        <i class="fas fa-trash-alt"></i> 
                                    </button>
                                    <input type="hidden" name="id" value="<%=meta.getId_meta() %>">
                                </form>
                                
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
<%
        }
    }
%>

<%
    if (listaMeta == null || listaMeta.isEmpty()) {
%>
    <div class="alert alert-warning" role="alert">
        No hay metas registradas.
    </div>
<%
    }
%>
</div>

<!-- Logout Modal Para crear metas-->
<div class="modal fade" id="crearMeta" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title" id="exampleModalLabel">Establecer Meta</h5>
                <button class="close text-white" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <form class="user" action="SvMeta" method="POST">
                <div class="modal-body">
                    <div class="form-group">
                        <select class="form-control" id="id_odonto" name="tipoMeta" >
                            <option value="">Seleccione tipo de meta</option>
                            <option value="Meta Personal">Meta Personal</option>
                            <option value="Meta Profesional">Meta Profesional</option>
                            <option value="Meta Relaciones">Meta Relaciones</option>
                            <option value="Meta Mundo">Meta Mundo</option>
                            <option value="Meta Legado">Meta Legado</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <input type="text" class="form-control" id="nombreMeta" name="nombreMeta" placeholder="Nombre de la meta">
                    </div>

                    <!--<div class="form-group">
                        <input type="number" class="form-control" id="exampleInputEmail" placeholder="Porcentaje">
                    </div>-->
                    <div class="form-group">
                        <label>Fecha límite</label>
                        <input type="date" class="form-control" id="fechaLimite" name="fechaLimite" placeholder="Fecha Limite">
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
    $(document).ready(function() {
        $('#crearMeta').on('hidden.bs.modal', function () {
            $(this).find('form')[0].reset();
        });
        
        
    });
</script>

<%@include file="componentes/body2.jsp"%>
