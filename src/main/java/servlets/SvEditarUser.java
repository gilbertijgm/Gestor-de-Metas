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

@WebServlet(name = "SvEditarUser", urlPatterns = {"/SvEditarUser"})
public class SvEditarUser extends HttpServlet {

    Controlador control = new Controlador();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        User user = control.traerUser(id);

        if (user == null) {
            // Manejar el caso cuando el usuario no se encuentra
            response.sendRedirect("error.jsp");
            return;
        }

        HttpSession misession = request.getSession();
        misession.setAttribute("usuEditar", user);

        response.sendRedirect("editarUser.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombreUsu = request.getParameter("nombreEdit");
        String apellidoUsu = request.getParameter("apellidoEdit");
        String telefonoUsu = request.getParameter("telefonoEdit");
        String usuario = request.getParameter("usuarioEdit");
        String password = request.getParameter("passwordEdit");
        String rol = request.getParameter("rolEdit");

        //hashear la contraseña antes de enviarla al controlador
        String hashPassword = hashPassword(password);

        User user = (User) request.getSession().getAttribute("usuEditar");
        user.setNombre_usu(nombreUsu);
        user.setApellido_usu(apellidoUsu);
        user.setTelefono(telefonoUsu);
        user.setUser(usuario);
        user.setPassword(hashPassword);
        user.setRol(rol);

        control.editarUser(user);

        response.sendRedirect("SvUser");
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
