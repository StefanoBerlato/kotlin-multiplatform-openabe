package it.stefanoberlato.oabe

expect object LibopenabeUtil {
    fun initializeOpenABE()
    fun initializeOpenABEWithoutOpenSSL()
    fun shutdownOpenABE()
    fun assertLibInit()
}


/** [NIST_P256] is the default EC */
enum class ECID {
    NIST_P256;

    override fun toString(): String {
        return when (this) {
            NIST_P256 -> "NIST_P256"
        }
    }
}


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