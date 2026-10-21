cmake_minimum_required(VERSION 3.10)
project(TriXpress C)

set(CMAKE_C_STANDARD 99)

include_directories(include)

add_executable(TriXpress
        main.c
        src/menu.c
        src/utils.c
        src/algorithmes.c
)
