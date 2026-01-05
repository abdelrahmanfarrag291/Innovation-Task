cmake_minimum_required(VERSION 3.22.1)

project(movie_db)

add_library(
        movie_db
        SHARED
        movie_db.cpp
)

find_library(
        log-lib
        log
)

target_link_libraries(
        movie_db
        ${log-lib}
)
