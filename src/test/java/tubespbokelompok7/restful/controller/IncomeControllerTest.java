package tubespbokelompok7.restful.controller;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tubespbokelompok7.restful.entity.Income;
import tubespbokelompok7.restful.entity.User;
import tubespbokelompok7.restful.model.CreateIncomeRequest;
import tubespbokelompok7.restful.model.IncomeResponse;
import tubespbokelompok7.restful.model.UpdateIncomeRequest;
import tubespbokelompok7.restful.model.WebResponse;
import tubespbokelompok7.restful.repository.IncomeRepository;
import tubespbokelompok7.restful.repository.UserRepository;
import tubespbokelompok7.restful.security.BCrypt;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class IncomeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        incomeRepository.deleteAll();
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
    void createIncomeBadRequest() throws Exception {
        CreateIncomeRequest request = new CreateIncomeRequest();
        request.setBalance(BigDecimal.valueOf(10000.00));
        request.setItem_name("");
        request.setPrice(BigDecimal.valueOf(20000.00));
        request.setAmount(2);

        mockMvc.perform(
                post("/api/income")
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
    void createIncomeSuccess() throws Exception {
        CreateIncomeRequest request = new CreateIncomeRequest();
        request.setBalance(new BigDecimal("10000.00"));
        request.setItem_name("Towel Cake");
        request.setPrice(new BigDecimal("20000.00"));
        request.setAmount(2);

        mockMvc.perform(
                post("/api/income")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<IncomeResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());

            BigDecimal expectedBalance = new BigDecimal("10000.00");
            BigDecimal expectedPrice = new BigDecimal("20000.00");

            assertEquals(0, expectedBalance.compareTo(response.getData().getBalance()));
            assertEquals("Towel Cake", response.getData().getItem_name());
            assertEquals(0, expectedPrice.compareTo(response.getData().getPrice()));
            assertEquals(2, response.getData().getAmount());

            assertTrue(incomeRepository.existsById(response.getData().getId()));
        });
    }


    @Test
    void getIncomeNotFound() throws Exception {
        mockMvc.perform(
                get("/api/income/21213131313")
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
    void getIncomeSuccess() throws Exception {
        User user = userRepository.findById("test").orElseThrow();

        Income income = new Income();
        income.setId(UUID.randomUUID().toString());
        income.setUser(user);
        income.setBalance(BigDecimal.valueOf(10000.00));
        income.setItem_name("Towel Cake");
        income.setPrice(BigDecimal.valueOf(20000.00));
        income.setAmount(2);
        incomeRepository.save(income);

        mockMvc.perform(
                get("/api/income/" + income.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<IncomeResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            BigDecimal expectedBalance = new BigDecimal("10000.00");
            BigDecimal expectedPrice = new BigDecimal("20000.00");

            assertNull(response.getErrors());
            assertEquals(expectedBalance, response.getData().getBalance());
            assertEquals(income.getItem_name(), response.getData().getItem_name());
            assertEquals(expectedPrice, response.getData().getPrice());
            assertEquals(income.getAmount(), response.getData().getAmount());
        });
    }

    @Test
    void updateIncomeBadRequest() throws Exception {
        UpdateIncomeRequest request = new UpdateIncomeRequest();
        request.setBalance(BigDecimal.valueOf(10000.00));
        request.setItem_name("");
        request.setPrice(BigDecimal.valueOf(20000.00));
        request.setAmount(2);

        mockMvc.perform(
                put("/api/income/1235")
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
    void updateIncomeSuccess() throws Exception {
        User user = userRepository.findById("test").orElseThrow();

        Income income = new Income();
        income.setId(UUID.randomUUID().toString());
        income.setUser(user);
        income.setBalance(BigDecimal.valueOf(10000.00));
        income.setItem_name("Towel Cake");
        income.setPrice(BigDecimal.valueOf(20000.00));
        income.setAmount(2);
        incomeRepository.save(income);

        UpdateIncomeRequest request = new UpdateIncomeRequest();
        request.setBalance(BigDecimal.valueOf(20000.00));
        request.setItem_name("French Toast");
        request.setPrice(BigDecimal.valueOf(12000.00));
        request.setAmount(4);

        mockMvc.perform(
                put("/api/income/"+income.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<IncomeResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(request.getBalance(), response.getData().getBalance());
            assertEquals(request.getItem_name(), response.getData().getItem_name());
            assertEquals(request.getPrice(), response.getData().getPrice());
            assertEquals(request.getAmount(), response.getData().getAmount());

            assertTrue(incomeRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void deleteIncomeNotFound() throws Exception {
        mockMvc.perform(
                delete("/api/income/21213131313")
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
    void deleteIncomeSuccess() throws Exception {
        User user = userRepository.findById("test").orElseThrow();

        Income income = new Income();
        income.setId(UUID.randomUUID().toString());
        income.setUser(user);
        income.setBalance(BigDecimal.valueOf(10000.00));
        income.setItem_name("Towel Cake");
        income.setPrice(BigDecimal.valueOf(20000.00));
        income.setAmount(2);
        incomeRepository.save(income);
        mockMvc.perform(
                delete("/api/income/" + income.getId())
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