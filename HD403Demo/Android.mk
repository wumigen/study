LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES := $(call all-java-files-under,src)

LOCAL_JNI_SHARED_LIBRARIES := libparts
LOCAL_PACKAGE_NAME := HD403Demo

include $(BUILD_PACKAGE)
include $(call all-makefiles-under,$(LOCAL_PATH))