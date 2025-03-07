package com.example.tpjee.models

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Entity
@Table(name = "matches")
data class Match(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var equipe1: String ="",
    var equipe2: String = "",
    var scoreEquipe1: Int = 0,
    var scoreEquipe2: Int = 0,
    var estTermine: Boolean = false,
    var date: LocalDateTime = LocalDateTime.now(),
    var photoUrl: String = ""
)

@Repository
interface MatchRepository : JpaRepository<Match, Long> {
    fun findByDateAfterAndEstTermineTrueOrderByDateDesc(date: LocalDateTime): List<Match>
}
