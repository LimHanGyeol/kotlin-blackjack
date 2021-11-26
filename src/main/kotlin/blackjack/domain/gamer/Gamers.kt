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
        val preparedGamers = mutableListOf<Gamer>()
        for (gamer in value) {
            val preparedGamer = gamer.prepare(deck)
            preparedGamers.add(preparedGamer)
        }
        return Gamers(preparedGamers)
    }

    fun getDealer(): Dealer {
        val dealer = value.first()
        return Dealer.init(dealer.name, dealer.state)
    }

    fun getPlayers(): List<Player> {
        return value.subList(PLAYER_INDEX, value.size)
            .map { Player.init(it.name, it.state) }
    }

    companion object {
        private const val PLAYER_INDEX = 1

        fun init(playerNames: List<String>): Gamers {
            val dealer = Dealer.from(Cards())
            val players = playerNames.map { Player.of(it.trim(), Cards()) }
            val gamers = listOf(dealer).plus(players)
            return Gamers(gamers)
        }

        fun from(dealer: Dealer, players: List<Player>): Gamers {
            val gamers: List<Gamer> = listOf(dealer).plus(players.toList())
            return Gamers(gamers)
        }
    }
}
