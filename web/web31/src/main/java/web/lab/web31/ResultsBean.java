package web.lab.web31;

import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import web.lab.entity.ResultEntity;
import web.lab.entity.Point;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
@Getter
@Setter
public class ResultsBean implements Serializable {

    @PersistenceContext(unitName = "my-persistence-unit")
    private EntityManager entityManager;

    private List<Point> results = new ArrayList<>();
    private boolean isInitialized = false; // Флаг для проверки, загружены ли данные

    @PostConstruct
    private void init() {
        // Загружаем результаты только один раз при старте приложения
        if (!isInitialized) {
            loadResults();
            isInitialized = true;
        }
    }

    private void loadResults() {
        TypedQuery<ResultEntity> query = entityManager.createQuery(
                "SELECT r FROM ResultEntity r", ResultEntity.class);

        List<ResultEntity> resultEntities = query.getResultList();
        results = new ArrayList<>();
        for (ResultEntity entity : resultEntities) {
            results.add(new Point(entity.getX(), entity.getY(), entity.getR(), entity.isHit()));
        }
    }

    @Transactional
    public void addResult(Point point) {
        ResultEntity resultEntity = ResultEntity.builder()
                .x(point.getX())
                .y(point.getY())
                .r(point.getR())
                .hit(point.isHit())
                .timestamp(LocalDateTime.now())
                .build();

        entityManager.persist(resultEntity);
        results.add(new Point(resultEntity.getX(), resultEntity.getY(), resultEntity.getR(), resultEntity.isHit()));
    }

    public String getJsonResults() {
        return new Gson().toJson(results);
    }
}
