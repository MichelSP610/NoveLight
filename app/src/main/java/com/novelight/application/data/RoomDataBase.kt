package com.novelight.application.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.novelight.application.data.converters.DateConverter
import com.novelight.application.data.dao.RoomBookDAO
import com.novelight.application.data.dao.RoomReleaseDAO
import com.novelight.application.data.dao.RoomSerieDAO
import com.novelight.application.data.dao.RoomSerieStaffCrossRefDAO
import com.novelight.application.data.dao.RoomStaffDAO
import com.novelight.application.data.entities.RoomBook
import com.novelight.application.data.entities.RoomRelease
import com.novelight.application.data.entities.RoomSerie
import com.novelight.application.data.entities.RoomSerieStaffCrossRef
import com.novelight.application.data.entities.RoomStaff

@Database(
    entities = [RoomSerie::class, RoomBook::class, RoomStaff::class, RoomSerieStaffCrossRef::class, RoomRelease::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class RoomDataBase: RoomDatabase() {

    abstract fun roomSerieDAO() : RoomSerieDAO
    abstract fun roomBookDAO(): RoomBookDAO
    abstract fun roomStaffDAO(): RoomStaffDAO
    abstract fun roomReleaseDAO(): RoomReleaseDAO
    abstract fun roomSerieStaffCrossRefDAO(): RoomSerieStaffCrossRefDAO

    companion object {

        @Volatile
        private var INSTANCE: RoomDataBase? = null

        fun getDatabase(context: Context): RoomDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

//        private fun buildDatabase(context: Context): dataBase {
//            return Room.databaseBuilder(
//                context.applicationContext,
//                dataBase::class.java,
//                "cotxes_database"
//            ).build()
//                //.createFromAsset("databases/motor_database.db").build()
//        }

        private fun buildDatabase(context: Context): RoomDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                RoomDataBase::class.java,
                "NoveLightDB"
            ).build()
        }
    }
}