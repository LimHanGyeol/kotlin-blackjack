package blackjack.domain.result

import blackjack.domain.deck.Cards
import blackjack.domain.gamer.Player
import blackjack.domain.result.ResultType.LOSE
import blackjack.domain.result.ResultType.WIN
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("블랙잭 게임 딜러 결과 테스트")
class DealerResultTest {

    @Test
    @DisplayName("플레이어의 결과들을 기반으로 딜러의 결과를 계산한다")
    fun `sut returns correctly`() {
        // Arrange
        val tommy = Player.of("tommy", Cards(), 10_000)
        tommy.judgeResult(WIN)

        val pobi = Player.of("pobi", Cards(), 20_000)
        pobi.judgeResult(WIN)

        val jason = Player.of("jason", Cards(), 20_000)
        jason.judgeResult(LOSE)

        val players = listOf(tommy, pobi, jason)

        // Act
        val sut = DealerResult.from(players)

        // Assert
        assertThat(sut.win).isEqualTo(1)
        assertThat(sut.push).isEqualTo(0)
        assertThat(sut.lose).isEqualTo(2)
    }
}
