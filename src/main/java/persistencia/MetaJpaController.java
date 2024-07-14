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
import logica.Meta;
import logica.User;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author gilbe
 */
public class MetaJpaController implements Serializable {

    public MetaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public MetaJpaController() {
        emf = Persistence.createEntityManagerFactory("metasPU");
    }

    public void create(Meta meta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user = meta.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getId_usuario());
                meta.setUser(user);
            }
            em.persist(meta);
            if (user != null) {
                user.getMetas().add(meta);
                user = em.merge(user);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Meta meta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Meta persistentMeta = em.find(Meta.class, meta.getId_meta());
            User userOld = persistentMeta.getUser();
            User userNew = meta.getUser();
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getId_usuario());
                meta.setUser(userNew);
            }
            meta = em.merge(meta);
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getMetas().remove(meta);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getMetas().add(meta);
                userNew = em.merge(userNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = meta.getId_meta();
                if (findMeta(id) == null) {
                    throw new NonexistentEntityException("The meta with id " + id + " no longer exists.");
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
            Meta meta;
            try {
                meta = em.getReference(Meta.class, id);
                meta.getId_meta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The meta with id " + id + " no longer exists.", enfe);
            }
            User user = meta.getUser();
            if (user != null) {
                user.getMetas().remove(meta);
                user = em.merge(user);
            }
            em.remove(meta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Meta> findMetaEntities() {
        return findMetaEntities(true, -1, -1);
    }

    public List<Meta> findMetaEntities(int maxResults, int firstResult) {
        return findMetaEntities(false, maxResults, firstResult);
    }

    private List<Meta> findMetaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Meta.class));
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

    public Meta findMeta(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Meta.class, id);
        } finally {
            em.close();
        }
    }

    public int getMetaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Meta> rt = cq.from(Meta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
