#include "wrapper.h"

#include <string>
#include <cassert>
#include <stdint.h>
#include <openabe/openabe.h>
#include <openabe/zsymcrypto.h>

using namespace std;
using namespace oabe;
using namespace oabe::crypto;


// 0. ========== My Utilities ==========
void freeString(
    char * stringToFree
) {
    free(stringToFree);
}
// ========== end of 0. ==========




// 1. ========== OpenABE Utilities ==========
void InitializeOpenABE() {
    oabe::InitializeOpenABE();
}

void InitializeOpenABEwithoutOpenSSL() {
    oabe::InitializeOpenABEwithoutOpenSSL();
}

void ShutdownOpenABE() {
    oabe::ShutdownOpenABE();
}

void AssertLibInit() {
    oabe::AssertLibInit();
}
// ========== end of 1. ==========




// 2. ========== openABECryptoContext ==========
struct openABECryptoContext {
	void *obj;
};

openABECryptoContext_t *openABECryptoContext_create(
    const char * schemeID,
    bool base64encode
) {
	openABECryptoContext_t *m;
	OpenABECryptoContext *obj;

	m      = (typeof(m))malloc(sizeof(*m));
	obj    = new OpenABECryptoContext(schemeID, base64encode);
	m->obj = obj;

	return m;
}

void openABECryptoContext_destroy(
    openABECryptoContext_t *m
) {
	delete static_cast<OpenABECryptoContext *>(m->obj);
	free(m);
}

void openABECryptoContext_generateParams(
    openABECryptoContext_t *m
) {
	OpenABECryptoContext *obj;
	obj = static_cast<OpenABECryptoContext *>(m->obj);
	obj->generateParams();
}

void openABECryptoContext_enableKeyManager(
    openABECryptoContext_t *m,
    const char * userID
) {
	OpenABECryptoContext *obj;
	obj = static_cast<OpenABECryptoContext *>(m->obj);
	obj->enableKeyManager(userID);
}

void openABECryptoContext_enableVerbose(
    openABECryptoContext_t *m
) {
	OpenABECryptoContext *obj;
	obj = static_cast<OpenABECryptoContext *>(m->obj);
	obj->enableVerbose();
}

void openABECryptoContext_keygen(
    openABECryptoContext_t *m,
    const char * keyInput,
    const char * keyID,
    const char * authID,
    const char * GID
) {
	OpenABECryptoContext *obj;
	obj = static_cast<OpenABECryptoContext *>(m->obj);
	obj->keygen(keyInput, keyID, authID, GID);
}

const char * openABECryptoContext_encrypt(
    openABECryptoContext_t *m,
    const char * encInput,
    const char * plaintext
) {
	OpenABECryptoContext *obj;
    string buffer;
	obj = static_cast<OpenABECryptoContext *>(m->obj);
	obj->encrypt(encInput, plaintext, buffer);
	return strdup(buffer.c_str());
}

// If an error occurs, set the proper error code
// and return an error message describing the error
const char * openABECryptoContext_decryptWithKeyID(
    openABECryptoContext_t *m,
    const char * keyID,
    const char * ciphertext,
    int * errorCode
) {
    try {
        OpenABECryptoContext *obj;
        string buffer;
        obj = static_cast<OpenABECryptoContext *>(m->obj);
        bool result = obj->decrypt(keyID, ciphertext, buffer);
        if (result) {
            errorCode[0] = Success;
            return strdup(buffer.c_str());
        } else {
            errorCode[0] = ABEDecryptionError;
            return strdup("Error during ABE decryption, probably the"
                   " ciphertext corrupted or the key does not satisfy the policy");
        }
	} catch (const ZCryptoBoxException& ex) {
        errorCode[0] = ABEDecryptionError;
        string exceptionMessage(ex.what());
        return strdup(("ABE ZCryptoBoxException: " + exceptionMessage).c_str());
    } catch (...) {
         errorCode[0] = ABEDecryptionError;
         return strdup("ABE decryptWithKeyID: unknown exception");
     }
}

