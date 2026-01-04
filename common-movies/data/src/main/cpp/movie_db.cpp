#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_abdelrahman_common_1data_remote_ApiKey_getApiKey(
        JNIEnv *env,
        jobject
) {
    std::string part1 = "eyJhbGciOiJIUzI1NiJ9";
    std::string part2 = "eyJhdWQiOiIyOTFhMGViY2VkZmZiM2I1NjUzMmJjMDE1YjY2NGI4NyIsIm5iZiI6MTY5Mjg4MTE4My40NzgwMDAyLCJzdWIiOiI2NGU3NTExZjFmZWFjMTAxMWIyYzkxM2YiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0";
    std::string part3 = "_acwCWS6sLN-46h_8Z3FLJL5Jb0UQpAG943W5A4g2RY";
    std::string apiKey = part1 + "." + part2 + "." + part3;
    return env->NewStringUTF(apiKey.c_str());
}

