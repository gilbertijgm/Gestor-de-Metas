/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logica.Accion;
import logica.SubAccion;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author gilbe
 */
public class SubAccionJpaController implements Serializable {

    public SubAccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public SubAccionJpaController() {
        emf = Persistence.createEntityManagerFactory("metasPU");
    }

    public void create(SubAccion subAccion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Accion accion = subAccion.getAccion();
            if (accion != null) {
                accion = em.getReference(accion.getClass(), accion.getId_accion());
                subAccion.setAccion(accion);
            }
            em.persist(subAccion);
            if (accion != null) {
                accion.getSubacciones().add(subAccion);
                accion = em.merge(accion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SubAccion subAccion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SubAccion persistentSubAccion = em.find(SubAccion.class, subAccion.getId_subAccion());
            Accion accionOld = persistentSubAccion.getAccion();
            Accion accionNew = subAccion.getAccion();
            if (accionNew != null) {
                accionNew = em.getReference(accionNew.getClass(), accionNew.getId_accion());
                subAccion.setAccion(accionNew);
            }
            subAccion = em.merge(subAccion);
            if (accionOld != null && !accionOld.equals(accionNew)) {
                accionOld.getSubacciones().remove(subAccion);
                accionOld = em.merge(accionOld);
            }
            if (accionNew != null && !accionNew.equals(accionOld)) {
                accionNew.getSubacciones().add(subAccion);
                accionNew = em.merge(accionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = subAccion.getId_subAccion();
                if (findSubAccion(id) == null) {
                    throw new NonexistentEntityException("The subAccion with id " + id + " no longer exists.");
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
            SubAccion subAccion;
            try {
                subAccion = em.getReference(SubAccion.class, id);
                subAccion.getId_subAccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subAccion with id " + id + " no longer exists.", enfe);
            }
            Accion accion = subAccion.getAccion();
            if (accion != null) {
                accion.getSubacciones().remove(subAccion);
                accion = em.merge(accion);
            }
            em.remove(subAccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SubAccion> findSubAccionEntities() {
        return findSubAccionEntities(true, -1, -1);
    }

    public List<SubAccion> findSubAccionEntities(int maxResults, int firstResult) {
        return findSubAccionEntities(false, maxResults, firstResult);
    }

    private List<SubAccion> findSubAccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SubAccion.class));
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

    public SubAccion findSubAccion(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SubAccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubAccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SubAccion> rt = cq.from(SubAccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
