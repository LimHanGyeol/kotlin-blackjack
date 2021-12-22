package blackjack.domain.gamer

import blackjack.domain.deck.Cards
import blackjack.domain.deck.Deck
import blackjack.domain.result.ResultType
import blackjack.domain.state.Blackjack
import blackjack.domain.state.FirstDraw
import blackjack.domain.state.Stand
import blackjack.domain.state.State

class Player private constructor(
    name: String,
    state: State,
    bettingMoney: Int = 0,
    result: ResultType? = null,
) : Gamer(name, state, result, bettingMoney) {

    init {
        validateName(name)
    }

    override fun prepare(deck: Deck): Player {
        val completedFirstDraw = draw(deck, state)
        val completedSecondDraw = draw(deck, completedFirstDraw)
        return Player(name, completedSecondDraw)
    }

    fun progress(playable: Boolean, deck: Deck): Player {
        if (isStand(playable)) {
            return Player(name, Stand(cards))
        }
        return play(deck)
    }

    override fun play(deck: Deck): Player {
        if (state.cards.isBlackjack()) {
            return Player(name, Blackjack(cards))
        }
        val currentState = draw(deck, state)
        return Player(name, currentState)
    }

    fun judgeResult(result: ResultType?) {
        this.result = result
    }

    fun isBlackjack(): Boolean = cards.isBlackjack()

    fun isTwentyOne(): Boolean = cards.isTwentyOne()

    companion object {
        fun of(name: String, cards: Cards, bettingMoney: Int): Player {
            return Player(name, FirstDraw(cards), bettingMoney)
        }

        fun init(name: String, state: State): Player {
            return Player(name, state)
        }
    }
}
