
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logica.Meta;
import persistencia.Controlador;

@WebServlet(name = "SvEditMeta", urlPatterns = {"/SvEditMeta"})
public class SvEditMeta extends HttpServlet {
     Controlador control = new Controlador();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        
        Meta meta = control.traerMeta(id);
        
        if (meta == null) {
            // Manejar el caso cuando la meta no se encuentra
            response.sendRedirect("error.jsp");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Cambia el formato según tus necesidades
        String fechaFormateada = sdf.format(meta.getFechaLimite());
        
        HttpSession misession = request.getSession();
        misession.setAttribute("fechaLimite", fechaFormateada);
        misession.setAttribute("usuMeta", meta);
        response.sendRedirect("editarMeta.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recuperación de parámetros y lógica de negocio aquí
            String tipoMeta = request.getParameter("tipoMeta");
            String nombreMeta = request.getParameter("nombreMeta");
            String fechaLimite = request.getParameter("fechaLimite");
            
            // Procesamiento de la fecha límite
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaLimiteDate = null;
            try {
                fechaLimiteDate = formatter.parse(fechaLimite);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            
            Meta meta = (Meta) request.getSession().getAttribute("usuMeta");
            meta.setTipo(tipoMeta);
            meta.setNombre_meta(nombreMeta);
            meta.setFechaLimite(fechaLimiteDate);
            
            control.editarMeta(meta);
            
            response.sendRedirect("SvMeta");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
