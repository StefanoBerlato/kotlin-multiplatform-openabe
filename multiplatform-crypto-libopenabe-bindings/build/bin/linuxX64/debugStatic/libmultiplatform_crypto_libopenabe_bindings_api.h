#ifndef KONAN_LIBMULTIPLATFORM_CRYPTO_LIBOPENABE_BINDINGS_H
#define KONAN_LIBMULTIPLATFORM_CRYPTO_LIBOPENABE_BINDINGS_H
#ifdef __cplusplus
extern "C" {
#endif
#ifdef __cplusplus
typedef bool            libmultiplatform_crypto_libopenabe_bindings_KBoolean;
#else
typedef _Bool           libmultiplatform_crypto_libopenabe_bindings_KBoolean;
#endif
typedef unsigned short     libmultiplatform_crypto_libopenabe_bindings_KChar;
typedef signed char        libmultiplatform_crypto_libopenabe_bindings_KByte;
typedef short              libmultiplatform_crypto_libopenabe_bindings_KShort;
typedef int                libmultiplatform_crypto_libopenabe_bindings_KInt;
typedef long long          libmultiplatform_crypto_libopenabe_bindings_KLong;
typedef unsigned char      libmultiplatform_crypto_libopenabe_bindings_KUByte;
typedef unsigned short     libmultiplatform_crypto_libopenabe_bindings_KUShort;
typedef unsigned int       libmultiplatform_crypto_libopenabe_bindings_KUInt;
typedef unsigned long long libmultiplatform_crypto_libopenabe_bindings_KULong;
typedef float              libmultiplatform_crypto_libopenabe_bindings_KFloat;
typedef double             libmultiplatform_crypto_libopenabe_bindings_KDouble;
typedef float __attribute__ ((__vector_size__ (16))) libmultiplatform_crypto_libopenabe_bindings_KVector128;
typedef void*              libmultiplatform_crypto_libopenabe_bindings_KNativePtr;
struct libmultiplatform_crypto_libopenabe_bindings_KType;
typedef struct libmultiplatform_crypto_libopenabe_bindings_KType libmultiplatform_crypto_libopenabe_bindings_KType;

typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_Byte;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_Short;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_Int;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_Long;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_Float;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_Double;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_Char;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_Boolean;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_Unit;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_LibopenabeInitializer;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_Function0;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_ECID;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_ECID_NIST_P256;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_LibopenabeUtil;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_SchemeID;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContextDecrypt;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContextDestroyed;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContextError;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContextExportKey;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContextKeyDelete;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContextMissingParameters;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContextRunTime;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContext;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContextDecrypt;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContextDestroyed;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContextEncrypt;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContextError;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContextExportKey;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContextRunTime;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContext;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContextDestroyed;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContextError;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContextExportKey;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContextRunTime;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContextSign;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContextVerify;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_SchemeID_CP_ABE;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_SchemeID_KP_ABE;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State_Success;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State_UnexpectedError;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State_ABEDecryptionError;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State_ABEDeleteKeyError;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State_ABEExportKeyError;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State_PKEEncryptionError;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State_PKEDecryptionError;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State_PKEExportKeyError;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State_SymDecryptionError;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State_SymEncryptionError;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State_PKSIGExportKeyError;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State_PKSIGSignError;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State_PKSIGVerifyError;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State_Companion;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_OpenABESymKeyHandleImpl;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_ByteArray;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_OpenABESymKeyHandleImplDecrypt;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_OpenABESymKeyHandleImplDestroyed;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_OpenABESymKeyHandleImplEncrypt;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_OpenABESymKeyHandleImplError;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_OpenABESymKeyHandleImplRunTime;
typedef struct {
  libmultiplatform_crypto_libopenabe_bindings_KNativePtr pinned;
} libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_Zsymcrypto;


typedef struct {
  /* Service functions. */
  void (*DisposeStablePointer)(libmultiplatform_crypto_libopenabe_bindings_KNativePtr ptr);
  void (*DisposeString)(const char* string);
  libmultiplatform_crypto_libopenabe_bindings_KBoolean (*IsInstance)(libmultiplatform_crypto_libopenabe_bindings_KNativePtr ref, const libmultiplatform_crypto_libopenabe_bindings_KType* type);
  libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_Byte (*createNullableByte)(libmultiplatform_crypto_libopenabe_bindings_KByte);
  libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_Short (*createNullableShort)(libmultiplatform_crypto_libopenabe_bindings_KShort);
  libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_Int (*createNullableInt)(libmultiplatform_crypto_libopenabe_bindings_KInt);
  libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_Long (*createNullableLong)(libmultiplatform_crypto_libopenabe_bindings_KLong);
  libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_Float (*createNullableFloat)(libmultiplatform_crypto_libopenabe_bindings_KFloat);
  libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_Double (*createNullableDouble)(libmultiplatform_crypto_libopenabe_bindings_KDouble);
  libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_Char (*createNullableChar)(libmultiplatform_crypto_libopenabe_bindings_KChar);
  libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_Boolean (*createNullableBoolean)(libmultiplatform_crypto_libopenabe_bindings_KBoolean);
  libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_Unit (*createNullableUnit)(void);

  /* User functions. */
  struct {
    struct {
      struct {
        struct {
          struct {
            libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
            libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_LibopenabeInitializer (*_instance)();
            void (*initializeWithCallback)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_LibopenabeInitializer thiz, libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_Function0 callback);
            libmultiplatform_crypto_libopenabe_bindings_KBoolean (*isInitialized)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_LibopenabeInitializer thiz);
          } LibopenabeInitializer;
          struct {
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              const char* (*toString)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_ECID thiz);
              struct {
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_ECID (*get)(); /* enum entry for NIST_P256. */
              } NIST_P256;
            } ECID;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_LibopenabeUtil (*_instance)();
              void (*assertLibInit)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_LibopenabeUtil thiz);
              const char* (*cloneDeallocAndReturn)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_LibopenabeUtil thiz, void* pointer);
              void (*initializeOpenABE)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_LibopenabeUtil thiz);
              void (*initializeOpenABEWithoutOpenSSL)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_LibopenabeUtil thiz);
              void (*shutdownOpenABE)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_LibopenabeUtil thiz);
            } LibopenabeUtil;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext (*OpenABECryptoContext)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_SchemeID schemeID, libmultiplatform_crypto_libopenabe_bindings_KBoolean base64encode);
              void* (*get_context)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext thiz);
              libmultiplatform_crypto_libopenabe_bindings_KBoolean (*get_destroyed)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext thiz);
              void (*set_destroyed)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext thiz, libmultiplatform_crypto_libopenabe_bindings_KBoolean set);
              libmultiplatform_crypto_libopenabe_bindings_KBoolean (*get_publicParametersSetup)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext thiz);
              void (*set_publicParametersSetup)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext thiz, libmultiplatform_crypto_libopenabe_bindings_KBoolean set);
              libmultiplatform_crypto_libopenabe_bindings_KBoolean (*get_secretParametersSetup)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext thiz);
              void (*set_secretParametersSetup)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext thiz, libmultiplatform_crypto_libopenabe_bindings_KBoolean set);
              const char* (*decrypt)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext thiz, const char* ciphertext);
              const char* (*decrypt_)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext thiz, const char* keyID, const char* ciphertext);
              libmultiplatform_crypto_libopenabe_bindings_KBoolean (*deleteKey)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext thiz, const char* keyID);
              void (*destroy)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext thiz);
              void (*enableKeyManager)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext thiz, const char* userId);
              void (*enableVerbose)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext thiz);
              const char* (*encrypt)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext thiz, const char* encInput, const char* plaintext);
              const char* (*exportPublicParams)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext thiz);
              const char* (*exportSecretParams)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext thiz);
              const char* (*exportUserKey)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext thiz, const char* keyID);
              void (*generateParams)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext thiz);
              void (*importPublicParams)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext thiz, const char* keyBlob);
              void (*importSecretParams)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext thiz, const char* keyBlob);
              void (*importUserKey)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext thiz, const char* keyID, const char* keyBlob);
              void (*keygen)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContext thiz, const char* keyInput, const char* keyID, const char* authID, const char* GID);
            } OpenABECryptoContext;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContextDecrypt (*OpenABECryptoContextDecrypt)(const char* message);
            } OpenABECryptoContextDecrypt;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContextDestroyed (*OpenABECryptoContextDestroyed)(const char* message);
            } OpenABECryptoContextDestroyed;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContextError (*OpenABECryptoContextError)(const char* message);
            } OpenABECryptoContextError;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContextExportKey (*OpenABECryptoContextExportKey)(const char* message);
            } OpenABECryptoContextExportKey;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContextKeyDelete (*OpenABECryptoContextKeyDelete)(const char* message);
            } OpenABECryptoContextKeyDelete;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContextMissingParameters (*OpenABECryptoContextMissingParameters)(const char* message);
            } OpenABECryptoContextMissingParameters;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenABECryptoContextRunTime (*OpenABECryptoContextRunTime)(const char* message);
            } OpenABECryptoContextRunTime;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContext (*OpenPKEContext)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_ECID ecID, libmultiplatform_crypto_libopenabe_bindings_KBoolean base64encode);
              void* (*get_context)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContext thiz);
              libmultiplatform_crypto_libopenabe_bindings_KBoolean (*get_destroyed)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContext thiz);
              void (*set_destroyed)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContext thiz, libmultiplatform_crypto_libopenabe_bindings_KBoolean set);
              const char* (*decrypt)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContext thiz, const char* receiverID, const char* ciphertext);
              void (*destroy)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContext thiz);
              const char* (*encrypt)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContext thiz, const char* receiverID, const char* plaintext);
              const char* (*exportPrivateKey)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContext thiz, const char* keyID);
              const char* (*exportPublicKey)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContext thiz, const char* keyID);
              void (*importPrivateKey)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContext thiz, const char* keyID, const char* keyBlob);
              void (*importPublicKey)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContext thiz, const char* keyID, const char* keyBlob);
              void (*keygen)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContext thiz, const char* keyID);
            } OpenPKEContext;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContextDecrypt (*OpenPKEContextDecrypt)(const char* message);
            } OpenPKEContextDecrypt;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContextDestroyed (*OpenPKEContextDestroyed)(const char* message);
            } OpenPKEContextDestroyed;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContextEncrypt (*OpenPKEContextEncrypt)(const char* message);
            } OpenPKEContextEncrypt;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContextError (*OpenPKEContextError)(const char* message);
            } OpenPKEContextError;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContextExportKey (*OpenPKEContextExportKey)(const char* message);
            } OpenPKEContextExportKey;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKEContextRunTime (*OpenPKEContextRunTime)(const char* message);
            } OpenPKEContextRunTime;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContext (*OpenPKSIGContext)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_ECID ecID, libmultiplatform_crypto_libopenabe_bindings_KBoolean base64encode);
              void* (*get_context)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContext thiz);
              libmultiplatform_crypto_libopenabe_bindings_KBoolean (*get_destroyed)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContext thiz);
              void (*set_destroyed)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContext thiz, libmultiplatform_crypto_libopenabe_bindings_KBoolean set);
              void (*destroy)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContext thiz);
              const char* (*exportPrivateKey)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContext thiz, const char* keyID);
              const char* (*exportPublicKey)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContext thiz, const char* keyID);
              void (*importPrivateKey)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContext thiz, const char* keyID, const char* keyBlob);
              void (*importPublicKey)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContext thiz, const char* keyID, const char* keyBlob);
              void (*keygen)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContext thiz, const char* keyID);
              const char* (*sign)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContext thiz, const char* keyID, const char* message);
              void (*verify)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContext thiz, const char* keyID, const char* message, const char* signature);
            } OpenPKSIGContext;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContextDestroyed (*OpenPKSIGContextDestroyed)(const char* message);
            } OpenPKSIGContextDestroyed;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContextError (*OpenPKSIGContextError)(const char* message);
            } OpenPKSIGContextError;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContextExportKey (*OpenPKSIGContextExportKey)(const char* message);
            } OpenPKSIGContextExportKey;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContextRunTime (*OpenPKSIGContextRunTime)(const char* message);
            } OpenPKSIGContextRunTime;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContextSign (*OpenPKSIGContextSign)(const char* message);
            } OpenPKSIGContextSign;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_OpenPKSIGContextVerify (*OpenPKSIGContextVerify)(const char* message);
            } OpenPKSIGContextVerify;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              const char* (*toString)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_SchemeID thiz);
              struct {
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_SchemeID (*get)(); /* enum entry for CP_ABE. */
              } CP_ABE;
              struct {
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_SchemeID (*get)(); /* enum entry for KP_ABE. */
              } KP_ABE;
            } SchemeID;
            struct {
              libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
              libmultiplatform_crypto_libopenabe_bindings_KInt (*get_code)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State thiz);
              struct {
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State (*get)(); /* enum entry for Success. */
              } Success;
              struct {
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State (*get)(); /* enum entry for UnexpectedError. */
              } UnexpectedError;
              struct {
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State (*get)(); /* enum entry for ABEDecryptionError. */
              } ABEDecryptionError;
              struct {
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State (*get)(); /* enum entry for ABEDeleteKeyError. */
              } ABEDeleteKeyError;
              struct {
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State (*get)(); /* enum entry for ABEExportKeyError. */
              } ABEExportKeyError;
              struct {
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State (*get)(); /* enum entry for PKEEncryptionError. */
              } PKEEncryptionError;
              struct {
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State (*get)(); /* enum entry for PKEDecryptionError. */
              } PKEDecryptionError;
              struct {
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State (*get)(); /* enum entry for PKEExportKeyError. */
              } PKEExportKeyError;
              struct {
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State (*get)(); /* enum entry for SymDecryptionError. */
              } SymDecryptionError;
              struct {
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State (*get)(); /* enum entry for SymEncryptionError. */
              } SymEncryptionError;
              struct {
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State (*get)(); /* enum entry for PKSIGExportKeyError. */
              } PKSIGExportKeyError;
              struct {
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State (*get)(); /* enum entry for PKSIGSignError. */
              } PKSIGSignError;
              struct {
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State (*get)(); /* enum entry for PKSIGVerifyError. */
              } PKSIGVerifyError;
              struct {
                libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State_Companion (*_instance)();
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State (*fromInt)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_State_Companion thiz, libmultiplatform_crypto_libopenabe_bindings_KInt value);
              } Companion;
            } State;
            struct {
              struct {
                libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_OpenABESymKeyHandleImpl (*OpenABESymKeyHandleImpl)(libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_ByteArray keyBytes, libmultiplatform_crypto_libopenabe_bindings_KBoolean applyB64Encode);
                void* (*get_context)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_OpenABESymKeyHandleImpl thiz);
                libmultiplatform_crypto_libopenabe_bindings_KBoolean (*get_destroyed)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_OpenABESymKeyHandleImpl thiz);
                void (*set_destroyed)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_OpenABESymKeyHandleImpl thiz, libmultiplatform_crypto_libopenabe_bindings_KBoolean set);
                const char* (*decrypt)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_OpenABESymKeyHandleImpl thiz, const char* ciphertext);
                void (*destroy)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_OpenABESymKeyHandleImpl thiz);
                const char* (*encrypt)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_OpenABESymKeyHandleImpl thiz, const char* plaintext);
                const char* (*exportKey)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_OpenABESymKeyHandleImpl thiz);
                const char* (*exportRawKey)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_OpenABESymKeyHandleImpl thiz);
              } OpenABESymKeyHandleImpl;
              struct {
                libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_OpenABESymKeyHandleImplDecrypt (*OpenABESymKeyHandleImplDecrypt)(const char* message);
              } OpenABESymKeyHandleImplDecrypt;
              struct {
                libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_OpenABESymKeyHandleImplDestroyed (*OpenABESymKeyHandleImplDestroyed)(const char* message);
              } OpenABESymKeyHandleImplDestroyed;
              struct {
                libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_OpenABESymKeyHandleImplEncrypt (*OpenABESymKeyHandleImplEncrypt)(const char* message);
              } OpenABESymKeyHandleImplEncrypt;
              struct {
                libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_OpenABESymKeyHandleImplError (*OpenABESymKeyHandleImplError)(const char* message);
              } OpenABESymKeyHandleImplError;
              struct {
                libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_OpenABESymKeyHandleImplRunTime (*OpenABESymKeyHandleImplRunTime)(const char* message);
              } OpenABESymKeyHandleImplRunTime;
              struct {
                libmultiplatform_crypto_libopenabe_bindings_KType* (*_type)(void);
                libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_Zsymcrypto (*_instance)();
                libmultiplatform_crypto_libopenabe_bindings_kref_kotlin_ByteArray (*generateSymmetricKey)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_Zsymcrypto thiz, libmultiplatform_crypto_libopenabe_bindings_KInt keyLen);
                const char* (*printAsHex)(libmultiplatform_crypto_libopenabe_bindings_kref_it_stefanoberlato_oabe_crypto_Zsymcrypto thiz, const char* binBuf);
              } Zsymcrypto;
            } crypto;
          } oabe;
        } stefanoberlato;
      } it;
    } root;
  } kotlin;
} libmultiplatform_crypto_libopenabe_bindings_ExportedSymbols;
extern libmultiplatform_crypto_libopenabe_bindings_ExportedSymbols* libmultiplatform_crypto_libopenabe_bindings_symbols(void);
#ifdef __cplusplus
}  /* extern "C" */
#endif
#endif  /* KONAN_LIBMULTIPLATFORM_CRYPTO_LIBOPENABE_BINDINGS_H */
