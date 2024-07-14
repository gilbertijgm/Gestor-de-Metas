//El servlet CerrarSesionServlet maneja la solicitud GET para cerrar sesión.
//request.getSession(false) intenta obtener la sesión actual del usuario sin crear una nueva sesión si no existe.
//session.invalidate() invalida la sesión actual del usuario, eliminando todos los datos asociados con ella.
//response.sendRedirect("login.jsp") redirige al usuario a la página de inicio de sesión después de cerrar sesión.
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SvCerrarSesion", urlPatterns = {"/SvCerrarSesion"})
public class SvCerrarSesion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // No crea una nueva sesión si no existe
        if (session != null) {
            session.invalidate(); // Invalida la sesión actual
        }
        response.sendRedirect("login.jsp"); // Redirige a la página de inicio de sesión
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
