package blackjack.domain.result

import blackjack.domain.gamer.Player
import blackjack.domain.result.ResultType.LOSE
import blackjack.domain.result.ResultType.PUSH
import blackjack.domain.result.ResultType.WIN

class DealerResult private constructor(
    val win: Int = 0,
    val push: Int = 0,
    val lose: Int = 0,
) {
    companion object {
        private const val DEFAULT = 0

        fun from(playerResults: List<Player>): DealerResult {
            return playerResults.groupingBy {
                it.result
            }.eachCount()
                .run {
                    DealerResult(
                        this[LOSE] ?: DEFAULT,
                        this[PUSH] ?: DEFAULT,
                        this[WIN] ?: DEFAULT,
                    )
                }
        }
    }
}
