import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movieapp.MovieEntity

@Dao
interface MovieDAO {

    @Query("SELECT * FROM movieentity")
    fun getAll(): List<MovieEntity>

    @Insert
    suspend fun insertAll(movie: MovieEntity)
}
