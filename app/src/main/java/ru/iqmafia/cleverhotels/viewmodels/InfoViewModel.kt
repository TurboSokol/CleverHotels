package ru.iqmafia.cleverhotels.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.iqmafia.cleverhotels.database.retrofit.HotelsRetrofitApi
import ru.iqmafia.cleverhotels.database.models.InfoResponse
import ru.iqmafia.cleverhotels.database.models.InfoResponseEntity
import ru.iqmafia.cleverhotels.utils.MY_IO_SCOPE
import ru.iqmafia.cleverhotels.utils.ROOM_REPOSITORY

class InfoViewModel : ViewModel() {
    var infoRetrofitResponse: MutableLiveData<Response<InfoResponse>> = MutableLiveData()
    var infoRoomResponse: LiveData<InfoResponseEntity> = MutableLiveData()

    fun dynamicFetchHotel(hotelsRetrofitApi: HotelsRetrofitApi, hotelId: Int) {
        viewModelScope.launch {
            val response = hotelsRetrofitApi.dynamicFetchHotelRetrofit(hotelId = hotelId)
            infoRetrofitResponse.value = response
        }
    }

    fun insertInfoToRoom(info: InfoResponseEntity) {
           MY_IO_SCOPE.launch {
               ROOM_REPOSITORY.insertInfo(info)
           }
    }

    fun getInfoFromRoom(): LiveData<InfoResponseEntity> {
        var response: LiveData<InfoResponseEntity>
        viewModelScope.launch {
            response = ROOM_REPOSITORY.infoRoomResponse
            infoRoomResponse = response
        }
        return infoRoomResponse
    }

}