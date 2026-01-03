package com.abdelrahman.data.remote_datasource.di

import com.abdelrahman.data.remote_datasource.validate_remote.IValidateRemoteSource
import com.abdelrahman.data.remote_datasource.validate_remote.ValidateRemoteSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreDataModule {

    companion object{

    }


    @Binds
    @Singleton
    abstract fun bindsValidateRemoteSource(validateRemoteSource: ValidateRemoteSource): IValidateRemoteSource
}