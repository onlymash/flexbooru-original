package onlymash.flexbooru.repository.popular

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import onlymash.flexbooru.api.MoebooruApi
import onlymash.flexbooru.database.FlexbooruDatabase
import onlymash.flexbooru.model.Popular
import onlymash.flexbooru.model.PostMoe
import java.util.concurrent.Executor

class PopularMoeDataSourceFactory(
    private val moebooruApi: MoebooruApi,
    private val db: FlexbooruDatabase,
    private val popular: Popular,
    private val retryExecutor: Executor) : DataSource.Factory<Int, PostMoe>(){

    val sourceLiveData = MutableLiveData<PopularMoeDataSource>()

    override fun create(): DataSource<Int, PostMoe> {
        val source = PopularMoeDataSource(moebooruApi, db, popular, retryExecutor)
        sourceLiveData.postValue(source)
        return source
    }
}