// If an error occurs, set the proper error code
// and return an error message describing the error
const char * openABECryptoContext_decrypt(
    openABECryptoContext_t *m,
    const char * ciphertext,
    int * errorCode
) {
    try {
        OpenABECryptoContext *obj;
        string buffer;
        obj = static_cast<OpenABECryptoContext *>(m->obj);
        bool result = obj->decrypt(ciphertext, buffer);
        if (result) {
            errorCode[0] = Success;
            return strdup(buffer.c_str());
        } else {
            errorCode[0] = ABEDecryptionError;
            return strdup("Error during ABE decryption, probably the"
                   " ciphertext corrupted or the key does not satisfy the policy");
        }
    } catch (const ZCryptoBoxException& ex) {
        errorCode[0] = ABEDecryptionError;
        string exceptionMessage(ex.what());
        return strdup(("ABE ZCryptoBoxException: " + exceptionMessage).c_str());
    } catch (...) {
         errorCode[0] = ABEDecryptionError;
         return strdup("ABE decrypt: unknown exception");
     }
}

const char * openABECryptoContext_exportPublicParams(
    openABECryptoContext_t *m
) {
  	OpenABECryptoContext *obj;
    string buffer;
  	obj = static_cast<OpenABECryptoContext *>(m->obj);
  	obj->exportPublicParams(buffer);
  	return strdup(buffer.c_str());
}

const char * openABECryptoContext_exportSecretParams(
    openABECryptoContext_t *m
) {
  	OpenABECryptoContext *obj;
    string buffer;
  	obj = static_cast<OpenABECryptoContext *>(m->obj);
  	obj->exportSecretParams(buffer);
  	return strdup(buffer.c_str());
}

// If an error occurs, set the proper error code
// and return an error message describing the error
const char * openABECryptoContext_exportUserKey(
    openABECryptoContext_t *m,
    const char * keyID,
    int * errorCode
) {
    try {
        OpenABECryptoContext *obj;
        string buffer;
        obj = static_cast<OpenABECryptoContext *>(m->obj);
        obj->exportUserKey(keyID, buffer);
        errorCode[0] = Success;
        return strdup(buffer.c_str());
    } catch (const ZCryptoBoxException& ex) {
        errorCode[0] = ABEExportKeyError;
        string exceptionMessage(ex.what());
        return strdup(("ABE ZCryptoBoxException: " + exceptionMessage).c_str());
    } catch (...) {
         errorCode[0] = ABEExportKeyError;
         return strdup("ABE exportUserKey: unknown exception");
     }
}

void openABECryptoContext_importPublicParams(
    openABECryptoContext_t *m,
    const char * keyBlob
) {
  	OpenABECryptoContext *obj;
  	obj = static_cast<OpenABECryptoContext *>(m->obj);
  	obj->importPublicParams(keyBlob);
}

void openABECryptoContext_importSecretParams(
    openABECryptoContext_t *m,
    const char * keyBlob
) {
  	OpenABECryptoContext *obj;
  	obj = static_cast<OpenABECryptoContext *>(m->obj);
  	obj->importSecretParams(keyBlob);
}

void openABECryptoContext_importUserKey(
    openABECryptoContext_t *m,
    const char * keyID,
    const char * keyBlob
) {
    OpenABECryptoContext *obj;
    obj = static_cast<OpenABECryptoContext *>(m->obj);
    obj->importUserKey(keyID, keyBlob);
}

void openABECryptoContext_deleteKey(
    openABECryptoContext_t *m,
    const char * keyID,
    int * errorCode
) {
    OpenABECryptoContext *obj;
    obj = static_cast<OpenABECryptoContext *>(m->obj);
    if (obj->deleteKey(keyID)) {
        errorCode[0] = Success;
    } else {
        errorCode[0] = ABEDeleteKeyError;
    }
}



