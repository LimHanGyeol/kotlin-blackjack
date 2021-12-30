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
    amount: Amount,
    result: ResultType? = null,
) : Gamer(name, state, amount, result) {

    init {
        validateName(name)
    }

    override fun prepare(deck: Deck): Player {
        val completedFirstDraw = draw(deck, state)
        val completedSecondDraw = draw(deck, completedFirstDraw)
        return Player(name, completedSecondDraw, this.amount)
    }

    fun progress(playable: Boolean, deck: Deck): Player {
        if (isStand(playable)) {
            return Player(name, Stand(cards), this.amount)
        }
        return play(deck)
    }

    override fun play(deck: Deck): Player {
        if (state.cards.isBlackjack()) {
            return Player(name, Blackjack(cards), this.amount)
        }
        val currentState = draw(deck, state)
        return Player(name, currentState, this.amount)
    }

    fun judgeResult(result: ResultType?) {
        this.result = result
    }

    fun isBlackjack(): Boolean = cards.isBlackjack()

    fun isTwentyOne(): Boolean = cards.isTwentyOne()

    companion object {
        fun of(name: String, cards: Cards, bettingMoney: Int): Player {
            return Player(name, FirstDraw(cards), Amount.from(bettingMoney))
        }

        fun init(name: String, state: State, amount: Amount): Player {
            return Player(name, state, amount)
        }
    }
}
