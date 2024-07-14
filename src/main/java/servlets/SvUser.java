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
import logica.User;
import org.mindrot.jbcrypt.BCrypt;
import persistencia.Controlador;

@WebServlet(name = "SvUser", urlPatterns = {"/SvUser"})
public class SvUser extends HttpServlet {

    Controlador control = new Controlador();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> listaUser = new ArrayList<User>();

//        listaUser = control.traerUsuarios();
//
//        HttpSession misession = request.getSession();
//        misession.setAttribute("listaUser", listaUser);
//
//        response.sendRedirect("usuarios.jsp");

        HttpSession session = request.getSession();
        String rol = (String) session.getAttribute("rol");

        // Verifica si el rol del usuario es administrador
        if (rol == null || !rol.equals("admin")) {
            // Si no es administrador, redirige a otra página o muestra un mensaje de error
            response.sendRedirect("error.jsp"); // Puedes crear una página de error personalizada
            return;
        }

        listaUser = control.traerUsuarios();

        session.setAttribute("listaUser", listaUser);

        response.sendRedirect("usuarios.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
             
        String nombreUsu = request.getParameter("nombreUsu");
        String apellidoUsu = request.getParameter("apellidoUsu");
        String telefonoUsu = request.getParameter("telefonoUsu");
        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");
        String rol = request.getParameter("rol");

        // Validar campos obligatorios
        if (camposInvalidos(nombreUsu, apellidoUsu, telefonoUsu, usuario, password)) {
            request.setAttribute("error", "Debes llenar todos los campos del usuario");
            request.getRequestDispatcher("usuarios.jsp").forward(request, response);
            return;
        }

        // Hash de la contraseña
        String hashPassword = hashPassword(password);

        // Crear usuario
        control.crearUsuario(nombreUsu, apellidoUsu, telefonoUsu, usuario, hashPassword, rol);

        // Redireccionar con mensaje de éxito
        response.sendRedirect("SvUser?success=Usuario creado correctamente");
    }

    // Método para validar campos obligatorios
    private boolean camposInvalidos(String nombreUsu, String apellidoUsu, String telefonoUsu, String usuario, String password) {
        return nombreUsu == null || nombreUsu.isEmpty()
                || apellidoUsu == null || apellidoUsu.isEmpty()
                || telefonoUsu == null || telefonoUsu.isEmpty()
                || usuario == null || usuario.isEmpty()
                || password == null || password.isEmpty();
    }

    // Método para hashear la contraseña
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
