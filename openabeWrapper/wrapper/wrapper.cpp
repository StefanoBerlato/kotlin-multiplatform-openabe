#include "wrapper.h"

#include <iostream>
#include <string>
#include <cassert>
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
// ========== end of 0 ==========




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
// ========== end of 1 ==========




// 2. ========== openABECryptoContext ==========
struct openABECryptoContext {
	void *obj;
};

openABECryptoContext_t *openABECryptoContext_create(
    const char * scheme_id,
    bool base64encode
) {
	openABECryptoContext_t *m;
	OpenABECryptoContext *obj;

	m      = (typeof(m))malloc(sizeof(*m));
	obj    = new OpenABECryptoContext(scheme_id, base64encode);
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
    const char * userId
) {
	OpenABECryptoContext *obj;
	obj = static_cast<OpenABECryptoContext *>(m->obj);
	obj->enableKeyManager(userId);
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

const char * openABECryptoContext_decryptWithKeyID(
    openABECryptoContext_t *m,
    const char * keyID,
    const char * ciphertext,
    int * errorCode
) {
	OpenABECryptoContext *obj;
    string buffer;
	obj = static_cast<OpenABECryptoContext *>(m->obj);
	bool result = obj->decrypt(keyID, ciphertext, buffer);
	if (result) {
	    errorCode[0] = Success;
		return strdup(buffer.c_str());
	} else {
	    errorCode[0] = DecryptionError;
        return NULL;
	}
}

const char * openABECryptoContext_decrypt(
    openABECryptoContext_t *m,
    const char * ciphertext,
    int * errorCode
) {
	OpenABECryptoContext *obj;
    string buffer;
    obj = static_cast<OpenABECryptoContext *>(m->obj);
    bool result = obj->decrypt(ciphertext, buffer);
	if (result) {
	    errorCode[0] = Success;
		return strdup(buffer.c_str());
	} else {
	    errorCode[0] = DecryptionError;
        return NULL;
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

const char * openABECryptoContext_exportUserKey(
    openABECryptoContext_t *m,
    const char * keyID
) {
    OpenABECryptoContext *obj;
    string buffer;
    obj = static_cast<OpenABECryptoContext *>(m->obj);
    obj->exportUserKey(keyID, buffer);
    return strdup(buffer.c_str());
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
        errorCode[0] = DeleteKeyError;
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
// ========== end of 2 ==========