package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logica.Accion;
import logica.SubAccion;
import persistencia.Controlador;

@WebServlet(name = "SvSubaccion", urlPatterns = {"/SvSubaccion"})
public class SvSubaccion extends HttpServlet {

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
        //obtengo la accion segun id
        int idAccion = Integer.parseInt(idParam);
        Accion accion = control.traerAccion(idAccion);

        //obtengo las acciones segun la meta por su id
        List<SubAccion> listaSubaccion = new ArrayList<SubAccion>();
        listaSubaccion = control.traerSubacciones(idAccion);

        //guardo subacciones y acciones en la session
        misession.setAttribute("accion", accion);
        misession.setAttribute("listaSubaccion", listaSubaccion);
        response.sendRedirect("subacciones.jsp");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession misession = request.getSession();
        
        Accion accion = (Accion) misession.getAttribute("accion");
        if (accion == null) {
            // Manejar el caso cuando la accion no está en la sesión
            request.setAttribute("error", "No se encontró la accion en la sesión.");
            request.getRequestDispatcher("subacciones.jsp").forward(request, response);
            return;
        }
        //resibo los parametros del formulario
        String nombreSubaccion = request.getParameter("nombreSubaccion");
        Boolean completud = Boolean.parseBoolean(request.getParameter("completud"));

        // Obtener la fecha actual
        java.util.Date fechaActual = new java.util.Date();
        
        control.crearSubaccion(nombreSubaccion, completud, fechaActual, accion);
        
        response.sendRedirect("SvSubaccion?id=" + accion.getId_accion());
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
