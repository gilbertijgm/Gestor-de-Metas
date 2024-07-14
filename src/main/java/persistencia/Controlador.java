package persistencia;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import logica.Accion;

import logica.Meta;
import logica.SubAccion;
import logica.User;
import org.mindrot.jbcrypt.BCrypt;

public class Controlador {

    ControladorPersistencia controlPersis = new ControladorPersistencia();

    public void crearUsuario(String nombreUsu, String apellidoUsu, String telefonoUsu, String usuario, String hashPassword, String rol) {
        User usu = new User();

        usu.setNombre_usu(nombreUsu);
        usu.setApellido_usu(apellidoUsu);
        usu.setTelefono(telefonoUsu);
        usu.setUser(usuario);
        usu.setPassword(hashPassword);
        usu.setRol(rol);

        controlPersis.crearUsuario(usu);
    }

    public List<User> traerUsuarios() {
        return controlPersis.traerUsuarios();
    }

    public void eliminarUser(int id) {
        controlPersis.eliminarUser(id);
    }

    public User traerUser(int id) {
        return controlPersis.traerUser(id);
    }

    public void editarUser(User user) {
        controlPersis.editarUser(user);
    }

    public boolean comprobarIngreso(String user, String pass) {
        User usuario = controlPersis.traerUserPorUsername(user);
        if (usuario != null && BCrypt.checkpw(pass, usuario.getPassword())) {
            return true;
        }
        return false;
    }

    public String obtenerRolUsuario(String user) {
        return controlPersis.obtenerRolUsuario(user);
    }

//    ------------------------METASS-------------------------------------
//    public void crearMeta(String tipoMeta, String nombreMeta, Date fecha_Limite) {
//        Meta meta = new Meta();
//        
//        meta.setTipo(tipoMeta);
//        meta.setNombre_meta(nombreMeta);
//        meta.setFechaLimite(fecha_Limite);
//        
//        controlPersis.crearMeta(meta);
//    }
    public void crearMeta(String tipoMeta, String nombreMeta, Date fecha_Limite, Date fechaActual, User user) {
        Meta meta = new Meta();

        meta.setTipo(tipoMeta);
        meta.setNombre_meta(nombreMeta);
        meta.setFechaInicio(fechaActual);
        meta.setFechaLimite(fecha_Limite);
        meta.setUser(user);

        controlPersis.crearMeta(meta);
    }

    public User obtenerUsuarioPorNombre(String user) {
        return controlPersis.obtenerUsuarioPorNombre(user);

    }

    public List<Meta> traerMetas() {
        return controlPersis.traerMetas();
    }

    public List<Meta> obtenerMetasPorUsuario(User usuario, String rol) {
        return controlPersis.obtenerMetasPorUsuario(usuario, rol);
    }

    public void borrarMeta(int id) {
        controlPersis.borrarMeta(id);
    }

    public Meta traerMeta(int id) {
        return controlPersis.traerMeta(id);
    }

    public void editarMeta(Meta meta) {
        controlPersis.editarMeta(meta);
    }
//    ------------------------ACCIONEES-------------------------------------

    public void crearAccion(String accion, Meta meta) {
        Accion acion = new Accion();

        acion.setNombre(accion);
//        acion.setPorcentajeCumplimiento(porcentaje);
        acion.setMetas(meta);

        controlPersis.createAccion(acion);
    }

    public List<Accion> traerAcciones(int idMeta) {
        return controlPersis.traerAcciones(idMeta);
    }

    public Accion traerAccion(int idAccion) {
        return controlPersis.traerAccion(idAccion);
    }

    public void editarAccion(Accion accion) {
        controlPersis.editarAccion(accion);
    }

    public void eliminarAccion(int idAccion) {
        controlPersis.eliminarAccion(idAccion);
    }

    public List<SubAccion> traerSubacciones(int idAccion) {
        return controlPersis.traerSubacciones(idAccion);
    }

