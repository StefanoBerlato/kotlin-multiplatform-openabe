package it.stefanoberlato.oabe.crypto

/**
 * This is a wrapper class exposing the
 * functionalities of the corresponding
 * Zsymcrypto object in OpenABE.
 */
expect object Zsymcrypto {

    // TODO
    //  """
    //  // Hash-based key derivation function
    //  void OpenABEComputeHKDF(OpenABEByteString& key, OpenABEByteString& salt,
    //      OpenABEByteString& info, size_t key_len,
    //      OpenABEByteString& output_key);
    //  """

    /**
     * Generate a symmetric key of length [keyLen]
     * (ideally a random sequence of [keyLen] bytes)
     */
    fun generateSymmetricKey(keyLen: Int): ByteArray

    /**
     * The original documentation says about this
     * function that it is "For debug purposes only!!"
     */
    fun printAsHex(binBuf: String): String
}