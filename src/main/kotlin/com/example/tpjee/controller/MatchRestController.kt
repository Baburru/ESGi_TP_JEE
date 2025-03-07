package com.example.tpjee.controller

import com.example.tpjee.models.Match
import com.example.tpjee.models.MatchRepository
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class MatchRestController(
    private val matchRepository: MatchRepository,
) {

    @GetMapping("/latest")
    fun getLatestMatch(): List<Match> {
        val oneWeekAgo = LocalDateTime.now().minusDays(7)
        return matchRepository.findByDateAfterAndEstTermineTrueOrderByDateDesc(oneWeekAgo)
    }
}