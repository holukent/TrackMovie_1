package com.chinlung.trackmovie.viewmodel

import android.content.Context
import android.os.Parcelable
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.*
import androidx.room.Room
import com.bumptech.glide.Glide
import com.chinlung.trackmovie.model.*
import com.chinlung.trackmovie.repository.TmdbApi
import com.chinlung.trackmovie.room.entity.Movie
import com.chinlung.trackmovie.room.roomdatabase.TmdbDataBase
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.*


class ViewModels(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        const val CHROME_SEARCH_PREFIX = "https://www.google.com/search?q="
    }


    private var _position: MutableLiveData<Int> = MutableLiveData()
    val position: LiveData<Int> = _position

    private var _json: MutableLiveData<TotalJson> = MutableLiveData()
    val json: LiveData<TotalJson> get() = _json

    private var _tvList: MutableLiveData<List<Result>> = MutableLiveData()
    val tvList: LiveData<List<Result>> get() = _tvList

    private var _movieList: MutableLiveData<List<Result>> = MutableLiveData()
    val movieList: LiveData<List<Result>> get() = _movieList

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

    init {
        _tabLayoutItem.value = Pair("movie", 0)
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
        savedStateHandle[fragment] = state
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
        return Room.databaseBuilder(
            context,
            TmdbDataBase::class.java,
            "database-Tmdb"
        ).build()
    }

    fun dbInsert(db: TmdbDataBase, id: String, poster_path: String?, title: String, first: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = db.movieDao().getAll().map { it.movieid }
            if (!list.contains(id))

                db.movieDao().insert(
                    Movie(
                        movieid = id,
                        poster_path = poster_path ?: "",
                        title = title,
                        movieortv = first
                    )
                )
            db.close()
        }
    }

    fun dbGetAll(db: TmdbDataBase) {
        viewModelScope.launch(Dispatchers.IO) {
            _dblist.postValue(db.movieDao().getAll())
            db.close()
        }
    }

    fun deleteByid(db: TmdbDataBase, id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            db.movieDao().deleteByid(id)
            dbGetAll(db)
        }
    }

    fun deleteByMovieId(db: TmdbDataBase,movieid:String) {
        viewModelScope.launch(Dispatchers.IO) {
            db.movieDao().deleteByMovieId(movieid)
            dbGetAll(db)
        }
    }
}
