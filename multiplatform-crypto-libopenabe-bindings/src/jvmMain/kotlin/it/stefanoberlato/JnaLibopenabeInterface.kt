package it.stefanoberlato

import com.sun.jna.Library
import com.sun.jna.PointerType
import it.stefanoberlato.oabe.OpenABECryptoContextObject

/**
 * Class corresponding to char arrays dynamically allocated
 * by the C wrapper around OpenABE (which needs to be freed)
 */
class PointerTypeString : PointerType() {
    fun free() {
        LibopenabeInitializer.openabeJna.freeString(this)
    }

    val string: String
        get() = this.pointer.getString(0)
}


interface JnaLibopenabeInterface : Library {

    //  void freeString(char * string)
    fun freeString(pointerTypeString: PointerTypeString)

    //  void InitializeOpenABE()
    fun InitializeOpenABE()

    //  void InitializeOpenABEwithoutOpenSSL()
    fun InitializeOpenABEwithoutOpenSSL()

    //  void ShutdownOpenABE()
    fun ShutdownOpenABE()

    //  void AssertLibInit()
    fun AssertLibInit()

    //  openABECryptoContext_t *openABECryptoContext_create(
    //      const char * scheme_id,
    //      bool base64encode
    //  )
    fun openABECryptoContext_create(
        schemeID: String,
        base64encode: Boolean
    ): OpenABECryptoContextObject

    //  void openABECryptoContext_destroy(
    //      openABECryptoContext_t *m
    //  )
    fun openABECryptoContext_destroy(
        openABECryptoContextObject: OpenABECryptoContextObject
    )

    //  void openABECryptoContext_generateParams(
    //      openABECryptoContext_t *m
    //  )
    fun openABECryptoContext_generateParams(
        openABECryptoContextObject: OpenABECryptoContextObject
    )

    //  void openABECryptoContext_keygen(
    //      openABECryptoContext_t *m,
    //      const char * keyInput,
    //      const char * keyID,
    //      const char * authID,
    //      const char * GID
    //  )
    fun openABECryptoContext_keygen(
        openABECryptoContextObject: OpenABECryptoContextObject,
        keyInput: String,
        keyID: String,
        authID: String,
        GID: String
    )

    //  const char * openABECryptoContext_encrypt(
    //      openABECryptoContext_t *m,
    //      const char * encInput,
    //      const char * plaintext
    //  )
    fun openABECryptoContext_encrypt(
        openABECryptoContextObject: OpenABECryptoContextObject,
        encInput: String,
        plaintext: String
    ): PointerTypeString

    //  const char * openABECryptoContext_decryptWithKeyID(
    //      openABECryptoContext_t *m,
    //      const char * keyID,
    //      const char * ciphertext,
    //      int * errorCode
    //  )
    fun openABECryptoContext_decryptWithKeyID(
        openABECryptoContextObject: OpenABECryptoContextObject,
        keyID: String,
        ciphertext: String,
        errorCode: IntArray
    ): PointerTypeString


    //  const char * openABECryptoContext_decrypt(
    //      openABECryptoContext_t *m,
    //      const char * ciphertext,
    //      int * errorCode,
    //      errorCode: IntArray
    //  )
    fun openABECryptoContext_decrypt(
        openABECryptoContextObject: OpenABECryptoContextObject,
        ciphertext: String,
        errorCode: IntArray
    ): PointerTypeString

    //  const char * openABECryptoContext_exportPublicParams(
    //      openABECryptoContext_t *m
    //  )
    fun openABECryptoContext_exportPublicParams(
        openABECryptoContextObject: OpenABECryptoContextObject
    ): PointerTypeString

    //  const char * openABECryptoContext_exportSecretParams(
    //      openABECryptoContext_t *m
    //  )
    fun openABECryptoContext_exportSecretParams(
        openABECryptoContextObject: OpenABECryptoContextObject
    ): PointerTypeString

    //  const char * openABECryptoContext_exportUserKey(
    //      openABECryptoContext_t *m,
    //      const char * keyID
    //  )
    fun openABECryptoContext_exportUserKey(
        openABECryptoContextObject: OpenABECryptoContextObject,
        keyID: String
    ): PointerTypeString

    //  void openABECryptoContext_enableKeyManager(
    //      openABECryptoContext_t *m,
    //      const char * userId
    //  )
    fun openABECryptoContext_enableKeyManager(
        openABECryptoContextObject: OpenABECryptoContextObject,
        userId: String
    )

    //  void openABECryptoContext_enableVerbose(
    //      openABECryptoContext_t *m
    //  )
    fun openABECryptoContext_enableVerbose(
        openABECryptoContextObject: OpenABECryptoContextObject,
    )

    //  void openABECryptoContext_importPublicParams(
    //      openABECryptoContext_t *m,
    //      const char * keyBlob
    //  )
    fun openABECryptoContext_importPublicParams(
        openABECryptoContextObject: OpenABECryptoContextObject,
        keyBlob: String
    )

    //  void openABECryptoContext_importSecretParams(
    //      openABECryptoContext_t *m,
    //      const char * keyBlob
    //  )
    fun openABECryptoContext_importSecretParams(
        openABECryptoContextObject: OpenABECryptoContextObject,
        keyBlob: String
    )

    //  void openABECryptoContext_importUserKey(
    //      openABECryptoContext_t *m,
    //      const char * keyID,
    //      const char * keyBlob
    //  )
    fun openABECryptoContext_importUserKey(
        openABECryptoContextObject: OpenABECryptoContextObject,
        keyID: String,
        keyBlob: String
    )

    //  void openABECryptoContext_deleteKey(
    //      openABECryptoContext_t *m,
    //      const char * keyID,
    //      int * errorCode
    //  )
    fun openABECryptoContext_deleteKey(
        openABECryptoContextObject: OpenABECryptoContextObject,
        keyID: String,
        errorCode: IntArray
    )
}