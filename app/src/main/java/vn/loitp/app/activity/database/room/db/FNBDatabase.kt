package vn.loitp.app.activity.database.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import vn.loitp.app.activity.database.room.converter.AreaConverter
import vn.loitp.app.activity.database.room.model.FloorPlan

@Database(entities = [FloorPlan::class], version = 1)
abstract class FNBDatabase : RoomDatabase() {

    //@TypeConverters(DateTypeConverter::class, AreaConverter::class)
    @TypeConverters(AreaConverter::class)

    abstract fun floorPlanDao(): FloorPlanDao

    companion object {

        var instance: FNBDatabase? = null

        fun getInstance(context: Context): FNBDatabase? {
            if (instance == null) {
                synchronized(FNBDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext, FNBDatabase::class.java, "FNBDatabase")
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }

            return instance
        }

    }
}
