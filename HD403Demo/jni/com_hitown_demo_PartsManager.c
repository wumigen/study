#include <jni.h>
#include <string.h>
#include <android/log.h>
#define LOG_TAG "partsJni"

#define LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO,  LOG_TAG, fmt, ##args)
#define LOGD(fmt, args...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, fmt, ##args)
#define LOGE(fmt, args...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, fmt, ##args)


JNIEXPORT jint JNICALL Java_com_hitown_demo_PartsManager_openDev(JNIEnv *env,
		jobject obj, jstring path) {
	const char *str = (const char *) (*env)->GetStringUTFChars(env, path,
			JNI_FALSE);
	LOGD("------> jni on openDev path =  %s", (const char *)str);
	return 0;
}

/*
 * Class:     com_hitown_demo_PartsManager
 * Method:    closeDev
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_hitown_demo_PartsManager_closeDev(JNIEnv *env,
		jobject obj, jstring path) {
	const char *str = (const char *) (*env)->GetStringUTFChars(env, path,
			JNI_FALSE);
	LOGD("------> jni on closeDev--->%s", (const char *)str);
	return 0;
}

/*
 * Class:     com_hitown_demo_PartsManager
 * Method:    readDev
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_hitown_demo_PartsManager_readDev(JNIEnv *env,
		jobject obj, jbyteArray buffer) {
	LOGD("------> jni on readDev ");
	return 0;
}

/*
 * Class:     com_hitown_demo_PartsManager
 * Method:    writeDev
 * Signature: ([B)I
 */
JNIEXPORT jint JNICALL Java_com_hitown_demo_PartsManager_writeDev(JNIEnv *env,
		jobject obj, jbyteArray buffer) {
	LOGD("------> jni on writeDev ");
	jbyte *arraybody;
	arraybody = (*env)->GetByteArrayElements(env,buffer,0);
	char *lic;
	lic = (char*)arraybody;
	LOGD("lic = %d",*lic);
	jsize lenth = (*env)->GetArrayLength(env,buffer);
	LOGD("buffer lenth = %d",lenth);
	return lenth;
}

/*
 * Class:     com_hitown_demo_PartsManager
 * Method:    ioctrlDev
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_hitown_demo_PartsManager_ioctrlDev(JNIEnv *env,
		jobject obj, jint a, jint b) {
	LOGD("------> jni on ioctrlDev cmd = %d , num = %d", a, b);
	return 0;
}

#ifdef __cplusplus
}
#endif
