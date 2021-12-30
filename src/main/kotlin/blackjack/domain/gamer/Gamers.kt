package blackjack.domain.gamer

import blackjack.domain.deck.Cards
import blackjack.domain.deck.Deck
import blackjack.exception.NotExistDealerException

@JvmInline
value class Gamers private constructor(
    val value: List<Gamer>,
) {

    init {
        if (value.first() !is Dealer) {
            throw NotExistDealerException()
        }
    }

    fun prepare(deck: Deck): Gamers {
        val gamers = value.map { gamer ->
            gamer.prepare(deck)
        }
        return Gamers(gamers)
    }

    fun getDealer(): Dealer {
        val dealer = value.first()
        return Dealer.init(dealer.name, dealer.state)
    }

    fun getPlayers(): List<Player> {
        return value.subList(PLAYER_INDEX, value.size)
            .map { Player.init(it.name, it.state, it.amount) }
    }

    companion object {
        private const val PLAYER_INDEX = 1

        fun init(players: List<Gamer>): Gamers {
            val dealer = Dealer.from(Cards())
            val gamers = listOf(dealer).plus(players)
            return Gamers(gamers)
        }

        fun from(dealer: Dealer, players: List<Player>): Gamers {
            val gamers: List<Gamer> = listOf(dealer).plus(players.toList())
            return Gamers(gamers)
        }
    }
}
