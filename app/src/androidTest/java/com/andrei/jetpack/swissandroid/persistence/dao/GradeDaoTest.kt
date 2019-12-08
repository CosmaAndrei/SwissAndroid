package com.andrei.jetpack.swissandroid.persistence.dao

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.andrei.jetpack.swissandroid.persistence.AppDatabase
import com.andrei.jetpack.swissandroid.persistence.entities.Grade
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GradeDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var gradeDao: GradeDao

    @Before
    fun init() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java
        ).build()
        gradeDao = db.gradeDao()
    }

    @Test
    fun testIfGradeIsSavedCorrectly() {
        val grade = Grade(
            "1",
            "TestItem",
            "TestClientCountLvlOne",
            "TestClientCountLvlTwo",
            5.0
        )

        runBlocking {
            gradeDao.save(grade)
        }

        runBlocking {
            val savedGrade = gradeDao.getOne("1")

            assertThat(savedGrade, equalTo(grade))
        }
    }

    @After
    fun clean() {
        db.close()
    }
}