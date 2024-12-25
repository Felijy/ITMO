package web.lab.web41;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HitController {

    @Autowired
    private PointService pointService;

    @Autowired
    private UserService userService;

    @PostMapping("/check")
    public ResponseEntity<Point> checkPoint(@RequestBody PointRequest pointRequest) {
        User user = userService.findByUsername(pointRequest.getUsername())
                .filter(u -> u.getPassword().equals(pointRequest.getPassword()))
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if ((pointRequest.getX() < -5) || (pointRequest.getX() > 3) ||
                (pointRequest.getY() < -5) || (pointRequest.getY() > 5) ||
                (pointRequest.getR() < 1) || (pointRequest.getR() > 3)) {
            return ResponseEntity.status(400).build();
        }
        boolean hit = isPointInArea(pointRequest.getX(), pointRequest.getY(), pointRequest.getR());

        Point point = new Point();
        point.setX(pointRequest.getX());
        point.setY(pointRequest.getY());
        point.setR(pointRequest.getR());
        point.setHit(hit);
        point.setUser(user);

        pointService.savePoint(point);

        return ResponseEntity.ok(point);
    }

    @GetMapping("/getPoints")
    public ResponseEntity<List<Point>> getPoints(@RequestParam String username, @RequestParam String password) {
        User user = userService.findByUsername(username)
                .filter(u -> u.getPassword().equals(password))
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        List<Point> points = pointService.getPointsByUser(user);

        return ResponseEntity.ok(points);
    }

    private boolean isPointInArea(double x, double y, double r) {
        return
                (x <= 0 && y >= 0 && x*x + y*y <= (r/2 * r/2)) ||
                        (x >= 0 && y >= 0 && x <= r && y <= r/2) ||
                        (x <= 0 && y <= 0 && y >= 2*(-r/2 - x));

    }
}