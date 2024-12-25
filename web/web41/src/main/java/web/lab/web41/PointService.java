package web.lab.web41;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointService {

    @Autowired
    private PointRepository pointRepository;

    public List<Point> getPointsByUser(User user) {
        return pointRepository.findAllByUser(user);
    }

    public Point savePoint(Point point) {
        return pointRepository.save(point);
    }
}
