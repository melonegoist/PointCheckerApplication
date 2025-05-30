package ru.melon_egoist.graph.management;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.melon_egoist.auth.User;
import ru.melon_egoist.auth.UserRepository;
import ru.melon_egoist.graph.classes.Coords;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GraphController.class)
class GraphControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CoordsRepository coordsRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetCoordinates_validInput_returnsInAreaResponse() throws Exception {
        Coords coords = new Coords();
        coords.setX("0");
        coords.setY("0");
        coords.setR("2");
        coords.setOwner("testUser");

        User mockUser = new User();
        mockUser.setUsername("testUser");
        mockUser.setCoords(Collections.emptyList());

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));

        mockMvc.perform(post("/graph")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coords)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.x").value("0"))
                .andExpect(jsonPath("$.y").value("0"))
                .andExpect(jsonPath("$.r").value("2"))
                .andExpect(jsonPath("$.inArea").value("in Area!"));
    }

    @Test
    void testGetAllDots_withUsername_returnsUserCoords() throws Exception {
        Coords coords = new Coords();
        coords.setX("1");
        coords.setY("1");
        coords.setR("2");
        coords.setOwner("testUser");
        coords.setInArea("in Area!");

        when(coordsRepository.findAll()).thenReturn(Collections.singletonList(coords));

        mockMvc.perform(get("/graph/dots").param("username", "testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].x").value("1"))
                .andExpect(jsonPath("$[0].y").value("1"))
                .andExpect(jsonPath("$[0].r").value("2"))
                .andExpect(jsonPath("$[0].inArea").value("in Area!"))
                .andExpect(jsonPath("$[0].owner").value("testUser"));
    }
}
