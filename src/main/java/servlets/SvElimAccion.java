
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistencia.Controlador;

@WebServlet(name = "SvElimAccion", urlPatterns = {"/SvElimAccion"})
public class SvElimAccion extends HttpServlet {

    Controlador control = new Controlador();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idAccion = Integer.parseInt(request.getParameter("idAccion"));
        int idMeta = Integer.parseInt(request.getParameter("idMeta"));
        
        control.eliminarAccion(idAccion);
        
        response.sendRedirect("SvAccion?id=" + idMeta);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
