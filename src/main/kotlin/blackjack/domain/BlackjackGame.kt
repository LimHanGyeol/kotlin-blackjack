package blackjack.domain

import blackjack.domain.deck.Deck
import blackjack.domain.gamer.Dealer
import blackjack.domain.gamer.Gamer
import blackjack.domain.gamer.Gamers
import blackjack.domain.gamer.Player
import blackjack.domain.result.DealerResult
import blackjack.domain.result.PlayerResult

class BlackjackGame(
    private val gamers: Gamers,
    private val deck: Deck,
) {

    fun prepare(startGameCallback: (List<Gamer>) -> Unit): Gamers {
        val preparedGamers = gamers.prepare(deck)
        startGameCallback(preparedGamers.value)
        return preparedGamers
    }

    fun play(
        preparedGamer: Gamers,
        playableCallback: (Gamer) -> Boolean,
        currentDealerScoreCallback: (Int) -> Unit,
        gamerCardCallback: (Gamer) -> Unit,
    ): Gamers {
        val playersResult = playPlayers(playableCallback, preparedGamer.getPlayers(), gamerCardCallback)
        val dealerResult = playDealer(preparedGamer.getDealer(), currentDealerScoreCallback, gamerCardCallback)
        return Gamers.from(dealerResult, playersResult)
    }

    private fun playPlayers(
        playableCallback: (Gamer) -> Boolean,
        players: List<Player>,
        gamerCardCallback: (Gamer) -> Unit,
    ): List<Player> {
        val completedBlackjackPlayers = mutableListOf<Player>()

        for (player in players) {
            while (true) {
                val playable = playableCallback(player)
                val progressedBlackjackPlayer = player.progress(playable, deck)

                gamerCardCallback(progressedBlackjackPlayer)
                if (progressedBlackjackPlayer.isStand()) {
                    completedBlackjackPlayers.add(player)
                    break
                }
                if (progressedBlackjackPlayer.isFinished()) {
                    completedBlackjackPlayers.add(progressedBlackjackPlayer)
                    break
                }
            }
        }
        return completedBlackjackPlayers
    }

    private fun playDealer(
        dealer: Dealer,
        currentDealerScoreCallback: (Int) -> Unit,
        gamerCardCallback: (Gamer) -> Unit,
    ): Dealer {
        val preparedCurrentScore = dealer.currentScore()
        currentDealerScoreCallback(preparedCurrentScore)
        while (true) {
            val currentScore = dealer.currentScore()
            val progressedBlackjackDealer = dealer.progress(currentScore, deck)

            println()
            if (progressedBlackjackDealer.isFinished()) {
                return progressedBlackjackDealer
            }
            gamerCardCallback(progressedBlackjackDealer)
        }
    }

    fun judgeResult(
        playedGamers: Gamers,
        blackjackResultCallback: (List<Gamer>) -> Unit,
        blackjackFinalOutcomeCallback: (DealerResult, List<Player>) -> Unit,
    ) {
        blackjackResultCallback(playedGamers.value)
        val judgedPlayerResult = getPlayerResult(playedGamers)
        val dealerResult = getDealerResult(judgedPlayerResult)
        blackjackFinalOutcomeCallback(dealerResult, judgedPlayerResult)
    }

    private fun getPlayerResult(gamers: Gamers): List<Player> {
        return PlayerResult(gamers).judgePlayerResult()
    }

    private fun getDealerResult(judgedPlayerResult: List<Player>): DealerResult {
        return DealerResult.from(judgedPlayerResult)
    }
}
