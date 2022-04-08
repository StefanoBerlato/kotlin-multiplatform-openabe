package it.stefanoberlato

/** OpenABE has two schemes only, [CP_ABE] and [KP_ABE] */
enum class SchemeID {
    CP_ABE,
    KP_ABE;

    override fun toString(): String {
        return when (this) {
            CP_ABE -> "CP-ABE"
            KP_ABE -> "KP-ABE"
        }
    }
}