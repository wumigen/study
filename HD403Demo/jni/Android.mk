LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE_TAGS := optional  
LOCAL_MODULE := libparts
LOCAL_SRC_FILES := com_hitown_demo_PartsManager.c
LOCAL_PRELINK_MODULE := false
LOCAL_SHARED_LIBRARIES += \
libcutils libutils
include $(BUILD_SHARED_LIBRARY)