// These functions below are marked as available
// in the header files. However, the import functions are available,
// while the export functions are are not. This should be because multi
// attribute ABE is available in the "premium" version of OpenABE only.
// The premium version seems to not be available even for purchase anymore
/*

void openABECryptoContext_importPublicParamsWithAuthID(
    openABECryptoContext_t *m,
    const char * authID,
    const char * keyBlob
) {
    OpenABECryptoContext *obj;
    obj = static_cast<OpenABECryptoContext *>(m->obj);
    obj->importPublicParams(authID, keyBlob);
}

void openABECryptoContext_importSecretParamsWithAuthID(
    openABECryptoContext_t *m,
    const char * authID,
    const char * keyBlob
) {
    OpenABECryptoContext *obj;
    obj = static_cast<OpenABECryptoContext *>(m->obj);
    obj->importSecretParams(authID, keyBlob);
}

void openABECryptoContext_exportGlobalParams(
    openABECryptoContext_t *m,
    char * globlmpk
) {
  	OpenABECryptoContext *obj;

  	if (m == NULL)
  		return;

    string buffer;
  	obj = static_cast<OpenABECryptoContext *>(m->obj);
  	obj->exportGlobalParams(buffer);
  	strcpy(globlmpk, buffer.c_str());
}

void openABECryptoContext_importGlobalParams(
    openABECryptoContext_t *m,
    const char * keyBlob
) {
  	OpenABECryptoContext *obj;

  	if (m == NULL)
  		return;

  	obj = static_cast<OpenABECryptoContext *>(m->obj);
    obj->importGlobalParams(keyBlob);
}
*/
// ========== end of 2. ==========



// 3. ========== openPKEContext ==========
struct openPKEContext {
	void *obj;
};

openPKEContext_t *openPKEContext_create(
    const char * ecID,
    bool base64encode
) {
	openPKEContext_t *m;
	OpenPKEContext *obj;

	m      = (typeof(m))malloc(sizeof(*m));
	obj    = new OpenPKEContext(ecID, base64encode);
	m->obj = obj;

	return m;
}

void openPKEContext_destroy(
    openPKEContext_t *m
) {
	delete static_cast<OpenPKEContext *>(m->obj);
	free(m);
}

const char * openPKEContext_exportPublicKey(
    openPKEContext_t *m,
    const char * keyID,
    int * errorCode
) {
    try {
        OpenPKEContext *obj;
        string buffer;
        obj = static_cast<OpenPKEContext *>(m->obj);
        obj->exportPublicKey(keyID, buffer);
        errorCode[0] = Success;
        return strdup(buffer.c_str());
  	} catch (const ZCryptoBoxException& ex) {
        errorCode[0] = PKEExportKeyError;
        string exceptionMessage(ex.what());
        return strdup(("PKE ZCryptoBoxException: " + exceptionMessage).c_str());
    } catch (...) {
        errorCode[0] = PKEExportKeyError;
        return strdup("PKE exportPublicKey: unknown exception");
    }
}

const char * openPKEContext_exportPrivateKey(
    openPKEContext_t *m,
    const char * keyID,
    int * errorCode
) {
    try {
  	    OpenPKEContext *obj;
        string buffer;
        obj = static_cast<OpenPKEContext *>(m->obj);
        obj->exportPrivateKey(keyID, buffer);
        errorCode[0] = Success;
        return strdup(buffer.c_str());
    } catch (const ZCryptoBoxException& ex) {
        errorCode[0] = PKEExportKeyError;
        string exceptionMessage(ex.what());
        return strdup(("PKE ZCryptoBoxException: " + exceptionMessage).c_str());
    } catch (...) {
        errorCode[0] = PKEExportKeyError;
        return strdup("PKE exportPrivateKey: unknown exception");
    }
}

void openPKEContext_importPublicKey(
    openPKEContext_t *m,
    const char * keyID,
    const char * keyBlob
) {
  	OpenPKEContext *obj;
    obj = static_cast<OpenPKEContext *>(m->obj);
  	obj->importPublicKey(keyID, keyBlob);
}

