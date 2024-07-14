/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logica.SubAccion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import logica.Accion;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author gilbe
 */
public class AccionJpaController implements Serializable {

    public AccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public AccionJpaController() {
        emf = Persistence.createEntityManagerFactory("metasPU");
    }

    public void create(Accion accion) {
        if (accion.getSubacciones() == null) {
            accion.setSubacciones(new ArrayList<SubAccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SubAccion> attachedSubacciones = new ArrayList<SubAccion>();
            for (SubAccion subaccionesSubAccionToAttach : accion.getSubacciones()) {
                subaccionesSubAccionToAttach = em.getReference(subaccionesSubAccionToAttach.getClass(), subaccionesSubAccionToAttach.getId_subAccion());
                attachedSubacciones.add(subaccionesSubAccionToAttach);
            }
            accion.setSubacciones(attachedSubacciones);
            em.persist(accion);
            for (SubAccion subaccionesSubAccion : accion.getSubacciones()) {
                Accion oldAccionOfSubaccionesSubAccion = subaccionesSubAccion.getAccion();
                subaccionesSubAccion.setAccion(accion);
                subaccionesSubAccion = em.merge(subaccionesSubAccion);
                if (oldAccionOfSubaccionesSubAccion != null) {
                    oldAccionOfSubaccionesSubAccion.getSubacciones().remove(subaccionesSubAccion);
                    oldAccionOfSubaccionesSubAccion = em.merge(oldAccionOfSubaccionesSubAccion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Accion accion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Accion persistentAccion = em.find(Accion.class, accion.getId_accion());
            List<SubAccion> subaccionesOld = persistentAccion.getSubacciones();
            List<SubAccion> subaccionesNew = accion.getSubacciones();
            List<SubAccion> attachedSubaccionesNew = new ArrayList<SubAccion>();
            for (SubAccion subaccionesNewSubAccionToAttach : subaccionesNew) {
                subaccionesNewSubAccionToAttach = em.getReference(subaccionesNewSubAccionToAttach.getClass(), subaccionesNewSubAccionToAttach.getId_subAccion());
                attachedSubaccionesNew.add(subaccionesNewSubAccionToAttach);
            }
            subaccionesNew = attachedSubaccionesNew;
            accion.setSubacciones(subaccionesNew);
            accion = em.merge(accion);
            for (SubAccion subaccionesOldSubAccion : subaccionesOld) {
                if (!subaccionesNew.contains(subaccionesOldSubAccion)) {
                    subaccionesOldSubAccion.setAccion(null);
                    subaccionesOldSubAccion = em.merge(subaccionesOldSubAccion);
                }
            }
            for (SubAccion subaccionesNewSubAccion : subaccionesNew) {
                if (!subaccionesOld.contains(subaccionesNewSubAccion)) {
                    Accion oldAccionOfSubaccionesNewSubAccion = subaccionesNewSubAccion.getAccion();
                    subaccionesNewSubAccion.setAccion(accion);
                    subaccionesNewSubAccion = em.merge(subaccionesNewSubAccion);
                    if (oldAccionOfSubaccionesNewSubAccion != null && !oldAccionOfSubaccionesNewSubAccion.equals(accion)) {
                        oldAccionOfSubaccionesNewSubAccion.getSubacciones().remove(subaccionesNewSubAccion);
                        oldAccionOfSubaccionesNewSubAccion = em.merge(oldAccionOfSubaccionesNewSubAccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = accion.getId_accion();
                if (findAccion(id) == null) {
                    throw new NonexistentEntityException("The accion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Accion accion;
            try {
                accion = em.getReference(Accion.class, id);
                accion.getId_accion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The accion with id " + id + " no longer exists.", enfe);
            }
            List<SubAccion> subacciones = accion.getSubacciones();
            for (SubAccion subaccionesSubAccion : subacciones) {
                subaccionesSubAccion.setAccion(null);
                subaccionesSubAccion = em.merge(subaccionesSubAccion);
            }
            em.remove(accion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Accion> findAccionEntities() {
        return findAccionEntities(true, -1, -1);
    }

    public List<Accion> findAccionEntities(int maxResults, int firstResult) {
        return findAccionEntities(false, maxResults, firstResult);
    }

    private List<Accion> findAccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Accion.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Accion findAccion(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Accion.class, id);
        } finally {
            em.close();
        }
    }

    public int getAccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Accion> rt = cq.from(Accion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
