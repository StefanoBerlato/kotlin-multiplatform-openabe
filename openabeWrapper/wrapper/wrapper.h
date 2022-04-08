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




// 0. ========== My Utilities ==========
enum State {
    Success = 1,
    UnexpectedError = 2,
    DecryptionError = 3,
    DeleteKeyError = 4,
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

openABECryptoContext_t *openABECryptoContext_create(const char * scheme_id, bool base64encode);
void openABECryptoContext_destroy(openABECryptoContext_t *m);
void openABECryptoContext_generateParams(openABECryptoContext_t *m);

void openABECryptoContext_keygen(openABECryptoContext_t *m, const char * keyInput, const char * keyID, const char * authID, const char * GID);
void openABECryptoContext_deleteKey(openABECryptoContext_t *m, const char * keyID, int * errorCode);

const char * openABECryptoContext_encrypt(openABECryptoContext_t *m, const char * encInput, const char * plaintext);
const char * openABECryptoContext_decryptWithKeyID(openABECryptoContext_t *m, const char * keyID, const char * ciphertext, int * errorCode);
const char * openABECryptoContext_decrypt(openABECryptoContext_t *m, const char * ciphertext, int * errorCode);

const char * openABECryptoContext_exportPublicParams(openABECryptoContext_t *m);
const char * openABECryptoContext_exportSecretParams(openABECryptoContext_t *m);
const char * openABECryptoContext_exportUserKey(openABECryptoContext_t *m, const char * keyID);

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




#ifdef __cplusplus
    }
#endif

#endif