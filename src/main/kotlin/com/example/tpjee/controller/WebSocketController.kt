package com.example.tpjee.controller

import com.example.tpjee.config.CHANNEL_NAME
import com.example.tpjee.models.Match
import com.example.tpjee.models.MatchRepository
import org.springframework.context.event.EventListener
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.socket.messaging.SessionSubscribeEvent

data class ScoreUpdate(val matchId: Long, val teamName: String)


@Controller
@RequestMapping("/ws") // Chemin de base pour toutes les méthodes de ce contrôleur
class WebSocketController(private val messagingTemplate: SimpMessagingTemplate, private val matchRepository: MatchRepository
) {

    private val messageHistory = ArrayList<Match>()

    @MessageMapping("/addMatch")
    fun addMatch(newMatch: Match) {
        println("Ajout d'un match: $newMatch")

        // Sauvegarde le match dans la base de données
        val savedMatch = matchRepository.save(newMatch)

        // Ajoute le match à l'historique local
        messageHistory.add(savedMatch)

        // Envoie la mise à jour aux clients
        messagingTemplate.convertAndSend(CHANNEL_NAME, messageHistory)
    }

    @MessageMapping("/score")
    fun scored(scoreUpdate: ScoreUpdate) {
        println("${scoreUpdate.teamName} scored!")

        val existingMatch = messageHistory.find { it.id == scoreUpdate.matchId }

        if (existingMatch != null) {
            if (existingMatch.equipe1 == scoreUpdate.teamName) {
                existingMatch.scoreEquipe1 += 1
            } else if (existingMatch.equipe2 == scoreUpdate.teamName) {
                existingMatch.scoreEquipe2 += 1
            }

            println("Updated Score: ${existingMatch.scoreEquipe1} - ${existingMatch.scoreEquipe2}")
        } else {
            println("Match not found!")
        }

        // Send updated data to all clients
        messagingTemplate.convertAndSend(CHANNEL_NAME, messageHistory)
    }

    @MessageMapping("/finish")
    fun finishMatch(matchUpdate: Match) {
        println("Match terminé: ${matchUpdate.id}")

        // Récupère le match dans la BD
        val existingMatch = matchRepository.findById(matchUpdate.id!!)
            .orElse(null)

        if (existingMatch != null) {
            existingMatch.estTermine = true
            matchRepository.save(existingMatch) // Met à jour dans la BD

            // Met à jour l'historique local
            messageHistory.replaceAll { if (it.id == existingMatch.id) existingMatch else it }

            // Notifie les clients
            messagingTemplate.convertAndSend(CHANNEL_NAME, messageHistory)
        } else {
            println("Match non trouvé !")
        }
    }

    @EventListener
    fun handleWebSocketSubscribeListener(event: SessionSubscribeEvent) {
        val headerAccessor = StompHeaderAccessor.wrap(event.message)
        if (CHANNEL_NAME == headerAccessor.destination) {
            messagingTemplate.convertAndSend(CHANNEL_NAME, messageHistory)
        }
    }
}