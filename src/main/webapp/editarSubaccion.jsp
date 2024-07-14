<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="componentes/header.jsp"%>
<%@include file="componentes/body1.jsp"%>
<div class="text-center">
    <h1 class="h4 text-gray-900 mb-4">Editar Subaccion</h1>
</div>

<form class="user" action="SvEditSubccion" method="POST">
    <div class="form-group row">

        <div class="col-sm-6 mb-3">
            <label for="nombreSubaccion">Subacción</label>
            <input type="text" class="form-control" id="nombreSubaccion" name="nombreSubaccion" placeholder="Descripción de la subacción">
        </div>

        <div class="col-sm-6 mb-3">
            <label for="completud">Indique completo/incompleto</label>
            <select class="form-control" id="completud" name="completud">
                <option value="">Seleccione</option>
                <option value="true">Completado</option>
                <option value="false">Incompleto</option>
            </select>
        </div>

    </div>

    <button class="btn btn-primary btn-user btn-block" type="submit">
        Guardar
    </button>
</form>
<%@include file="componentes/body2.jsp"%>