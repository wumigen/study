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
	
	fd = open(DEVICE_NAME,O_RDWR);//打开设备
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
		jobject obj, jbyteArray buffer) {

		int  len2;
		//char buf[1024];

	jbyte *arraybody;
	arraybody = (*env)->GetByteArrayElements(env,buffer,0);
	char *lic;
	lic = (char*)arraybody;
	    
	LOGD("lic = %s",(const char*)lic);
	len2=0;
        len2 = read(fd, lic, sizeof(lic));
	LOGD("------> jni on readDev %d char", len2);
	(*env)->ReleaseByteArrayElements(env, buffer, arraybody, 0);
	return len2;
}

/*
 * Class:     com_hitown_demo_PartsManager
 * Method:    writeDev
 * Signature: ([B)I
 */
JNIEXPORT jint JNICALL Java_com_hitown_demo_PartsManager_writeDev(JNIEnv *env,
		jobject obj, jbyteArray buffer) {
		int len2; 
  //char buf[a];

  //len2=0;
  fd = open(DEVICE_NAME,O_RDWR);//打开设备
	LOGD("------> jni on writeDev ");
	jbyte *arraybody;
	arraybody = (*env)->GetByteArrayElements(env,buffer,0);
	char *lic;
	lic = (char*)arraybody;
	LOGD("lic = %d",*lic);
	jsize lenth = (*env)->GetArrayLength(env,buffer);
	LOGD("buffer lenth = %d",lenth);
	char buf[lenth];
//if(lic[0]==0X10)
  	buf[0]=1;

   len2= write(fd, buf, lenth);  
  	(*env)->ReleaseByteArrayElements(env, buffer, arraybody, 0);
	close(fd);
	return len2;
}

/*
 * Class:     com_hitown_demo_PartsManager
 * Method:    ioctrlDev
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_hitown_demo_PartsManager_ioctrlDev(JNIEnv *env,
		jobject obj, jint a, jint b) {
		switch(a)
		   {
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
