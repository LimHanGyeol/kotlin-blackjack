package blackjack.controller

import blackjack.model.BlackJackGame
import blackjack.model.Card
import blackjack.model.Dealer
import blackjack.model.Gamer
import blackjack.model.Player
import blackjack.model.Point
import blackjack.view.InputView
import blackjack.view.ResultView

fun main() {
    registerGame().let {
        drawCard(it)
        showResult(it)
        showScore(it)
    }
}

private fun showScore(game: BlackJackGame) {
    ResultView.printFinalScore(game)
}

private fun showResult(game: BlackJackGame) {
    (game.dealer as Dealer).requestCardIfAvailableExtraCard()
    ResultView.printResult(game)
}

private fun drawCard(game: BlackJackGame) {
    game.players.map {
        while (isContinueDraw(it)) {
            it.requestCard(Card.pop())
            ResultView.printPlayerHaveCard(it)
        }
    }
}

private fun isContinueDraw(player: Gamer) =
    !Point.isReachMaxPoint(player.calculatePoint()) && InputView.requestOneOfCard(player) == "y"

private fun registerGame(): BlackJackGame {
    val playerName = InputView.requestPlayerNames()
    val blackJackGame = BlackJackGame(dealer = Dealer(), players = playerName.map(::Player)).apply {
        initDealer()
        initPlayers()
    }
    ResultView.printPreGame(blackJackGame)
    return blackJackGame
}