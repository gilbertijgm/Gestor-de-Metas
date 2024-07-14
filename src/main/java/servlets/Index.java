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
import logica.Meta;
import logica.User;
import org.json.JSONArray;
import org.json.JSONObject;
import persistencia.Controlador;

@WebServlet(name = "Index", urlPatterns = {"/Index"})
public class Index extends HttpServlet {
    Controlador control = new Controlador();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("usuario");
        String rol = (String) session.getAttribute("rol");

//        if (currentUser == null || rol == null) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuario no autenticado o rol no definido");
//            return;
//        }

        JSONArray jsonUsuarios = new JSONArray();

        try {
            if ("admin".equals(rol)) {
                // Si el usuario es administrador, obtener todos los usuarios
                List<User> usuarios = control.traerUsuarios();

                for (User usuario : usuarios) {
                    if (usuario != null) {
                        agregarDatosUsuario(jsonUsuarios, usuario, usuario.getRol());
                    } else {
                        System.err.println("Usuario nulo encontrado en la lista de usuarios");
                    }
                }
            } else {
                // Si el usuario no es administrador, obtener solo los datos del usuario actual
                agregarDatosUsuario(jsonUsuarios, currentUser, rol);
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(jsonUsuarios);
            out.flush();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar los datos");
            e.printStackTrace();
        }
    }

    private void agregarDatosUsuario(JSONArray jsonUsuarios, User usuario, String rol) {
        if (usuario == null) {
            System.err.println("Usuario es nulo en agregarDatosUsuario");
            return; // Salir si el usuario es nulo
        }

        List<Meta> metas = control.obtenerMetasPorUsuario(usuario, rol);
        List<String> nombresMetas = new ArrayList<>();
        List<Double> porcentajesCumplimiento = new ArrayList<>();

        for (Meta meta : metas) {
            nombresMetas.add(meta.getNombre_meta());
            porcentajesCumplimiento.add(meta.getPorcentajeCumplimiento());
        }

        JSONObject jsonUsuario = new JSONObject();
        jsonUsuario.put("usuario", usuario.getNombre_usu());
        jsonUsuario.put("nombresMetas", nombresMetas);
        jsonUsuario.put("porcentajesCumplimiento", porcentajesCumplimiento);

        jsonUsuarios.put(jsonUsuario);
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
