cmake_minimum_required(VERSION 3.4.1)

file (GLOB dependency_evaluator_sources
    /Users/sankar-5526/Desktop/deps/djinni/support-lib/jni/*.cpp
    /Users/sankar-5526/Desktop/Dependency_Evaluator/generated-src/jni/*.cpp
    /Users/sankar-5526/Desktop/Dependency_Evaluator/src/cpp/*.cpp
    /Users/sankar-5526/Desktop/Dependency_Evaluator/src/cpp/*.hpp

)

add_library(dependency_evaluator SHARED ${dependency_evaluator_sources})

# include directories for header files

include_directories(
    /Users/sankar-5526/Desktop/deps/djinni/support-lib/
    /Users/sankar-5526/Desktop/deps/djinni/support-lib/jni/
    /Users/sankar-5526/Desktop/Dependency_Evaluator/generated-src/cpp/
    /Users/sankar-5526/Desktop/Dependency_Evaluator/generated-src/jni/
    /Users/sankar-5526/Desktop/Dependency_Evaluator/src/cpp
)

target_link_libraries(dependency_evaluator
                        android
                        log)