void openPKEContext_importPrivateKey(
    openPKEContext_t *m,
    const char * keyID,
    const char * keyBlob
) {
  	OpenPKEContext *obj;
    obj = static_cast<OpenPKEContext *>(m->obj);
  	obj->importPrivateKey(keyID, keyBlob);
}

void openPKEContext_keygen(
    openPKEContext_t *m,
    const char * keyID
) {
  	OpenPKEContext *obj;
    obj = static_cast<OpenPKEContext *>(m->obj);
  	obj->keygen(keyID);
}

// If an error occurs, set the proper error code
// and return an error message describing the error
const char * openPKEContext_encrypt(
    openPKEContext_t *m,
    const char * receiverID,
    const char * plaintext,
    int * errorCode
) {
    try {
        OpenPKEContext *obj;
        string buffer;
        obj = static_cast<OpenPKEContext *>(m->obj);
        bool result = obj->encrypt(receiverID, plaintext, buffer);
        if (result) {
            errorCode[0] = Success;
            return strdup(buffer.c_str());
        } else {
            errorCode[0] = PKEEncryptionError;
            return strdup("Error during PKE encryption");
        }
    } catch (const ZCryptoBoxException& ex) {
        errorCode[0] = PKEEncryptionError;
        string exceptionMessage(ex.what());
        return strdup(("PKE ZCryptoBoxException: " + exceptionMessage).c_str());
    } catch (...) {
         errorCode[0] = PKEEncryptionError;
         return strdup("PKE encrypt: unknown exception");
     }
}

// If an error occurs, set the proper error code
// and return an error message describing the error
const char * openPKEContext_decrypt(
    openPKEContext_t *m,
    const char * receiverID,
    const char * ciphertext,
    int * errorCode
) {
    try {
        OpenPKEContext *obj;
        string buffer;
        obj = static_cast<OpenPKEContext *>(m->obj);
        bool result = obj->decrypt(receiverID, ciphertext, buffer);
        if (result) {
            errorCode[0] = Success;
            return strdup(buffer.c_str());
        } else {
            errorCode[0] = PKEDecryptionError;
            return strdup("Error during PKE decryption");
        }
    } catch (const ZCryptoBoxException& ex) {
        errorCode[0] = PKEDecryptionError;
        string exceptionMessage(ex.what());
        return strdup(("PKE ZCryptoBoxException: " + exceptionMessage).c_str());
    } catch (...) {
        errorCode[0] = PKEDecryptionError;
        return strdup("PKE decrypt: unknown exception");
    }
}
// ========== end of 3. ==========



// 4. ========== openABESymKeyHandleImpl ==========
struct openABESymKeyHandleImpl {
	void *obj;
};

openABESymKeyHandleImpl_t *openABESymKeyHandleImpl_create(
    char * keyBytes,
    int keyBytesLen,
    bool apply_b64_encode
) {
	openABESymKeyHandleImpl_t *m;
	OpenABESymKeyHandleImpl *obj;

	m      = (typeof(m))malloc(sizeof(*m));
	std::string keyString(keyBytes, keyBytesLen);
	obj    = new OpenABESymKeyHandleImpl(keyString, apply_b64_encode);
	m->obj = obj;

	return m;
}

void openABESymKeyHandleImpl_destroy(
    openABESymKeyHandleImpl_t *m
) {
	delete static_cast<OpenABESymKeyHandleImpl *>(m->obj);
	free(m);
}

