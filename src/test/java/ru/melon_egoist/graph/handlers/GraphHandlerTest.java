package ru.melon_egoist.graph.handlers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GraphHandlerTest {

    @Test
    @DisplayName("Точка в прямоугольнике: x = -1, y = -1, r = 2")
    void testPointInRectangle() {
        GraphHandler handler = new GraphHandler("-1", "-1", "2");
        assertEquals("in Area!", handler.inArea());
    }

    @Test
    @DisplayName("Точка в треугольнике: x = 1, y = -0.5, r = 2")
    void testPointInTriangle() {
        GraphHandler handler = new GraphHandler("1", "-1", "2");
        assertEquals("in Area!", handler.inArea());
    }

    @Test
    @DisplayName("Точка в секторе: x = 1, y = 1, r = 2")
    void testPointInSector() {
        GraphHandler handler = new GraphHandler("1", "1", "2");
        assertEquals("in Area!", handler.inArea());
    }

    @Test
    @DisplayName("Точка вне области: x = 3, y = 3, r = 2")
    void testPointOutOfArea() {
        GraphHandler handler = new GraphHandler("3", "3", "2");
        assertEquals("not in Area(", handler.inArea());
    }

    @Test
    @DisplayName("Неверное значение X")
    void testInvalidX() {
        GraphHandler handler = new GraphHandler("abc", "0", "2");
        assertTrue(handler.inArea().contains("|Wrong x|"));
    }

    @Test
    @DisplayName("Неверное значение Y")
    void testInvalidY() {
        GraphHandler handler = new GraphHandler("0", "hello", "2");
        assertTrue(handler.inArea().contains("|Wrong y|"));
    }

    @Test
    @DisplayName("Неверное значение R")
    void testInvalidR() {
        GraphHandler handler = new GraphHandler("0", "0", "zero");
        assertTrue(handler.inArea().contains("|Wrong r|"));
    }

    @Test
    @DisplayName("Несколько ошибок: X и R неверные")
    void testMultipleInvalidInputs() {
        GraphHandler handler = new GraphHandler("Xx", "0", "-1");
        String result = handler.inArea();
        assertTrue(result.contains("|Wrong x|") || result.contains("|Wrong r|"));
    }
}
