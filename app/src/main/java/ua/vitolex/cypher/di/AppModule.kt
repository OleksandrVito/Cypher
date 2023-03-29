package ua.vitolex.cypher.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.vitolex.to_dolist.data.MessageDatabase
import ua.vitolex.to_dolist.data.MessageRepository
import ua.vitolex.to_dolist.data.MessageRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMessageDatabase(app: Application): MessageDatabase{
        return Room.databaseBuilder(
            app,
            MessageDatabase::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMessageRepository(db: MessageDatabase): MessageRepository {
        return MessageRepositoryImpl(db.dao)
    }
}