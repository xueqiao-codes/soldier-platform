%module(directors="1") FileWatcher
%{
#include "watcher/swig/file_watcher_swig.h"
#include "watcher/file_listener.h"

%}

%include "std_string.i"

#ifdef SWIGJAVA
%{
extern "C" jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    return JNI_VERSION_1_4;
}

extern "C" void JNI_OnUnload(JavaVM* vm, void* reserved) {
    ::soldier::watcher::FileWatcherSwig::destroy();
}
%}

#endif


%feature("director", assumeoverride=1) soldier::watcher::IFileListener;

%include "../file_listener.h"
%include "file_watcher_swig.h"

