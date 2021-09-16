package com.chinlung.trackmovie.viewmodel

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.*
import androidx.room.Room
import androidx.savedstate.SavedStateRegistryOwner
import com.bumptech.glide.Glide
import com.chinlung.trackmovie.model.*
import com.chinlung.trackmovie.repository.TmdbApi
import com.chinlung.trackmovie.room.dao.MovieDao
import com.chinlung.trackmovie.room.entity.Movie
import com.chinlung.trackmovie.room.roomdatabase.TmdbDataBase
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.*


class ViewModels(val savedStateHandle: SavedStateHandle, private val itemDao: MovieDao) :
    ViewModel() {

    companion object {
        const val CHROME_SEARCH_PREFIX = "https://www.google.com/search?q="
    }

    fun data() = itemDao.getAll1().transform{
        it.onEachIndexed { index, _ -> emit(index) }
    }

    val data:()->Flow<List<Movie>> = { itemDao.getAll1()  }

    private var _position: MutableLiveData<Int> = MutableLiveData()
    val position: LiveData<Int> = _position

    private var _json: MutableLiveData<TotalJson> = MutableLiveData()
    val json: LiveData<TotalJson> get() = _json

    private var _tvList: MutableLiveData<List<Result>> = MutableLiveData()
    val tvList: LiveData<List<Result>> get() = _tvList

    private var _movieList: MutableLiveData<List<Result>> = MutableLiveData()
    val movieList: LiveData<List<Result>> get() = _movieList
    val movieListflow = flow {
        while (true) {
            delay(1000L)
            val list = movieList.value
            emit(list)
        }
    }

    private var _editInput: MutableLiveData<String> = MutableLiveData()
    val editInput: LiveData<String> get() = _editInput

    private val _dataBase: MutableLiveData<List<Movie>> = MutableLiveData()
    val dataBase: LiveData<List<Movie>> get() = _dataBase

    private var _tabLayoutItem: MutableLiveData<Pair<String, Int>> = MutableLiveData()
    val tabLayoutItem: LiveData<Pair<String, Int>> get() = _tabLayoutItem

    private var _url: MutableLiveData<String> = MutableLiveData()
    val url: LiveData<String> get() = _url

    private var _navItem: MutableLiveData<Int> = MutableLiveData()
    val navItem: LiveData<Int> get() = _navItem

    private var _id = MutableLiveData<Int>()
    val id: LiveData<Int> get() = _id

    private var _info = MutableLiveData<InfoResult>()
    val info: LiveData<InfoResult> get() = _info

    private var _jsonn: MutableLiveData<JsonToGson> = MutableLiveData()
    val jsonn: LiveData<JsonToGson> get() = _jsonn

    private var _geners: MutableLiveData<String> = MutableLiveData()
    val geners: LiveData<String> get() = _geners

    private var _dblist = MutableLiveData<List<Movie>>()
    val dblist: LiveData<List<Movie>> get() = _dblist

    val test = flow<Int> {  }

    init {
        _tabLayoutItem.value = Pair("movie", 0)

    }


    fun edit(editSearch:String): Flow<String> {
        return flow {
            while (true) {
                delay(5000L)
                emit(editSearch)
            }
        }
    }

    fun setmovielist() {

    }

    fun getPosition(position: Int) {
        _position.value = position
    }

    fun editInput(text: CharSequence) {
        _editInput.value = text.toString()
    }

    fun getNavBarItem(itemId: Int) {
        _navItem.value = itemId
    }


    fun setImage(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }

    fun requestTmdbApi(api: String = url.value!!) {

        viewModelScope.launch(Dispatchers.IO) {

            api.requestBuild().enqueue(object : Callback {

                override fun onResponse(call: Call, response: Response) {

                    val json = Gson().fromJson(response.body!!.string(), TotalJson::class.java)
                    _json.postValue(json)

                    if (api == TmdbApi.TMDB_MOVIE_HOT) {
                        _movieList.postValue(json.results)
                    } else {
                        _tvList.postValue(json.results)
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    Log.d("requset", "Failure")
                }
            })
        }
    }

    fun requestSeachApi(api: String = url.value!!) {

        viewModelScope.launch(Dispatchers.IO) {
            api.requestBuild().enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("requset", "Failure")
                }

                override fun onResponse(call: Call, response: Response) {
                    val json = Gson().fromJson(response.body!!.string(), TotalJson::class.java)
                    _json.postValue(json)
                }

            })
        }
    }

    fun getTabLayoutItem(tab: TabLayout.Tab) {
        _tabLayoutItem.value =
            if (tab.text.toString() == "MOVIE") Pair("movie", 0) else Pair("tv", 1)
    }

    fun editSearchApi(edit: String): String {
        return if (tabLayoutItem.value!!.first.equals(TmdbApi.TV, ignoreCase = true))
            "${TmdbApi.TMDB_SEARCH}${TmdbApi.TV}${TmdbApi.TMDB_API}&language=zh-TW&query=$edit"
        else
            "${TmdbApi.TMDB_SEARCH}${TmdbApi.MOVIE}${TmdbApi.TMDB_API}&language=zh-TW&query=$edit"
    }

    fun setImageUrl() {
        _url.value = "${TmdbApi.TMDB_IMAGE}${jsonn.value?.poster_path ?: ""}"
    }

    fun cleanImageUrl() {
        _url.value = ""
    }

    fun getState(fragment: String): Parcelable? {
        return savedStateHandle[fragment]
    }

    fun saveState(fragment: String, state: Parcelable) {
//        savedStateHandle.set(fragment,state)
        savedStateHandle[fragment] = state

    }

    fun onclick(position: Int) {

    }


    fun parseId(id: String, str: String = tabLayoutItem.value!!.first) {

        val api = "${TmdbApi.TMDB_GETMOVIE_BY_ID}$str/$id${TmdbApi.TMDB_API}&language=zh-TW"
        Log.d("safsdf", "$api")
        viewModelScope.launch(Dispatchers.IO) {

            api.requestBuild().enqueue(object : Callback {

                override fun onResponse(call: Call, response: Response) {
                    val result = response.body!!.string()

                    _jsonn.postValue(Gson().fromJson(result, JsonToGson::class.java))
                }

                override fun onFailure(call: Call, e: IOException) {
                    Log.d("requset", "Failure")
                }
            })
        }
    }

    fun String.requestBuild(): Call {
        return OkHttpClient.Builder().build().newCall(
            Request.Builder().url(this).build()
        )
    }

    fun setGenres() {
        _geners.value = jsonn.value!!.genres.map { it.name }.joinToString()
    }

    fun openDb(context: Context): TmdbDataBase {
//        return TmdbDataBase.getDatabase(context)
        return Room.databaseBuilder(
            context,
            TmdbDataBase::class.java,
            "database-Tmdb"
        ).build()
    }

    fun dbinsert() =viewModelScope.launch {

    }

    fun dbInsert(db: TmdbDataBase, id: String, poster_path: String?, title: String, first: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = itemDao.getAll().map { it.movieid }
            if (!list.contains(id))

                itemDao.insert(
                    Movie(
                        movieid = id,
                        poster_path = poster_path ?: "",
                        title = title,
                        movieortv = first
                    )
                )
            dbGetAll(db)
            db.close()
        }
    }

    fun dbGetAll(db: TmdbDataBase) {
        viewModelScope.launch(Dispatchers.IO) {
            _dblist.postValue(itemDao.getAll())
            db.close()
        }
    }

    fun deleteByid(db: TmdbDataBase, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.deleteByid(id)
            dbGetAll(db)
        }
    }


    fun deleteByMovieId(db: TmdbDataBase, movieid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.deleteByMovieId(movieid)
            dbGetAll(db)
        }
    }

    fun expand(currenlist: List<Result>, position: Int) {
        if (currenlist[position].expand == View.GONE) {
            currenlist[position].expand = View.VISIBLE
            }else{
            currenlist[position].expand = View.GONE
            }
        _movieList.value = currenlist
    }
}

//class ViewModelFactory(val savedStateHandle: SavedStateHandle,val itemDao: MovieDao) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ViewModels::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return ViewModels(savedStateHandle,itemDao) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
class ViewModelFactory(
    private val itemDao: MovieDao,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle?
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(ViewModels::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewModels(handle, itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


