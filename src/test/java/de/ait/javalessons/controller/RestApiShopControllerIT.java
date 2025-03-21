package de.ait.javalessons.controller;

import de.ait.javalessons.model.Article;
import de.ait.javalessons.repositories.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RestApiShopControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        articleRepository.deleteAll();
        List<Article> articles = List.of(
                new Article("Keyboard", "Mechanical keyboard", 49.99, 10),
                new Article("Mouse", "Wireless mouse", 29.99, 15),
                new Article("Monitor", "27-inch IPS monitor", 199.99, 5),
                new Article("Mousepad", "Gaming mouse pad", 9.99, 20),
                new Article("Headphones", "Bluetooth headphones", 79.99, 8)
        );
        articleRepository.saveAll(articles);
    }

    @Test
    @DisplayName("Проверяем, что публичный эндпоинт доступен всем и возвращает правильный контент")
    void testGetPublicInfo() throws Exception {
        mockMvc.perform(get("/products/public/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray());
    }

    @Nested
    @DisplayName("Тесты для эндпоинта /products/customer/cart")
    class UserInfoTests {

        @Test
        @DisplayName("Когда пользователь авторизован с ролью CUSTOMER, возвращается статус 200 и нужный контент")
        @WithMockUser(username = "testUser", roles = {"CUSTOMER"})
        void testGetUserInfoAsUser() throws Exception {
            mockMvc.perform(get("/products/customer/cart"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$").isArray());
        }

        @Test
        @DisplayName("Когда пользователь авторизован с ролью CUSTOMER, при добавлении в корзину возвращается статус 200 и нужный контент")
        @WithMockUser(username = "testUser", roles = {"CUSTOMER"})
        void testPostArticleToCartAsUser() throws Exception {
            mockMvc.perform(post("/products/customer/cart?id=1&quantity=1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"));
        }

        @Test
        @DisplayName("Когда пользователь не авторизован")
        void testGetUserInfoAsAnonymous() throws Exception {
            mockMvc.perform(get("/products/customer/cart"))
                    .andExpect(status().is3xxRedirection());
        }

        @Test
        @DisplayName("Когда пользователь не имеет статуса CUSTOMER")
        @WithMockUser(username = "testUser", roles = {"ADMIN"})
        void testGetCustomerCartAsAdmin() throws Exception {
            mockMvc.perform(get("/products/customer/cart"))
                    .andExpect(status().is4xxClientError());
        }
    }

    @Nested
    @DisplayName("Тесты для эндпоинта /products/manager/add")
    class AdminInfoTests {

        @Test
        @DisplayName("Когда пользователь авторизован с ролью MANAGER, при добавлении нового товара возвращается статус 200 и нужный контент")
        @WithMockUser(username = "testUser", roles = {"MANAGER"})
        void testPostArticleToCartAsUser() throws Exception {
            mockMvc.perform(post("/products/manager/add?name=Audio%20System%20Sony&description=200W%2C%20stereo&price=475&quantity=5"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"));
        }

        @Test
        @DisplayName("Когда пользователь не имеет роли MANAGER, возвращается ошибка 403")
        @WithMockUser(username = "testUser", roles = {"CUSTOMER"})
        void testGetAdminInfoAsUser() throws Exception {
            mockMvc.perform(post("/products/manager/add?name=Audio%20System%20Sony&description=200W%2C%20stereo&price=475&quantity=5"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("Когда пользователь не авторизован, возвращается ошибка 401 (или 403)")
        void testGetAdminInfoAsAnonymous() throws Exception {
            mockMvc.perform(get("/products/manager/add?name=Audio%20System%20Sony&description=200W%2C%20stereo&price=475&quantity=5"))
                    .andExpect(status().is3xxRedirection());
        }
    }
}
