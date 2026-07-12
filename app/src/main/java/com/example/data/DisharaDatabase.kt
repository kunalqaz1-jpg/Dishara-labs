package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "user_profiles")
data class UserProfile(
    @PrimaryKey val id: Int = 1,
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val avatarIndex: Int = 0,
    val dateOfBirth: String = "",
    val gender: String = "",
    val schoolName: String = "",
    val grade: String = "",
    val state: String = "",
    val district: String = "",
    val preferredLanguage: String = "English", // "हिन्दी" or "English"
    val selectedInterests: String = "", // Comma-separated list of interests
    val dream: String = "",
    val onboardingCompleted: Boolean = false,
    val registered: Boolean = false,
    val totalXp: Int = 0,
    val currentStreak: Int = 0,
    val enrolledBatchId: String = "" // Empty string means not enrolled in any batch yet
)

@Entity(tableName = "lesson_progress")
data class LessonProgress(
    @PrimaryKey val lessonId: String,
    val moduleId: String,
    val completed: Boolean = false,
    val progressPercent: Int = 0
)

@Dao
interface DisharaDao {
    @Query("SELECT * FROM user_profiles WHERE id = 1 LIMIT 1")
    fun getUserProfileFlow(): Flow<UserProfile?>

    @Query("SELECT * FROM user_profiles WHERE id = 1 LIMIT 1")
    suspend fun getUserProfile(): UserProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProfile(profile: UserProfile)

    @Query("SELECT * FROM lesson_progress")
    fun getAllProgressFlow(): Flow<List<LessonProgress>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLessonProgress(progress: LessonProgress)

    @Query("SELECT * FROM lesson_progress WHERE lessonId = :lessonId LIMIT 1")
    suspend fun getLessonProgress(lessonId: String): LessonProgress?
}

@Database(entities = [UserProfile::class, LessonProgress::class], version = 2, exportSchema = false)
abstract class DisharaDatabase : RoomDatabase() {
    abstract fun disharaDao(): DisharaDao

    companion object {
        @Volatile
        private var INSTANCE: DisharaDatabase? = null

        fun getInstance(context: android.content.Context): DisharaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    DisharaDatabase::class.java,
                    "dishara_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class DisharaRepository(private val dao: DisharaDao) {
    val userProfile: Flow<UserProfile?> = dao.getUserProfileFlow()
    val allProgress: Flow<List<LessonProgress>> = dao.getAllProgressFlow()

    suspend fun getProfile(): UserProfile? = dao.getUserProfile()
    suspend fun saveProfile(profile: UserProfile) = dao.saveUserProfile(profile)
    suspend fun saveProgress(progress: LessonProgress) = dao.saveLessonProgress(progress)
    suspend fun getProgress(lessonId: String): LessonProgress? = dao.getLessonProgress(lessonId)
}
