package com.udacity.asteroidradar.persistence

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid

@Database(entities = [AsteroidsDataBaseEntity::class], version = 3, exportSchema = false)
abstract class AsteroidsDatabase : RoomDatabase() {
    abstract fun userDao(): AsteroidsDAO
}

@Entity
data class AsteroidsDataBaseEntity constructor(
    @PrimaryKey
    val id: String,
    val codename: String,
    val approach_date: String,
    val absolute_magnitude: String,
    val estimated_diameter_max_km: String,
    val is_potentially_hazardous_asteroid: String,
    val approach_relative_speed: String,
    val approach_relative_distance: String
    )

fun List<AsteroidsDataBaseEntity>.asDomainModel(): List<Asteroid>{
    return map{
        Asteroid(
            id = it.id.toLong(),
            codename = it.codename,
            closeApproachDate = it.approach_date,
            absoluteMagnitude = it.absolute_magnitude.toDouble(),
            estimatedDiameter = it.estimated_diameter_max_km.toDouble(),
            relativeVelocity = it.approach_relative_speed.toDouble(),
            distanceFromEarth = it.approach_relative_distance.toDouble(),
            isPotentiallyHazardous = it.is_potentially_hazardous_asteroid.toBoolean()
        )
    }
}

fun List<Asteroid>.asDAOModel(): List<AsteroidsDataBaseEntity>{
    return map{
        AsteroidsDataBaseEntity(
            id = it.id.toString(),
            codename = it.codename,
            approach_date = it.closeApproachDate,
            absolute_magnitude = it.absoluteMagnitude.toString(),
            estimated_diameter_max_km = it.estimatedDiameter.toString(),
            approach_relative_speed = it.relativeVelocity.toString(),
            approach_relative_distance = it.distanceFromEarth.toString(),
            is_potentially_hazardous_asteroid = it.isPotentiallyHazardous.toString()
        )
    }
}

@Dao
interface AsteroidsDAO{
    @Query("SELECT * FROM asteroidsdatabaseentity")
    fun getAll(): LiveData<List<AsteroidsDataBaseEntity>>

    @Query("SELECT * FROM asteroidsdatabaseentity WHERE id=:id")
    fun getAsteroidById(id: String): LiveData<AsteroidsDataBaseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOneAsteroid(asteroid: AsteroidsDataBaseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsteroidsInBatch(vararg asteroid: AsteroidsDataBaseEntity)
}

private lateinit var instanceOfAsteroidsDatabase: AsteroidsDatabase

fun getAsteroidsDataBase(context: Context): AsteroidsDatabase{
    synchronized(AsteroidsDatabase::class.java) {
        if (!::instanceOfAsteroidsDatabase.isInitialized) {
            instanceOfAsteroidsDatabase = Room.databaseBuilder(context.applicationContext,
                AsteroidsDatabase::class.java,
                "asteroids")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return instanceOfAsteroidsDatabase
}


