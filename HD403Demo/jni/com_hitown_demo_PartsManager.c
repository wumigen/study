#include <jni.h>
#include <string.h>
#include <android/log.h>

#include <errno.h>

#include <cutils/properties.h>
#include <cutils/sockets.h>

#include <fcntl.h>
#include <termios.h>
#include <stdio.h>
#include <stdarg.h>

#define LOG_TAG "partsJni"

#define LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO,  LOG_TAG, fmt, ##args)
#define LOGD(fmt, args...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, fmt, ##args)
#define LOGE(fmt, args...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, fmt, ##args)

int fd;
#define DEVICE_NAME	"dev/ds277x"
JNIEXPORT jint JNICALL Java_com_hitown_demo_PartsManager_openDev(JNIEnv *env,
		jobject obj, jstring path) {
	const char *str = (const char *) (*env)->GetStringUTFChars(env, path,
			JNI_FALSE);

	fd = open(str, O_RDWR); //打开设备
	LOGD("------> jni on openDev path =  %s fd = %d", (const char *)str, fd);
	return fd;
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
	close(fd);
	LOGD("------> jni on closeDev--->%s", (const char *)str);
	return 0;
}

/*
 * Class:     com_hitown_demo_PartsManager
 * Method:    readDev
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_hitown_demo_PartsManager_readDev(JNIEnv *env,
		jobject obj, jbyteArray buffer, jint size) {
	unsigned char *buf_char = (char*) ((*env)->GetByteArrayElements(env, buffer,
			NULL));
	LOGD("read size = %d", size);
	int lenth = read(fd, buf_char, size);
	LOGD("read lenth = %d", lenth);
	return lenth;
}

/*
 * Class:     com_hitown_demo_PartsManager
 * Method:    writeDev
 * Signature: ([B)I
 */
JNIEXPORT jint JNICALL Java_com_hitown_demo_PartsManager_writeDev(JNIEnv *env,
		jobject obj, jbyteArray buffer, jint size) {
	unsigned char *buf_char = (char*) ((*env)->GetByteArrayElements(env, buffer,
			NULL));
	int lenth = write(fd, buf_char, size);
	LOGD("write lenth = %d", lenth);
	return -1;
}

/*
 * Class:     com_hitown_demo_PartsManager
 * Method:    ioctrlDev
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_hitown_demo_PartsManager_ioctrlDev(JNIEnv *env,
		jobject obj, jint a, jint b) {
	switch (a) {
	case 0:

		break;

	default:
		break;
	}

	LOGD("------> jni on ioctrlDev cmd = %d , num = %d", a, b);
	return 0;
}

#ifdef __cplusplus
}
#endif
