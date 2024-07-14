package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logica.Meta;
import logica.User;
import persistencia.Controlador;

@WebServlet(name = "SvMeta", urlPatterns = {"/SvMeta"})
public class SvMeta extends HttpServlet {

    Controlador control = new Controlador();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User usuario = (User) session.getAttribute("usuario");
        String rol = (String) session.getAttribute("rol");
        
        
        control.calcularPorcentajeCumplimiento(usuario,rol);
        
        
        List<Meta> listaMeta = new ArrayList<Meta>();
        
        listaMeta = control.obtenerMetasPorUsuario(usuario, rol);
        
        
        session.setAttribute("listaMeta", listaMeta);

        response.sendRedirect("metas.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener el objeto de sesión
        HttpSession session = request.getSession();

        // Obtener el usuario de la sesión
        User user = (User) session.getAttribute("usuario");

        if (user != null) {
            // Recuperación de parámetros y lógica de negocio aquí
            String tipoMeta = request.getParameter("tipoMeta");
            String nombreMeta = request.getParameter("nombreMeta");
            String fechaLimite = request.getParameter("fechaLimite");

            // Validación de campos
            if (validarCampos(tipoMeta, nombreMeta, fechaLimite)) {
                request.setAttribute("error", "Debes llenar todos los campos");
                request.getRequestDispatcher("metas.jsp").forward(request, response);
                return;
            }

            // Procesamiento de la fecha límite
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaLimiteDate = null;
            try {
                fechaLimiteDate = formatter.parse(fechaLimite);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            
            // Obtener la fecha actual
            java.util.Date fechaActual = new java.util.Date();

            // Crear la meta utilizando el usuario obtenido
            control.crearMeta(tipoMeta, nombreMeta, fechaLimiteDate,fechaActual, user);

            // Redireccionar a la página de metas con mensaje de éxito
            response.sendRedirect("SvMeta?success=Meta creada correctamente");
        } else {
            // Manejo del caso donde el usuario no está en sesión correctamente
            response.sendRedirect("login.jsp"); // Redirigir a la página de inicio de sesión
        }
    }

    // Método para validar campos obligatorios
    private boolean validarCampos(String tipoMeta, String nombreMeta, String fechaLimite) {
        return tipoMeta == null || tipoMeta.isEmpty()
                || nombreMeta == null || nombreMeta.isEmpty()
                || fechaLimite == null || fechaLimite.isEmpty();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