// If an error occurs, set the proper error code
// and return an error message describing the error
const char * openABESymKeyHandleImpl_encrypt(
    openABESymKeyHandleImpl_t *m,
    const char * plaintext,
    int * errorCode
) {
    try {
        OpenABESymKeyHandleImpl *obj;
        string buffer;
        obj = static_cast<OpenABESymKeyHandleImpl *>(m->obj);
        obj->encrypt(buffer, plaintext);
        errorCode[0] = Success;
        return strdup(buffer.c_str());
    } catch (const ZCryptoBoxException& ex) {
        errorCode[0] = SymEncryptionError;
        string exceptionMessage(ex.what());
        return strdup(("Sym ZCryptoBoxException: " + exceptionMessage).c_str());
    } catch (...) {
         errorCode[0] = SymEncryptionError;
         return strdup("Sym encrypt: unknown exception");
     }
}

// If an error occurs, set the proper error code
// and return an error message describing the error
const char * openABESymKeyHandleImpl_decrypt(
    openABESymKeyHandleImpl_t *m,
    const char * ciphertext,
    int * errorCode
) {
    try {
        OpenABESymKeyHandleImpl *obj;
        string buffer;
        obj = static_cast<OpenABESymKeyHandleImpl *>(m->obj);
        obj->decrypt(buffer, ciphertext);
        errorCode[0] = Success;
        return strdup(buffer.c_str());
    } catch (const ZCryptoBoxException& ex) {
        errorCode[0] = SymDecryptionError;
        string exceptionMessage(ex.what());
        return strdup(("Sym ZCryptoBoxException: " + exceptionMessage).c_str());
    } catch (...) {
         errorCode[0] = SymDecryptionError;
         return strdup("Sym decrypt: unknown exception");
    }
}

const char * openABESymKeyHandleImpl_exportRawKey(
    openABESymKeyHandleImpl_t *m
) {
    OpenABESymKeyHandleImpl *obj;
    string buffer;
    obj = static_cast<OpenABESymKeyHandleImpl *>(m->obj);
    obj->exportRawKey(buffer);
    return strdup(buffer.c_str());
}

const char * openABESymKeyHandleImpl_exportKey(
    openABESymKeyHandleImpl_t *m
) {
    OpenABESymKeyHandleImpl *obj;
    string buffer;
    obj = static_cast<OpenABESymKeyHandleImpl *>(m->obj);
    obj->exportKey(buffer);
    return strdup(buffer.c_str());
}
// ========== end of 4. ==========



// 4.1. ========== zsymcrypto ==========
const char * zsymcrypto_generateSymmetricKey(
    int keyLen
) {
    string buffer;
    generateSymmetricKey(buffer, ((unsigned int) keyLen));
    return strdup(buffer.c_str());
}

// Original documentation says: "For debug purposes only!!"
const char * zsymcrypto_printAsHex(
    const char * binBuf
) {
    string result = oabe::crypto::printAsHex(binBuf);
    return strdup(result.c_str());
}
// ========== end of 4.1. ==========



// 5. ========== openPKSIGContext ==========
struct openPKSIGContext {
	void *obj;
};

openPKSIGContext_t *openPKSIGContext_create(
    const char * ecID,
    bool base64encode
) {
	openPKSIGContext_t *m;
	OpenPKSIGContext *obj;

	m      = (typeof(m))malloc(sizeof(*m));
	obj    = new OpenPKSIGContext(ecID, base64encode);
	m->obj = obj;

	return m;
}

void openPKSIGContext_destroy(
    openPKSIGContext_t *m
) {
	delete static_cast<OpenPKSIGContext *>(m->obj);
	free(m);
}

// If an error occurs, set the proper error code
// and return an error message describing the error
const char * openPKSIGContext_exportPublicKey(
    openPKSIGContext_t *m,
    const char * keyID,
    int * errorCode
) {
    try {
        OpenPKSIGContext *obj;
        string buffer;
        obj = static_cast<OpenPKSIGContext *>(m->obj);
        obj->exportPublicKey(keyID, buffer);
        errorCode[0] = Success;
        return strdup(buffer.c_str());
    } catch (const ZCryptoBoxException& ex) {
        errorCode[0] = PKSIGExportKeyError;
        string exceptionMessage(ex.what());
        return strdup(("PKSIG ZCryptoBoxException: " + exceptionMessage).c_str());
    } catch (...) {
        errorCode[0] = PKSIGExportKeyError;
        return strdup("PKSIG exportPrivateKey: unknown exception");
    }
}

