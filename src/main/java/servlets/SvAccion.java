package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logica.Accion;
import logica.Meta;
import persistencia.Controlador;

@WebServlet(name = "SvAccion", urlPatterns = {"/SvAccion"})
public class SvAccion extends HttpServlet {

    Controlador control = new Controlador();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession misession = request.getSession();
        
        String idParam = request.getParameter("id");    
        if (idParam == null || idParam.isEmpty()) {
            // Manejar el caso cuando no se proporciona el parámetro id
            System.out.println("no se enctro el parametro id o es nulo");
            return;
        }
        //obtengo la meta segun el id 
        int idMeta = Integer.parseInt(idParam);
        Meta meta = control.traerMeta(idMeta);
        
        //obtengo las acciones segun la meta por su id
        List<Accion> listaAccion = new ArrayList<Accion>();
        listaAccion = control.traerAcciones(idMeta);        
        
        //guardo metas y acciones en la session
        misession.setAttribute("meta", meta);
        misession.setAttribute("listaAccion", listaAccion);
        response.sendRedirect("acciones.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession misession = request.getSession();

        Meta meta = (Meta) misession.getAttribute("meta");
        if (meta == null) {
            // Manejar el caso cuando la Meta no está en la sesión
            request.setAttribute("error", "No se encontró la Meta en la sesión.");
            request.getRequestDispatcher("acciones.jsp").forward(request, response);
            return;
        }
          // Obtener los parámetros de la solicitud
        String accion = request.getParameter("nombreAccion");
        Double porcentaje = null;

        try {
            porcentaje = Double.parseDouble(request.getParameter("porcentaje"));
        } catch (NumberFormatException e) {
            // Si la conversión falla, el porcentaje será nulo
        }

        if (!validarCampos(accion)) {
            // Campos no válidos
            request.setAttribute("error", "Los campos no son válidos o estan vacios. Verifique los datos ingresados.");
            request.getRequestDispatcher("acciones.jsp").forward(request, response);
            return;
        }
        
        control.crearAccion(accion, meta);
        
        response.sendRedirect("SvAccion?id=" + meta.getId_meta());
    }

    private boolean validarCampos(String accion) {
        if (accion == null || accion.isEmpty()) {
            return false;
        }

//        if (porcentaje == null || porcentaje < 0.0 || porcentaje > 100.0) {
//            return false;
//        }

        return true;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
