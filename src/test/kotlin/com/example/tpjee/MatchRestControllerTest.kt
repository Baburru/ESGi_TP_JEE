package com.example.tpjee

import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

@SpringBootTest
@AutoConfigureMockMvc
class MatchRestControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun testAccessRoute() {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk)
    }

    @Test
    fun testAdminNotAllowed() {
        mockMvc.perform(get("/admin"))
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun testAdminAllowed() {

        val authentication = UsernamePasswordAuthenticationToken("user", null, emptyList())
        SecurityContextHolder.getContext().authentication = authentication

        mockMvc.perform(get("/admin"))
            .andExpect(status().isOk)
    }
}
