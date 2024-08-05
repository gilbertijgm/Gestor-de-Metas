<%@page import="logica.User"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="componentes/header.jsp"%>
<%@include file="componentes/body1.jsp"%>
<%
    String error = (String) request.getAttribute("error");
    String success = (String) request.getAttribute("success");
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
    <h1 class="h3 mb-0 text-gray-800">Usuarios</h1>
    <a href="#" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm" data-toggle="modal" data-target="#crearUsuario">
        <i class="fas fa-solid fa-plus fa-sm text-white-50"></i> Crear Usuario</a>
</div>

<div class="container-fluid">

    <!-- DataTales Example -->
    <div class="card shadow mb-4">

        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                    <thead>
                        <tr>
                            <th>Nombre</th> 
                            <th>Apellido</th>
                            <th>Telefono</th>       
                            <th>Usuario</th> 
                            <th>Rol</th> 
                            <th>Acciones</th>  
                        </tr>
                    </thead>
                    <tfoot>
                        <tr>
                            <th>Nombre</th> 
                            <th>Apellido</th>
                            <th>Telefono</th>       
                            <th>Usuario</th> 
                            <th>Rol</th>  
                            <th style="width: 210px">Acciones</th> 
                        </tr>
                    </tfoot>
                    
                    <tbody>
                        <%
                            List<User> listaUser = (List<User>) request.getSession().getAttribute("listaUser");
                            if (listaUser != null) {
                                for (User usu : listaUser) {
                        %>
                        <tr>
                            <th><%=usu.getNombre_usu() %></th>
                            <th><%=usu.getApellido_usu() %></th>       
                            <th><%=usu.getTelefono() %></th> 
                            <th><%=usu.getUser() %></th>
                            <th><%= "admin".equals(usu.getRol()) ? "Administrador" : "Usuario" %></th> 
                            <td style="display: flex; width: 230px">
                                <form name="eliminar" action="SvElimUser" method="POST">    
                                    <button type="submit" class="btn btn-primary btn-user btn-block" style="background-color:red;margin-right:5px;">
                                        <i class="fas fa-trash-alt"></i> Eliminar
                                    </button>
                                    <input type="hidden" name="id" value="<%=usu.getId_usuario()%>">
                                </form>
                                <form name="editar" action="SvEditarUser" method="GET">    
                                    <button type="submit" class="btn btn-primary btn-user btn-block" style="margin-right:5px;">
                                        <i class="fas fa-pencil-alt"></i> Editar
                                    </button>
                                    <input type="hidden" name="id" value="<%=usu.getId_usuario()%>">
                                </form>
                            </td>
                        </tr>
                        <%
                            }
                        } else {
                        %>    
                        <tr>
                            <td colspan="5" class="text-center">No hay usuarios registrados</td>
                        </tr>
                        <% }%>
                    </tbody>
                   
                </table>
            </div>
        </div>
    </div>

</div>

<!-- Logout Modal Para crear metas-->
<div class="modal fade" id="crearUsuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title" id="exampleModalLabel">Registrar Usuario</h5>
                <button class="close text-white" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <form class="user" action="SvUser" method="POST">
                <div class="modal-body">
                    <div class="form-group">
                        <input type="text" class="form-control" id="nombre" name="nombreUsu" placeholder="Nombre" required>
                    </div>

                    <div class="form-group">
                        <input type="text" class="form-control" id="apellido" name="apellidoUsu" placeholder="Apellido" required>
                    </div>

                    <div class="form-group">
                        <input type="text" class="form-control" id="telefono" name="telefonoUsu" placeholder="Telefono" required>
                    </div>

                    <div class="form-group">
                        <input type="text" class="form-control" id="usuario" name="usuario" placeholder="Usuario" required>
                    </div>

                    <div class="form-group">
                        <input type="password" class="form-control" id="password"  name="password"placeholder="Password" required>
                    </div>

                    <!-- <div class="form-group">
                         <input type="text" class="form-control" id="rol" name="rol" placeholder="Usuario">
                     </div>-->

                    <div class="form-group">
                        <select class="form-control" id="rol" name="rol" required>
                            <option value="">Seleccione rol</option>
                            <option value="admin">Administrador</option>
                            <option value="user">Usuario</option>
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
    $(document).ready(function() {
        $('#crearUsuario').on('hidden.bs.modal', function () {
            $(this).find('form')[0].reset();
        });
        
        
    });
</script>
<%@include file="componentes/body2.jsp"%>
