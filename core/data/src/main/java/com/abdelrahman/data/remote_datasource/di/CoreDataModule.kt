package com.abdelrahman.data.remote_datasource.di

import android.content.Context
import android.net.ConnectivityManager
import com.abdelrahman.data.remote_datasource.networkstate.CheckNetworkState
import com.abdelrahman.data.remote_datasource.networkstate.ICheckNetworkState
import com.abdelrahman.data.remote_datasource.validate_remote.IValidateRemoteSource
import com.abdelrahman.data.remote_datasource.validate_remote.ValidateRemoteSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreDataModule {

    companion object {

        @Provides
        @Singleton
        fun providesConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
            return context.getSystemService(ConnectivityManager::class.java)
        }

    }

    @Binds
    @Singleton
    abstract fun bindsNetworkCheck(checkNetworkState: CheckNetworkState): ICheckNetworkState

    @Binds
    @Singleton
    abstract fun bindsValidateRemoteSource(validateRemoteSource: ValidateRemoteSource): IValidateRemoteSource
}