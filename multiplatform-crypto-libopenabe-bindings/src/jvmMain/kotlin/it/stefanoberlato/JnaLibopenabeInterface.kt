package it.stefanoberlato

import com.sun.jna.Library
import com.sun.jna.PointerType
import it.stefanoberlato.oabe.OpenABECryptoContextObject
import it.stefanoberlato.oabe.OpenPKEContextObject
import it.stefanoberlato.oabe.OpenPKSIGContextObject
import it.stefanoberlato.oabe.crypto.OpenABESymKeyHandleImplObject

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

/**
 * 0. My Utilities - wrapper utility functions
 * 1. OpenABE Utilities - functions defined in 'openabe.h'
 * 2. openABECryptoContext - functions defined in 'zcrypto_box.h' for the openABECryptoContext class
 * 3. openPKEContext - functions defined in 'zcrypto_box.h' for the OpenPKEContext class
 * 4. openABESymKeyHandleImpl - functions defined in 'zsymcrypto.h' for the OpenABESymKeyHandleImpl class
 * 4.1. zsymcrypto - functions defined in 'zsymcrypto.h'
 * 5. openPKSIGContext - functions defined in 'zcrypto_box.h' for the OpenPKSIGContext class
 */
interface JnaLibopenabeInterface : Library {

    // 0. ========== My Utilities ==========
    //  void freeString(char * string)
    fun freeString(pointerTypeString: PointerTypeString)
    // ========== end of 0. ==========

    
    
    // 1. ========== OpenABE Utilities ==========
    //  void InitializeOpenABE()
    fun InitializeOpenABE()

    //  void InitializeOpenABEwithoutOpenSSL()
    fun InitializeOpenABEwithoutOpenSSL()

    //  void ShutdownOpenABE()
    fun ShutdownOpenABE()

    //  void AssertLibInit()
    fun AssertLibInit()
    // ========== end of 1. ==========

    // 2. ========== openABECryptoContext ==========
    //  openABECryptoContext_t *openABECryptoContext_create(
    //      const char * schemeID,
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
    //      const char * keyID,
    //      int * errorCode,
    //  )
    fun openABECryptoContext_exportUserKey(
        openABECryptoContextObject: OpenABECryptoContextObject,
        keyID: String,
        errorCode: IntArray
    ): PointerTypeString

