 
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistencia.Controlador;

 
@WebServlet(name = "SvElimUser", urlPatterns = {"/SvElimUser"})
public class SvElimUser extends HttpServlet {
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
        int id = Integer.parseInt(request.getParameter("id"));
        
        control.eliminarUser(id);
        
        response.sendRedirect("SvUser");
    }
 
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
