#ifndef KONAN_LIBMULTIPLATFORM_CRYPTO_API_H
#define KONAN_LIBMULTIPLATFORM_CRYPTO_API_H
#ifdef __cplusplus
extern "C" {
#endif
#ifdef __cplusplus
typedef bool            libmultiplatform_crypto_api_KBoolean;
#else
typedef _Bool           libmultiplatform_crypto_api_KBoolean;
#endif
typedef unsigned short     libmultiplatform_crypto_api_KChar;
typedef signed char        libmultiplatform_crypto_api_KByte;
typedef short              libmultiplatform_crypto_api_KShort;
typedef int                libmultiplatform_crypto_api_KInt;
typedef long long          libmultiplatform_crypto_api_KLong;
typedef unsigned char      libmultiplatform_crypto_api_KUByte;
typedef unsigned short     libmultiplatform_crypto_api_KUShort;
typedef unsigned int       libmultiplatform_crypto_api_KUInt;
typedef unsigned long long libmultiplatform_crypto_api_KULong;
typedef float              libmultiplatform_crypto_api_KFloat;
typedef double             libmultiplatform_crypto_api_KDouble;
typedef float __attribute__ ((__vector_size__ (16))) libmultiplatform_crypto_api_KVector128;
typedef void*              libmultiplatform_crypto_api_KNativePtr;
struct libmultiplatform_crypto_api_KType;
typedef struct libmultiplatform_crypto_api_KType libmultiplatform_crypto_api_KType;

typedef struct {
  libmultiplatform_crypto_api_KNativePtr pinned;
} libmultiplatform_crypto_api_kref_kotlin_Byte;
typedef struct {
  libmultiplatform_crypto_api_KNativePtr pinned;
} libmultiplatform_crypto_api_kref_kotlin_Short;
typedef struct {
  libmultiplatform_crypto_api_KNativePtr pinned;
} libmultiplatform_crypto_api_kref_kotlin_Int;
typedef struct {
  libmultiplatform_crypto_api_KNativePtr pinned;
} libmultiplatform_crypto_api_kref_kotlin_Long;
typedef struct {
  libmultiplatform_crypto_api_KNativePtr pinned;
} libmultiplatform_crypto_api_kref_kotlin_Float;
typedef struct {
  libmultiplatform_crypto_api_KNativePtr pinned;
} libmultiplatform_crypto_api_kref_kotlin_Double;
typedef struct {
  libmultiplatform_crypto_api_KNativePtr pinned;
} libmultiplatform_crypto_api_kref_kotlin_Char;
typedef struct {
  libmultiplatform_crypto_api_KNativePtr pinned;
} libmultiplatform_crypto_api_kref_kotlin_Boolean;
typedef struct {
  libmultiplatform_crypto_api_KNativePtr pinned;
} libmultiplatform_crypto_api_kref_kotlin_Unit;
typedef struct {
  libmultiplatform_crypto_api_KNativePtr pinned;
} libmultiplatform_crypto_api_kref_PrimitivesApi;


typedef struct {
  /* Service functions. */
  void (*DisposeStablePointer)(libmultiplatform_crypto_api_KNativePtr ptr);
  void (*DisposeString)(const char* string);
  libmultiplatform_crypto_api_KBoolean (*IsInstance)(libmultiplatform_crypto_api_KNativePtr ref, const libmultiplatform_crypto_api_KType* type);
  libmultiplatform_crypto_api_kref_kotlin_Byte (*createNullableByte)(libmultiplatform_crypto_api_KByte);
  libmultiplatform_crypto_api_kref_kotlin_Short (*createNullableShort)(libmultiplatform_crypto_api_KShort);
  libmultiplatform_crypto_api_kref_kotlin_Int (*createNullableInt)(libmultiplatform_crypto_api_KInt);
  libmultiplatform_crypto_api_kref_kotlin_Long (*createNullableLong)(libmultiplatform_crypto_api_KLong);
  libmultiplatform_crypto_api_kref_kotlin_Float (*createNullableFloat)(libmultiplatform_crypto_api_KFloat);
  libmultiplatform_crypto_api_kref_kotlin_Double (*createNullableDouble)(libmultiplatform_crypto_api_KDouble);
  libmultiplatform_crypto_api_kref_kotlin_Char (*createNullableChar)(libmultiplatform_crypto_api_KChar);
  libmultiplatform_crypto_api_kref_kotlin_Boolean (*createNullableBoolean)(libmultiplatform_crypto_api_KBoolean);
  libmultiplatform_crypto_api_kref_kotlin_Unit (*createNullableUnit)(void);

  /* User functions. */
  struct {
    struct {
      struct {
        libmultiplatform_crypto_api_KType* (*_type)(void);
        void (*primitiveFunction)(libmultiplatform_crypto_api_kref_PrimitivesApi thiz);
      } PrimitivesApi;
    } root;
  } kotlin;
} libmultiplatform_crypto_api_ExportedSymbols;
extern libmultiplatform_crypto_api_ExportedSymbols* libmultiplatform_crypto_api_symbols(void);
#ifdef __cplusplus
}  /* extern "C" */
#endif
#endif  /* KONAN_LIBMULTIPLATFORM_CRYPTO_API_H */