    //  void openABECryptoContext_enableKeyManager(
    //      openABECryptoContext_t *m,
    //      const char * userID
    //  )
    fun openABECryptoContext_enableKeyManager(
        openABECryptoContextObject: OpenABECryptoContextObject,
        userID: String
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
    // ========== end of 2. ==========
    
    
    
    // 3. ========== openPKEContext ==========
    //  openPKEContext_t *openPKEContext_create(
    //      const char * ecID,
    //      bool base64encode
    //  )
    fun openPKEContext_create(
        ecID: String,
        base64encode: Boolean
    ): OpenPKEContextObject

    //  void openPKEContext_destroy(
    //      openPKEContext_t *m
    //  )
    fun openPKEContext_destroy(
        openPKEContextObject: OpenPKEContextObject
    )


    // const char * openPKEContext_exportPublicKey(
    //     openPKEContext_t *m, 
    //     const char * keyID,
    //     int * errorCode
    // )
    fun openPKEContext_exportPublicKey(
        openPKEContextObject: OpenPKEContextObject,
        keyID: String,
        errorCode: IntArray
    ): PointerTypeString
    
    
    // const char * openPKEContext_exportPrivateKey(
    //     openPKEContext_t *m, 
    //     const char * keyID
    //     int * errorCode
    // )
    fun openPKEContext_exportPrivateKey(
        openPKEContextObject: OpenPKEContextObject,
        keyID: String,
        errorCode: IntArray
    ): PointerTypeString
    
    // void openPKEContext_importPublicKey(
    //     openPKEContext_t *m, 
    //     const char * keyID, 
    //     const char * keyBlob
    // )
    fun openPKEContext_importPublicKey(
        openPKEContextObject: OpenPKEContextObject,
        keyID: String,
        keyBlob: String,
    )
    
    // void openPKEContext_importPrivateKey(
    //     openPKEContext_t *m, 
    //     const char * keyID, 
    //     const char * keyBlob
    // )
    fun openPKEContext_importPrivateKey(
        openPKEContextObject: OpenPKEContextObject,
        keyID: String,
        keyBlob: String,
    )
    
    // void openPKEContext_keygen(
    //     openPKEContext_t *m, 
    //     const char * keyID
    // )
    fun openPKEContext_keygen(
        openPKEContextObject: OpenPKEContextObject,
        keyID: String
    )
    
    // const char * openPKEContext_encrypt(
    //     openPKEContext_t *m, 
    //     const char * receiverID, 
    //     const char * plaintext, 
    //     int * errorCode
    // )
    fun openPKEContext_encrypt(
        openPKEContextObject: OpenPKEContextObject,
        receiverID: String,
        plaintext: String,
        errorCode: IntArray
    ): PointerTypeString
    
    // const char * openPKEContext_decrypt(
    //     openPKEContext_t *m, 
    //     const char * receiverID, 
    //     const char * ciphertext, 
    //     int * errorCode
    // )
    fun openPKEContext_decrypt(
        openPKEContextObject: OpenPKEContextObject,
        receiverID: String,
        ciphertext: String,
        errorCode: IntArray
    ): PointerTypeString
    // ========== end of 3. ==========



    // 4. ========== openABESymKeyHandleImpl ==========
    //  openABESymKeyHandleImpl_t *openABESymKeyHandleImpl_create(
    //      const char * keyBytes,
    //      int keyBytesLen,
    //      bool apply_b64_encode
    //  )
    fun openABESymKeyHandleImpl_create(
        keyBytes: ByteArray,
        keyBytesLen: Int,
        apply_b64_encode: Boolean
    ): OpenABESymKeyHandleImplObject

    //  void openABESymKeyHandleImpl_destroy(
    //      openABESymKeyHandleImpl_t *m
    //  )
    fun openABESymKeyHandleImpl_destroy(
        openABESymKeyHandleImplObject: OpenABESymKeyHandleImplObject
    )

    // const char * openABESymKeyHandleImpl_encrypt(
    //     openABESymKeyHandleImpl_t *m,
    //     const char * plaintext,
    //     int * errorCode
    // )
    fun openABESymKeyHandleImpl_encrypt(
        openABESymKeyHandleImplObject: OpenABESymKeyHandleImplObject,
        plaintext: String,
        errorCode: IntArray
    ): PointerTypeString


    // const char * openABESymKeyHandleImpl_decrypt(
    //     openABESymKeyHandleImpl_t *m,
    //     const char * ciphertext,
    //     int * errorCode
    // )
    fun openABESymKeyHandleImpl_decrypt(
        openABESymKeyHandleImplObject: OpenABESymKeyHandleImplObject,
        ciphertext: String,
        errorCode: IntArray
    ): PointerTypeString

    // const char * exportRawKey(
    //     openABESymKeyHandleImpl_t *m
    // )
    fun openABESymKeyHandleImpl_exportRawKey(
        openABESymKeyHandleImplObject: OpenABESymKeyHandleImplObject,
    ): PointerTypeString

    // const char * exportKey(
    //     openABESymKeyHandleImpl_t *m
    // )
    fun openABESymKeyHandleImpl_exportKey(
        openABESymKeyHandleImplObject: OpenABESymKeyHandleImplObject,
    ): PointerTypeString
    // ========== end of 4 ==========


    // 4.1. ========== zsymcrypto ==========
    // const char * zsymcrypto_generateSymmetricKey(
    //     int keyLen
    // )
    fun zsymcrypto_generateSymmetricKey(
        keyLen: Int,
    ): PointerTypeString

    // const char * zsymcrypto_printAsHex(
    //     const char * binBuf
    // )
    fun zsymcrypto_printAsHex(
        binBuf: String,
    ): PointerTypeString
    // ========== end of 4.1. ==========



    // 5. ========== openPKSIGContext ==========
    //  openPKSIGContext_t *openPKSIGContext_create(
    //      const char * ecID,
    //      bool base64encode
    //  )
    fun openPKSIGContext_create(
        ecID: String,
        base64encode: Boolean
    ): OpenPKSIGContextObject

    //  void openPKSIGContext_destroy(
    //      openPKSIGContext_t *m
    //  )
    fun openPKSIGContext_destroy(
        openPKSIGContextObject: OpenPKSIGContextObject
    )

    // const char * openPKSIGContext_exportPublicKey(
    //     openPKSIGContext_t *m,
    //     const char * keyID,
    //     int * errorCode
    // );
    fun openPKSIGContext_exportPublicKey(
        openPKSIGContextObject: OpenPKSIGContextObject,
        keyID: String,
        errorCode: IntArray
    ): PointerTypeString

    // const char * openPKSIGContext_exportPrivateKey(
    //     openPKSIGContext_t *m,
    //     const char * keyID,
    //     int * errorCode
    // );
    fun openPKSIGContext_exportPrivateKey(
        openPKSIGContextObject: OpenPKSIGContextObject,
        keyID: String,
        errorCode: IntArray
    ): PointerTypeString

    // void openPKSIGContext_importPublicKey(
    //     openPKSIGContext_t *m,
    //     const char * keyID,
    //     const char * keyBlob
    // );
    fun openPKSIGContext_importPublicKey(
        openPKSIGContextObject: OpenPKSIGContextObject,
        keyID: String,
        keyBlob: String
    )

    // void openPKSIGContext_importPrivateKey(
    //     openPKSIGContext_t *m,
    //     const char * keyID,
    //     const char * keyBlob
    // );
    fun openPKSIGContext_importPrivateKey(
        openPKSIGContextObject: OpenPKSIGContextObject,
        keyID: String,
        keyBlob: String
    )

    // void openPKSIGContext_keygen(
    //     openPKSIGContext_t *m,
    //     const char * keyID
    // );
    fun openPKSIGContext_keygen(
        openPKSIGContextObject: OpenPKSIGContextObject,
        keyID: String
    )

    // const char * openPKSIGContext_sign(
    //     openPKSIGContext_t *m,
    //     const char * keyID,
    //     const char * message,
    //     int * errorCode
    // );
    fun openPKSIGContext_sign(
        openPKSIGContextObject: OpenPKSIGContextObject,
        keyID: String,
        message: String,
        errorCode: IntArray
    ): PointerTypeString

    // const char * openPKSIGContext_verify(
    //     openPKSIGContext_t *m,
    //     const char * keyID,
    //     const char * message,
    //     const char * signature,
    //     int * errorCode
    // );
    fun openPKSIGContext_verify(
        openPKSIGContextObject: OpenPKSIGContextObject,
        keyID: String,
        message: String,
        signature: String,
        errorCode: IntArray
    ): PointerTypeString
    // ========== end of 5 ==========
}
