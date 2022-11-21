package com.learning.baseprojectforkan.common.di

import com.learning.baseprojectforkan.common.data.remote.repository.MainRepository
import org.koin.dsl.module
/** Here, we provided the MainRepository instance by,*/
val repoModule = module {
    single {
        MainRepository(get())
    }
}