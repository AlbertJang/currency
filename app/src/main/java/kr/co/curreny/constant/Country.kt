package kr.co.curreny.constant

enum class Country(val position: Int, val unit: String, val description: String) {
    KOREA(0, "KRW", "한국(KRW)"),
    JAPAN(1, "JPY", "일본(JPY)"),
    PHILIPPINES(2, "PHP", "필리핀(PHP)")
}