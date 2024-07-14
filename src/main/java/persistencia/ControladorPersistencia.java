package persistencia;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import logica.Accion;
import logica.Meta;
import logica.SubAccion;
import logica.User;
import persistencia.exceptions.NonexistentEntityException;

public class ControladorPersistencia {

    UserJpaController userJpa = new UserJpaController();
    MetaJpaController metaJpa = new MetaJpaController();
    AccionJpaController accionJpa = new AccionJpaController();
    SubAccionJpaController subaccionJpa = new SubAccionJpaController();

    void crearUsuario(User usu) {
        userJpa.create(usu);
    }

    List<User> traerUsuarios() {
        return userJpa.findUserEntities();
    }

    void eliminarUser(int id) {
        try {
            userJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladorPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    User traerUser(int id) {
        return userJpa.findUser(id);
    }

    void editarUser(User user) {
        try {
            userJpa.edit(user);
        } catch (Exception ex) {
            Logger.getLogger(ControladorPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    User traerUserPorUsername(String user) {
        EntityManager em = userJpa.getEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.user = :user", User.class);
        query.setParameter("user", user);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    String obtenerRolUsuario(String user) {
        EntityManager em = userJpa.getEntityManager();
        TypedQuery<String> query = em.createQuery("SELECT u.rol FROM User u WHERE u.user = :user", String.class);
        query.setParameter("user", user);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Puedes manejar esto según tus necesidades
        }
    }

    //    ------------------------METASS-------------------------------------
//    void crearMeta(Meta meta) {
//        metaJpa.create(meta);
//    }
    void crearMeta(Meta meta) {
        metaJpa.create(meta);
    }

    User obtenerUsuarioPorNombre(String user) {
        EntityManager em = userJpa.getEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.user = :user", User.class);
        query.setParameter("user", user);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Manejar el caso cuando no se encuentra el usuario
        }
    }

    List<Meta> traerMetas() {
        return metaJpa.findMetaEntities();
    }

    List<Meta> obtenerMetasPorUsuario(User usuario, String rol) {
        EntityManager em = metaJpa.getEntityManager();
        List<Meta> metas;

        try {
            if ("admin".equals(rol)) {
                // Si el usuario es admin, obtiene todas las metas
                TypedQuery<Meta> query = em.createQuery("SELECT m FROM Meta m", Meta.class);
                metas = query.getResultList();
            } else {
                // Si el usuario no es admin, obtiene solo las metas del usuario logueado
                TypedQuery<Meta> query = em.createQuery("SELECT m FROM Meta m WHERE m.user = :user", Meta.class);
                query.setParameter("user", usuario);
                metas = query.getResultList();
            }
        } finally {
            em.close();
        }

        return metas;
    }

    void borrarMeta(int id) {
        try {
            metaJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladorPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Meta traerMeta(int id) {
        return metaJpa.findMeta(id);
    }

    void editarMeta(Meta meta) {
        try {
            metaJpa.edit(meta);
        } catch (Exception ex) {
            Logger.getLogger(ControladorPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    ------------------------ACCIONESS-------------------------------------

    void createAccion(Accion acion) {
        accionJpa.create(acion);
    }

    List<Accion> traerAcciones(int idMeta) {
        EntityManager em = accionJpa.getEntityManager();
        List<Accion> acciones;

        try {
            TypedQuery<Accion> query = em.createQuery("SELECT a FROM Accion a WHERE a.metas.id_meta = :idMeta", Accion.class);
            query.setParameter("idMeta", idMeta);
            acciones = query.getResultList();
        } finally {
            em.close();
        }

        return acciones;
    }

    Accion traerAccion(int idAccion) {
        return accionJpa.findAccion(idAccion);
    }

    void editarAccion(Accion accion) {
        try {
            accionJpa.edit(accion);
        } catch (Exception ex) {
            Logger.getLogger(ControladorPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void eliminarAccion(int idAccion) {
        try {
            accionJpa.destroy(idAccion);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladorPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    List<SubAccion> traerSubacciones(int idAccion) {
        EntityManager em = subaccionJpa.getEntityManager();
        List<SubAccion> subAcciones;

        try {
            TypedQuery<SubAccion> query = em.createQuery("SELECT s FROM SubAccion s WHERE s.accion.id_accion = :idAccion", SubAccion.class);
            query.setParameter("idAccion", idAccion);
            subAcciones = query.getResultList();
        } finally {
            em.close();
        }

        return subAcciones;
    }

    void crearSubaccion(SubAccion subaccion) {
        subaccionJpa.create(subaccion);
    }



}
