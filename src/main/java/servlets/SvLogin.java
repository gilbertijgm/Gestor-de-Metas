package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logica.User;
import org.mindrot.jbcrypt.BCrypt;
import persistencia.Controlador;

@WebServlet(name = "SvLogin", urlPatterns = {"/SvLogin"})
public class SvLogin extends HttpServlet {

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
        String user = request.getParameter("usuario");
        String password = request.getParameter("password");

        boolean validacion = false;

        validacion = control.comprobarIngreso(user, password);

        if (validacion == true) {
            // Obtener el objeto User desde el controlador
            User usuario = control.obtenerUsuarioPorNombre(user);
            // Verificar si el usuario existe
            if (usuario != null) {
                HttpSession misession = request.getSession(true);
                misession.setAttribute("usuario", usuario);// Almacena el objeto User en la sesión

                String rol = control.obtenerRolUsuario(user);// Método para obtener el rol del usuario
                misession.setAttribute("rol", rol); // Almacena el rol en la sesión

                response.sendRedirect("index.jsp");
            } else {
                response.sendRedirect("loginError.jsp");
            }
        } else {
            response.sendRedirect("loginError.jsp");
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
