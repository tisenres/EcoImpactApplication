package com.example.ecoimpactapplication.remote

import com.example.ecoimpactapplication.remote.vo.MyModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object Remote {

    private const val BASE_HOST = ""

    private val api: DataAPI = Retrofit.Builder()
        .baseUrl(BASE_HOST)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(DataAPI::class.java)

    fun getNames(): Single<MyModel> {
        return api.getNames()
            .subscribeOn(Schedulers.io())
    }
}