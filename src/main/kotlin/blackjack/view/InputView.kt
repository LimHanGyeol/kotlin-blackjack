package blackjack.view

import blackjack.domain.gamer.Gamer
import blackjack.domain.state.State

class InputView {

    companion object {
        private const val INPUT_PLAYER_MESSAGE = "게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)"
        private const val INPUT_CARD_SIGN = "는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)"
        private const val DELIMITER = ","

        fun inputPlayers(): List<String> {
            println(INPUT_PLAYER_MESSAGE)
            return readLine()!!.split(DELIMITER)
        }

        fun inputPlayable(player: Gamer): Boolean {
            if (player.isFinished()) {
                return false
            }
            val playable = inputCardSign(player)
            if (playable == State.FINISHED_SIGN) {
                return false
            }
            return playable == State.CAN_PLAY
        }

        private fun inputCardSign(gamer: Gamer): String {
            println("\n${gamer.name}$INPUT_CARD_SIGN")
            return readLine()!!
        }
    }
}
