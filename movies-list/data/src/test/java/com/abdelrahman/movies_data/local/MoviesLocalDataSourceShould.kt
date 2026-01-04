package com.abdelrahman.movies_data.local

import com.abdelrahman.movies_data.local.database.MoviesDao
import com.abdelrahman.movies_data.local.database.MoviesEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * MoviesLocalDataSource is to save/retrieve movies from cached data base in case of device have no internet connection.
 *
 *
 * 1-
 */
class MoviesLocalDataSourceShould {
    //SUT
    private lateinit var mMoviesLocalDataSource: IMoviesLocalDataSource
    private val fakeMoviesDao = FakeMoviesDao()

    @Before
    fun init() {
        mMoviesLocalDataSource = MoviesLocalDataSource(fakeMoviesDao)
    }

    @Test
    fun `when save movies to database it should added successfully`() = runTest {
        //fakeMoviesDao.insertMoves(listOf(MoviesEntity(1), MoviesEntity(2)))
        mMoviesLocalDataSource.saveAllMovies(listOf(MoviesEntity(1), MoviesEntity(2)))
        val expected = listOf(MoviesEntity(1), MoviesEntity(2))
        val actual = mMoviesLocalDataSource.getAllMovies(1)
        assertEquals(actual, expected)
    }
}