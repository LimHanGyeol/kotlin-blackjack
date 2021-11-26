package blackjack.domain.result

enum class ResultType(val value: String) {
    WIN("승"), PUSH("무"), LOSE("패");

    fun opposite(): ResultType {
        return when (this) {
            WIN -> LOSE
            LOSE -> WIN
            else -> PUSH
        }
    }

    override fun toString(): String = this.value
}