// If an error occurs, set the proper error code
// and return an error message describing the error
const char * openPKSIGContext_exportPrivateKey(
    openPKSIGContext_t *m,
    const char * keyID,
    int * errorCode
) {
    try {
        OpenPKSIGContext *obj;
        string buffer;
        obj = static_cast<OpenPKSIGContext *>(m->obj);
        obj->exportPrivateKey(keyID, buffer);
        errorCode[0] = Success;
        return strdup(buffer.c_str());
    } catch (const ZCryptoBoxException& ex) {
        errorCode[0] = PKSIGExportKeyError;
        string exceptionMessage(ex.what());
        return strdup(("PKSIG ZCryptoBoxException: " + exceptionMessage).c_str());
    } catch (...) {
        errorCode[0] = PKSIGExportKeyError;
        return strdup("PKSIG exportPrivateKey: unknown exception");
    }
}

void openPKSIGContext_importPublicKey(
    openPKSIGContext_t *m,
    const char * keyID,
    const char * keyBlob
) {
    OpenPKSIGContext *obj;
    obj = static_cast<OpenPKSIGContext *>(m->obj);
    obj->importPublicKey(keyID, keyBlob);
}

void openPKSIGContext_importPrivateKey(
    openPKSIGContext_t *m,
    const char * keyID,
    const char * keyBlob
) {
    OpenPKSIGContext *obj;
    obj = static_cast<OpenPKSIGContext *>(m->obj);
    obj->importPrivateKey(keyID, keyBlob);
}

void openPKSIGContext_keygen(
    openPKSIGContext_t *m,
    const char * keyID
) {
    OpenPKSIGContext *obj;
    obj = static_cast<OpenPKSIGContext *>(m->obj);
  	obj->keygen(keyID);
}

// If an error occurs, set the proper error code
// and return an error message describing the error
const char * openPKSIGContext_sign(
    openPKSIGContext_t *m,
    const char * keyID,
    const char * message,
    int * errorCode
) {
    try {
        OpenPKSIGContext *obj;
        string buffer;
        obj = static_cast<OpenPKSIGContext *>(m->obj);
        obj->sign(keyID, message, buffer);
        errorCode[0] = Success;
        return strdup(buffer.c_str());
    } catch (const ZCryptoBoxException& ex) {
        errorCode[0] = PKSIGSignError;
        string exceptionMessage(ex.what());
        return strdup(("PKSIG ZCryptoBoxException: " + exceptionMessage).c_str());
    } catch (...) {
         errorCode[0] = PKSIGSignError;
         return strdup("PKSIG sign: unknown exception");
    }
}

// If an error occurs, set the proper error code
// and return an error message describing the error
const char * openPKSIGContext_verify(
    openPKSIGContext_t *m,
    const char * keyID,
    const char * message,
    const char * signature,
    int * errorCode
) {
    try {
        OpenPKSIGContext *obj;
        obj = static_cast<OpenPKSIGContext *>(m->obj);
        bool result = obj->verify(keyID, message, signature);
        if (result) {
            errorCode[0] = Success;
            return strdup("Success");
        } else {
            errorCode[0] = PKSIGVerifyError;
            return strdup("Error during PKSIG verify, probably the"
                   " signature is not correct or the message was tampered");
        }
    } catch (const ZCryptoBoxException& ex) {
        errorCode[0] = PKSIGVerifyError;
        string exceptionMessage(ex.what());
        return strdup(("PKSIG ZCryptoBoxException: " + exceptionMessage).c_str());
    } catch (...) {
         errorCode[0] = PKSIGVerifyError;
         return strdup("PKSIG verify: unknown exception");
     }
}
// ========== end of 5. ==========

