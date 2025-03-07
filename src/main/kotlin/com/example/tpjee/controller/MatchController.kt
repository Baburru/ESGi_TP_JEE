package com.example.tpjee.controller

import com.example.tpjee.models.Match
import com.example.tpjee.models.MatchRepository
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@Controller
class MatchController(
    private val matchRepository: MatchRepository,
) {
    @GetMapping("/")
    fun userPage(): String {
        return "index"
    }

    @GetMapping("/admin")
    fun adminPage(@AuthenticationPrincipal user: UserDetails?): String {
        return "admin"
    }

}