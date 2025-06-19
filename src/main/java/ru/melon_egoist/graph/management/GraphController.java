package ru.melon_egoist.graph.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.melon_egoist.auth.User;
import ru.melon_egoist.auth.UserRepository;
import ru.melon_egoist.graph.classes.Coords;
import ru.melon_egoist.graph.classes.GraphResponse;
import ru.melon_egoist.graph.handlers.GraphHandler;
import ru.melon_egoist.management.PointCounter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GraphController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CoordsRepository coordsRepository;

    @Autowired
    private PointCounter counter;

    @PostMapping("/graph")
    public ResponseEntity<?> getCoordinates(@RequestBody Coords data) {
        String x = data.getX();
        String y = data.getY();
        String r = data.getR();

        String owner = data.getOwner();

        GraphHandler handler = new GraphHandler(x, y, r);
        String responseValue = handler.inArea();
        
        // check if point is in area
        counter.countNewPoint(responseValue.equals("not in Area("));
        boolean notificationState = counter.getNotificationState();

        data.setInArea(responseValue);

        addCoords(data, owner);

        System.out.println("DONE");
        return ResponseEntity.ok(new GraphResponse(x, y, r, responseValue, notificationState));
    }

    @GetMapping("/graph/dots")
    public ResponseEntity<?> getAllDots(@RequestParam String username) {
        List<Map<String, String>> response = new ArrayList<>();

        for (Coords coord: coordsRepository.findAll()) {
            if (coord.getOwner().equals(username)) {
                Map<String, String> data = new HashMap<>();
                data.put("x", coord.getX());
                data.put("y", coord.getY());
                data.put("r", coord.getR());
                data.put("inArea", coord.getInArea());
                data.put("owner", coord.getOwner());

                response.add(data);
            }
        }

        return ResponseEntity.ok(response);
    }

    private void addCoords(Coords coord, String owner) {
        User user = userRepository.findByUsername(owner).orElse(null);

        if (user != null) {
            coord.setUsersCoords(user);
            user.getCoords().add(coord);

            userRepository.save(user);
        }
    }
}
