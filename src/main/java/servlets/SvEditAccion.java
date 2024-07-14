package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logica.Accion;
import persistencia.Controlador;

@WebServlet(name = "SvEditAccion", urlPatterns = {"/SvEditAccion"})
public class SvEditAccion extends HttpServlet {
    Controlador control = new Controlador();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idAccion = Integer.parseInt(request.getParameter("idAccion"));
        
        Accion accion = control.traerAccion(idAccion);
        
        HttpSession misession = request.getSession();
        
        misession.setAttribute("accion", accion);
        response.sendRedirect("editarAccion.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          // Obtener los parámetros de la solicitud
        String nombreAccion = request.getParameter("nombreAccion");
        Double porcentaje = null;

        try {
            porcentaje = Double.parseDouble(request.getParameter("porcentaje"));
        } catch (NumberFormatException e) {
            // Si la conversión falla, el porcentaje será nulo
        }
        
        Accion accion = (Accion) request.getSession().getAttribute("accion");
        
        accion.setNombre(nombreAccion);
        accion.setPorcentajeCumplimiento(porcentaje);
        
        control.editarAccion(accion);
        
        //recupero el idMeta para enviarlo al servlet accion
        int idMeta = accion.getMetas().getId_meta();
        response.sendRedirect("SvAccion?id=" + idMeta);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
