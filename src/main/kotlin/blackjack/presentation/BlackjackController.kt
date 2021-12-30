package blackjack.presentation

import blackjack.domain.BlackjackGame
import blackjack.domain.deck.Cards
import blackjack.domain.deck.Deck
import blackjack.domain.gamer.Gamers
import blackjack.domain.gamer.Player
import blackjack.view.InputView
import blackjack.view.OutputView

class BlackjackController {

    fun run() {
        val blackjackGame = BlackjackGame(initGamers(), Deck.init())
        val preparedGamers = blackjackGame.prepare(OutputView::printStartGame)

        with(blackjackGame) {
            play(
                preparedGamers,
                InputView::inputPlayable,
                OutputView::printCurrentDealerScore,
                OutputView::printGamerCard
            ).let {
                judgeResult(it, OutputView::printBlackjackResult, OutputView::printFinalOutcome)
            }
        }
    }

    private fun initGamers(): Gamers {
        val inputPlayerNames = InputView.inputPlayers()
        val players = inputPlayerNames.map {
            val inputBettingMoney = InputView.inputBettingMoney(it)
            Player.of(it.trim(), Cards(), inputBettingMoney)
        }
        return Gamers.init(players)
    }
}
