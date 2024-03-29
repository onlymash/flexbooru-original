package onlymash.flexbooru.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import onlymash.flexbooru.model.Search
import onlymash.flexbooru.repository.post.PostRepository

class PostViewModel(private val repo: PostRepository): ViewModel() {
    private val searchData = MutableLiveData<Search>()
    private val danRepoResult = map(searchData) { search ->
        repo.getDanbooruPosts(search)
    }
    val postsDan = switchMap(danRepoResult) { it.pagedList }
    val networkStateDan = switchMap(danRepoResult) { it.networkState }
    val refreshStateDan = switchMap(danRepoResult) { it.refreshState }

    private val moeRepoResult = map(searchData) { search ->
        repo.getMoebooruPosts(search)
    }
    val postsMoe = switchMap(moeRepoResult) { it.pagedList }
    val networkStateMoe = switchMap(moeRepoResult) { it.networkState }
    val refreshStateMoe = switchMap(moeRepoResult) { it.refreshState }

    fun show(search: Search): Boolean {
        if (searchData.value == search) {
            return false
        }
        searchData.value = search
        return true
    }

    fun refreshDan() {
        danRepoResult.value?.refresh?.invoke()
    }

    fun refreshMoe() {
        moeRepoResult.value?.refresh?.invoke()
    }

    fun retryDan() {
        danRepoResult?.value?.retry?.invoke()
    }

    fun retryMoe() {
        moeRepoResult?.value?.retry?.invoke()
    }
}