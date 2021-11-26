package blackjack.domain.result

import blackjack.domain.gamer.Gamers
import blackjack.domain.gamer.Player

class PlayerResult(
    private val gamers: Gamers,
) {

    fun judgePlayerResult(): List<Player> {
        val playerResults = mutableListOf<Player>()
        val dealer = gamers.getDealer()
        val players = gamers.getPlayers()
        for (player in players) {
            val playerResult = dealer.judgeGameResult(player).opposite()
            player.judgeResult(playerResult)
            playerResults.add(player)
        }
        return playerResults.toList()
    }
}
