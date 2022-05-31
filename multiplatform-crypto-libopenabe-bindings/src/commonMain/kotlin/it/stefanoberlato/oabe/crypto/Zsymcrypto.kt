package it.stefanoberlato.oabe.crypto

expect object Zsymcrypto {

    // TODO
    //  """
    //  // Hash-based key derivation function
    //  void OpenABEComputeHKDF(OpenABEByteString& key, OpenABEByteString& salt,
    //      OpenABEByteString& info, size_t key_len,
    //      OpenABEByteString& output_key);
    //  """

    fun generateSymmetricKey(keyLen: Int): ByteArray

    /**
     * The original documentation says about this
     * function that it is "For debug purposes only!!"
     */
    fun printAsHex(binBuf: String): String
}