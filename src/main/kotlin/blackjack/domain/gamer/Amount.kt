package blackjack.domain.gamer

class Amount private constructor(
    val value: Double,
) {
    companion object {
        val ZERO = Amount(0.0)

        fun from(bettingMoney: Int): Amount {
            return Amount(bettingMoney.toDouble())
        }
    }
}
