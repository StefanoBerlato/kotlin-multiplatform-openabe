package it.stefanoberlato.oabe

/** This is an enum for error codes */
enum class State(val code: Int) {
    Success(1),
    UnexpectedError(2),
    ABEDecryptionError(3),
    ABEDeleteKeyError(4),
    ABEExportKeyError(5),
    PKEEncryptionError(6),
    PKEDecryptionError(7),
    PKEExportKeyError(8),
    SymDecryptionError(9),
    SymEncryptionError(10);

    companion object {
        fun fromInt(value: Int) = values().first { it.code == value }
    }
}