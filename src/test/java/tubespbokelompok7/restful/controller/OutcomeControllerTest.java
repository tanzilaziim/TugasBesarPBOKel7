package tubespbokelompok7.restful.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tubespbokelompok7.restful.entity.Income;
import tubespbokelompok7.restful.entity.Outcome;
import tubespbokelompok7.restful.entity.User;
import tubespbokelompok7.restful.model.*;
import tubespbokelompok7.restful.repository.OutcomeRepository;
import tubespbokelompok7.restful.repository.UserRepository;
import tubespbokelompok7.restful.security.BCrypt;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OutcomeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OutcomeRepository outcomeRepository;
    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        outcomeRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("Test");
        user.setPassword(BCrypt.hashpw("123", BCrypt.gensalt()));
        user.setName("Test");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000);
        userRepository.save(user);
    }

    @Test
    void createOutcomeBadRequest() throws Exception {
        CreateOutcomeRequest request = new CreateOutcomeRequest();
        request.setBalance(BigDecimal.valueOf(10000.0));
        request.setOutcome_name("");

        mockMvc.perform(
                post("/api/outcome")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void createOutcomeSuccess() throws Exception {
        CreateOutcomeRequest request = new CreateOutcomeRequest();
        request.setBalance(BigDecimal.valueOf(30000.0));
        request.setOutcome_name("Production Cost");
        request.setOutcome_total(BigDecimal.valueOf(10000.0));

        mockMvc.perform(
                post("/api/outcome")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpect(
                status().isOk()
        ).andDo(result -> {
            WebResponse<OutcomeResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());

            // Sesuaikan ini dengan struktur OutcomeResponse yang sebenarnya
            BigDecimal expectedBalance = new BigDecimal("30000.0");
            BigDecimal expectedOutcomeTotal = new BigDecimal("10000.0");

            assertEquals(expectedBalance, response.getData().getBalance());
            assertEquals("Production Cost", response.getData().getOutcome_name());
            assertEquals(expectedOutcomeTotal, response.getData().getOutcome_total());
        });
    }


    @Test
    void getOutcomeNotFound() throws Exception {
        mockMvc.perform(
                get("/api/outcome/21213131313")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void getOutcomeSuccess() throws Exception {
        User user = userRepository.findById("test").orElseThrow();

        Outcome outcome = new Outcome();
        outcome.setId(UUID.randomUUID().toString());
        outcome.setUser(user);
        outcome.setBalance(BigDecimal.valueOf(30000.0));
        outcome.setOutcome_name("Production Cost");
        outcome.setOutcome_total(BigDecimal.valueOf(10000.0));
        outcomeRepository.save(outcome);

        mockMvc.perform(
                get("/api/outcome/" + outcome.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<OutcomeResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());

            // Sesuaikan ini dengan struktur OutcomeResponse yang sebenarnya
            BigDecimal expectedBalance = BigDecimal.valueOf(30000.0);
            BigDecimal expectedOutcome_total = BigDecimal.valueOf(10000.0);

            // Menggunakan metode compareTo untuk membandingkan BigDecimal
            assertEquals(0, expectedBalance.compareTo(response.getData().getBalance()));
            assertEquals("Production Cost", response.getData().getOutcome_name());
            assertEquals(0, expectedOutcome_total.compareTo(response.getData().getOutcome_total()));
        });
    }

    @Test
    void updateOutcomeBadRequest() throws Exception {
        UpdateOutcomeRequest request = new UpdateOutcomeRequest();
        request.setOutcome_name("");
        request.setOutcome_total(BigDecimal.valueOf(10000.0));

        mockMvc.perform(
                put("/api/outcome/1235")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void updateOutcomeSuccess() throws Exception {
        User user = userRepository.findById("test").orElseThrow();

        Outcome outcome = new Outcome();
        outcome.setId(UUID.randomUUID().toString());
        outcome.setUser(user);
        outcome.setBalance(BigDecimal.valueOf(20000.00));
        outcome.setOutcome_name("Production Cost");
        outcome.setOutcome_total(BigDecimal.valueOf(10000.00));
        outcomeRepository.save(outcome);

        UpdateOutcomeRequest request = new UpdateOutcomeRequest();
        request.setBalance(BigDecimal.valueOf(30000.00));
        request.setOutcome_name("Distribution Cost");
        request.setOutcome_total(BigDecimal.valueOf(12000.00));

        mockMvc.perform(
                put("/api/outcome/"+outcome.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<OutcomeResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(request.getBalance(), response.getData().getBalance());
            assertEquals(request.getOutcome_name(), response.getData().getOutcome_name());
            assertEquals(request.getOutcome_total(), response.getData().getOutcome_total());

            assertTrue(outcomeRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void deleteOutcomeNotFound() throws Exception {
        mockMvc.perform(
                delete("/api/outcome/21213131313")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void deleteOutcomeSuccess() throws Exception {
        User user = userRepository.findById("test").orElseThrow();

        Outcome outcome = new Outcome();
        outcome.setId(UUID.randomUUID().toString());
        outcome.setUser(user);
        outcome.setBalance(BigDecimal.valueOf(30000.0));
        outcome.setOutcome_name("Production Cost");
        outcome.setOutcome_total(BigDecimal.valueOf(10000.0));
        outcomeRepository.save(outcome);
        mockMvc.perform(
                delete("/api/outcome/" + outcome.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals("OK", response.getData());
        });
    }
}