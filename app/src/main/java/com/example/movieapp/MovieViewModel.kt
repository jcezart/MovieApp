import androidx.lifecycle.*
import com.example.movieapp.MovieEntity
import com.example.movieapp.MovieList
import com.example.movieapp.MovieRepository
import com.example.movieapp.network.*
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _movies = MutableLiveData<List<MovieEntity>>()
    val movies: LiveData<List<MovieEntity>> get() = _movies

    private val _favoriteMovies = MutableLiveData<List<MovieEntity>>()
    val favoriteMovies: LiveData<List<MovieEntity>> get() = _favoriteMovies

    fun loadMoviesByCategory(category: String) {
        viewModelScope.launch {
            repository.fetchAndSaveMovies("334f18bf0d5802c21af75980ff872ada", category)
            val movieList = repository.getMoviesByCategory(category)
            _movies.postValue(movieList)
        }
    }

    fun loadFavoriteMovies() {
        viewModelScope.launch {
            val movieList = repository.getFavoriteMovies()
            _favoriteMovies.postValue(movieList)
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            val moviesList = repository.searchMovies(query)
            _movies.postValue(moviesList)
        }
    }

    fun fetchAndSaveMovies(apiKey: String, category: String) {
        viewModelScope.launch {
            repository.fetchAndSaveMovies(apiKey, category)
        }
    }
}

class MovieViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
