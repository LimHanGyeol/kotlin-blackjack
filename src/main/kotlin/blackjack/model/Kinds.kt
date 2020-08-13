package blackjack.model

enum class Kinds(val kindsName: String, val point: Int) {
    ACE("A", 1),
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    KING("K", 10),
    QUEEN("Q", 10),
    JACK("J", 10);

    companion object {
        fun findByKinds(kinds: Kinds): Kinds = values().first { it.kindsName == kinds.kindsName }

        fun isAce(point: Int) = ACE.point == point
    }
}