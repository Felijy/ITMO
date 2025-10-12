package web.lab.islab12.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import web.lab.islab12.model.City;

import java.util.List;
import java.util.Map;

@ApplicationScoped
public class CityDataRepositoryImpl implements CityDataRepository {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(
            "cityPU",
            Map.of(
                    "eclipselink.ddl-generation", "none",
                    "jakarta.persistence.schema-generation.database.action", "none"
            )
    );

    private EntityManager em() { return emf.createEntityManager(); }

    @Override
    public City save(City c) {
        EntityManager em = em();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            City managed;
            if (c.getId() == null) {
                em.persist(c);
                managed = c;
            } else {
                managed = em.merge(c);
            }
            tx.commit();
            return managed;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public City findById(Integer id) {
        if (id == null) return null;
        EntityManager em = em();
        try { return em.find(City.class, id); } finally { em.close(); }
    }

    @Override
    public List<City> findAll() {
        EntityManager em = em();
        try { return em.createQuery("select c from City c", City.class).getResultList(); } finally { em.close(); }
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) return;
        EntityManager em = em();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            City c = em.find(City.class, id);
            if (c != null) em.remove(c);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally { em.close(); }
    }

    @Override
    public List<City> findByNameStartingWithIgnoreCase(String prefix) {
        if (prefix == null || prefix.isBlank()) return List.of();
        EntityManager em = em();
        try { return em.createQuery("select c from City c where lower(c.name) like :p", City.class)
                .setParameter("p", prefix.toLowerCase() + "%")
                .getResultList(); } finally { em.close(); }
    }

    @Override
    public List<City> findByMetersAboveSeaLevelGreaterThan(Float value) {
        if (value == null) return List.of();
        EntityManager em = em();
        try { return em.createQuery("select c from City c where c.metersAboveSeaLevel > :v", City.class)
                .setParameter("v", value)
                .getResultList(); } finally { em.close(); }
    }

    @Override
    public Long sumTelephoneCodes() {
        EntityManager em = em();
        try { Long s = em.createQuery("select sum(c.telephoneCode) from City c", Long.class).getSingleResult(); return s == null ? 0L : s; } finally { em.close(); }
    }

    @Override
    public List<City> findWithEstablishmentDate() {
        EntityManager em = em();
        try { return em.createQuery("select c from City c where c.establishmentDate is not null", City.class).getResultList(); } finally { em.close(); }
    }
}