    //------------------------SUBACCCIONES------------------------------------
    public void crearSubaccion(String nombreSubaccion, Boolean completud, Date fechaActual, Accion accion) {
        SubAccion subaccion = new SubAccion();

        subaccion.setNombre_sub(nombreSubaccion);
        subaccion.setCompletada(completud);
        subaccion.setFecha(fechaActual);
        subaccion.setAccion(accion);

        controlPersis.crearSubaccion(subaccion);
    }

    // Método para calcular el porcentaje de cumplimiento de todas las metas de un usuario
    public void calcularPorcentajeCumplimiento(User usuario, String rol) {
        // Obtiene la lista de metas asociadas a un usuario y un rol específico
        List<Meta> metas = obtenerMetasPorUsuario(usuario, rol);

        // Recorre cada meta del usuario
        for (Meta meta : metas) {
            double porcentajeTotalAcciones = 0.0; // Inicializa el porcentaje total de acciones completadas para la meta
            List<Accion> acciones = meta.getAcciones(); // Obtiene la lista de acciones asociadas a la meta
            if (acciones != null && !acciones.isEmpty()) { // Verifica si la lista de acciones no es nula y no está vacía
                for (Accion accion : acciones) { // Recorre cada acción de la meta
                    double porcentajeAccion = 0.0; // Inicializa el porcentaje de cumplimiento de la acción
                    List<SubAccion> subacciones = accion.getSubacciones(); // Obtiene la lista de subacciones asociadas a la acción
                    if (subacciones != null && !subacciones.isEmpty()) { // Verifica si la lista de subacciones no es nula y no está vacía
                        int subaccionesCompletadas = 0; // Inicializa el contador de subacciones completadas
                        for (SubAccion subaccion : subacciones) { // Recorre cada subacción de la acción
                            if (subaccion.isCompletada()) { // Verifica si la subacción está completada
                                subaccionesCompletadas++; // Incrementa el contador de subacciones completadas
                            }
                        }
                        // Calcula el porcentaje de subacciones completadas para la acción
                        porcentajeAccion = (double) subaccionesCompletadas / subacciones.size() * 100.0;
                        System.out.println(porcentajeAccion); // Imprime el porcentaje de subacciones completadas para la acción
                    }
                    // Establece el porcentaje de cumplimiento de la acción
                    accion.setPorcentajeCumplimiento(porcentajeAccion);
                    // Suma el porcentaje de la acción al total de porcentajes de acciones
                    porcentajeTotalAcciones += porcentajeAccion;
                    System.out.println(porcentajeTotalAcciones); // Imprime el porcentaje total de acciones completadas hasta ahora
                }
                // Calcula el porcentaje total de acciones completadas como un promedio de los porcentajes de cada acción
                porcentajeTotalAcciones = porcentajeTotalAcciones / acciones.size();
            }

            // Convertir las fechas de inicio y fin de la meta de Date a LocalDate
            LocalDate fechaInicio = meta.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fechaFin = meta.getFechaLimite().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Calcula el total de días entre la fecha de inicio y la fecha de fin de la meta
            long totalDias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
            // Calcula los días transcurridos desde la fecha de inicio hasta hoy
            long diasTranscurridos = ChronoUnit.DAYS.between(fechaInicio, LocalDate.now());
            // Calcula el porcentaje de tiempo transcurrido
            double porcentajeTiempo = (double) diasTranscurridos / totalDias * 100.0;

            // Calcula el porcentaje total de cumplimiento como una media de los porcentajes de acciones y tiempo
            double porcentajeCumplimientoFinal = (porcentajeTotalAcciones + porcentajeTiempo) / 2;

            // Establece el porcentaje de cumplimiento de la meta
            meta.setPorcentajeCumplimiento(porcentajeCumplimientoFinal);
            // Guarda los cambios de la meta
            editarMeta(meta);
        }
    }

}
