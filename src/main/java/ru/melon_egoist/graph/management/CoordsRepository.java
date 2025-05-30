package ru.melon_egoist.graph.management;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.melon_egoist.graph.classes.Coords;

public interface CoordsRepository extends JpaRepository<Coords, Long> {
}
