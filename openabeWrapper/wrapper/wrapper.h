#ifndef HEADER_FILE
#define HEADER_FILE

#include <stdio.h> // For 'NULL'
#include <stdbool.h> // For 'bool'

#ifdef __cplusplus
    extern "C" {
#endif


// The wrapper exposes as C-functions the APIs of OpenABE,
// and it is divided into the following blocks of code:
// 0. My Utilities - wrapper utility functions
// 1. OpenABE Utilities - functions defined in 'openabe.h'
// 2. openABECryptoContext - functions defined in 'zcrypto_box.h' for the openABECryptoContext class
// 3. openPKEContext - functions defined in 'zcrypto_box.h' for the OpenPKEContext class
// 4. openABESymKeyHandleImpl - functions defined in 'zsymcrypto.h' for the OpenABESymKeyHandleImpl class
// 4.1. zsymcrypto - functions defined in 'zsymcrypto.h'




// 0. ========== My Utilities ==========
enum State {
    Success = 1,
    UnexpectedError = 2,
    ABEDecryptionError = 3,
    ABEDeleteKeyError = 4,
    ABEExportKeyError = 5,
    PKEEncryptionError = 6,
    PKEDecryptionError = 7,
    PKEExportKeyError = 8,
    SymEncryptionError = 9,
    SymDecryptionError = 10,
};
void freeString(char * string);
// ========== end of 0 ==========




// 1. ========== OpenABE Utilities ==========
void InitializeOpenABE();
void InitializeOpenABEwithoutOpenSSL();
void ShutdownOpenABE();
void AssertLibInit();
// ========== end of 1 ==========




// 2. ========== openABECryptoContext ==========
struct openABECryptoContext;
typedef struct openABECryptoContext openABECryptoContext_t;

openABECryptoContext_t *openABECryptoContext_create(const char * schemeID, bool base64encode);
void openABECryptoContext_destroy(openABECryptoContext_t *m);
void openABECryptoContext_generateParams(openABECryptoContext_t *m);

void openABECryptoContext_keygen(openABECryptoContext_t *m, const char * keyInput, const char * keyID, const char * authID, const char * GID);
void openABECryptoContext_deleteKey(openABECryptoContext_t *m, const char * keyID, int * errorCode);

const char * openABECryptoContext_encrypt(openABECryptoContext_t *m, const char * encInput, const char * plaintext);
const char * openABECryptoContext_decryptWithKeyID(openABECryptoContext_t *m, const char * keyID, const char * ciphertext, int * errorCode);
const char * openABECryptoContext_decrypt(openABECryptoContext_t *m, const char * ciphertext, int * errorCode);

const char * openABECryptoContext_exportPublicParams(openABECryptoContext_t *m);
const char * openABECryptoContext_exportSecretParams(openABECryptoContext_t *m);
const char * openABECryptoContext_exportUserKey(openABECryptoContext_t *m, const char * keyID, int * errorCode);

void openABECryptoContext_importPublicParams(openABECryptoContext_t *m, const char * keyBlob);
void openABECryptoContext_importSecretParams(openABECryptoContext_t *m, const char * keyBlob);
void openABECryptoContext_importUserKey(openABECryptoContext_t *m, const char * keyID, const char * keyBlob);

void openABECryptoContext_enableKeyManager(openABECryptoContext_t *m, const char * userId);
void openABECryptoContext_enableVerbose(openABECryptoContext_t *m);

// See comment in wrapper.cpp for why these functions are commented out
//void openABECryptoContext_exportGlobalParams(openABECryptoContext_t *m, char * globlmpk);
//void openABECryptoContext_importGlobalParams(openABECryptoContext_t *m, const char * keyBlob);
//void openABECryptoContext_importPublicParamsWithAuthID(openABECryptoContext_t *m, const char * authID, const char * keyBlob);
//void openABECryptoContext_importSecretParamsWithAuthID(openABECryptoContext_t *m, const char * authID, const char * keyBlob);
// ========== end of 2 ==========



// 3. ========== openPKEContext ==========
struct openPKEContext;
typedef struct openPKEContext openPKEContext_t;

openPKEContext_t *openPKEContext_create(const char * ecID, bool base64encode);
void openPKEContext_destroy(openPKEContext_t *m);

const char * openPKEContext_exportPublicKey(openPKEContext_t *m, const char * keyID, int * errorCode);
const char * openPKEContext_exportPrivateKey(openPKEContext_t *m, const char * keyID, int * errorCode);

void openPKEContext_importPublicKey(openPKEContext_t *m, const char * keyID, const char * keyBlob);
void openPKEContext_importPrivateKey(openPKEContext_t *m, const char * keyID, const char * keyBlob);

void openPKEContext_keygen(openPKEContext_t *m, const char * keyID);
const char * openPKEContext_encrypt(openPKEContext_t *m, const char * receiverID, const char * plaintext, int * errorCode);
const char * openPKEContext_decrypt(openPKEContext_t *m, const char * receiverID, const char * ciphertext, int * errorCode);
// ========== end of 3 ==========



// 4. ========== openABESymKeyHandleImpl ==========
struct openABESymKeyHandleImpl;
typedef struct openABESymKeyHandleImpl openABESymKeyHandleImpl_t;

openABESymKeyHandleImpl_t *openABESymKeyHandleImpl_create(char * keyBytes, int keyBytesLen, bool apply_b64_encode);

const char * openABESymKeyHandleImpl_encrypt(openABESymKeyHandleImpl_t *m, const char * plaintext, int * errorCode);
const char * openABESymKeyHandleImpl_decrypt(openABESymKeyHandleImpl_t *m, const char * ciphertext, int * errorCode);

const char * openABESymKeyHandleImpl_exportRawKey(openABESymKeyHandleImpl_t *m);
const char * openABESymKeyHandleImpl_exportKey(openABESymKeyHandleImpl_t *m);
// ========== end of 4 ==========



// 4.1. ========== zsymcrypto ==========
const char * zsymcrypto_generateSymmetricKey(int keyLen);
const char * zsymcrypto_printAsHex(const char * binBuf);
// ========== end of 4.1. ==========

// TODO delete
int getLength(char * keyBytes);
// TODO delete

#ifdef __cplusplus
    }
#endif

#endif