#
#
#
#
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

#这个主要用于是 eng、user还是 userdebug 版本参与编译; optional值所有版本都参与编译
LOCAL_MODULE_TAGS := optional
LOCAL_PRIVATE_PLATFORM_APIS := true;

LOCAL_STATIC_JAVA_LIBRARIES := \
     android-support-v4 \
     android-support-v7-recyclerview \
     android-support-v7-palette \

#编译的java文件文件路径
LOCAL_SRC_FILES := $(call all-java-files-under, src)

 #需要编译的 AndroidManifest.xml 文件
#LOCAL_MANIFEST_FILE := $(LOCAL_PATH)/AndroidManifest.xml

 #编译的资源文件文件路径
LOCAL_RESOURCE_DIR := $(LOCAL_PATH)/res


 #编译出的apk的名称
LOCAL_PACKAGE_NAME := VebFileManager

 #apk 签名
LOCAL_CERTIFICATE := platform 

include $(BUILD_PACKAGE)
#include $(CLEAR_VERS)
#include $(call all-makefiles-under,$(LOCAL_PATH))
