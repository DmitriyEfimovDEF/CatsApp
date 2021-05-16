package ru.dimadef.cats.app.features.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.adapters.GenericFastItemAdapter
import com.mikepenz.fastadapter.adapters.GenericItemAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.listeners.ClickEventHook
import com.mikepenz.fastadapter.scroll.EndlessRecyclerOnScrollListener
import com.mikepenz.fastadapter.ui.items.ProgressItem
import moxy.ktx.moxyPresenter
import ru.dimadef.cats.app.App
import ru.dimadef.cats.app.R
import ru.dimadef.cats.app.databinding.FragmentHomeBinding
import ru.dimadef.cats.app.features.base.BaseFragment
import ru.dimadef.cats.app.features.home.items.CatItem
import ru.dimadef.cats.app.utils.diff.YFastAdapterDiffUtil
import javax.inject.Inject
import javax.inject.Provider

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home), HomeView {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var catsAdapter: GenericFastItemAdapter
    private lateinit var footerAdapter: GenericItemAdapter
    private lateinit var endlessRecyclerOnScrollListener: EndlessRecyclerOnScrollListener

    @Inject
    lateinit var presenterProvider: Provider<HomePresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun onAttach(context: Context) {
        App.appComponent.inject(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRv()

        binding.swipeRefreshLayout.setOnRefreshListener { presenter.loadCurrentPage() }
    }

    private fun initRv() {
        val handler = Handler()
        catsAdapter = FastItemAdapter()
        footerAdapter = ItemAdapter.items()

        catsAdapter.addAdapter(1, footerAdapter)

        catsAdapter.addEventHook(object : ClickEventHook<CatItem>() {
            override fun onBindMany(viewHolder: RecyclerView.ViewHolder): List<View>? {
                return if (viewHolder is CatItem.ViewHolder) {
                    listOf(viewHolder.likeBtn)
                } else {
                    return super.onBindMany(viewHolder)
                }
            }

            override fun onClick(
                v: View,
                position: Int,
                fastAdapter: FastAdapter<CatItem>,
                item: CatItem
            ) {
                when (v.id) {
                    R.id.likeBtn -> {
                        item.isFavorite = !item.isFavorite
                        presenter.onClickFavoriteBtn(item.data, item.isFavorite)
                    }
                }
            }
        })

        val staggeredGrid = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGrid.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        binding.rvCats.layoutManager = staggeredGrid
        binding.rvCats.adapter = catsAdapter

        endlessRecyclerOnScrollListener = object : EndlessRecyclerOnScrollListener(footerAdapter) {
            override fun onLoadMore(currentPage: Int) {
                binding.rvCats.post {
                    footerAdapter.clear()

                    val progressItem = ProgressItem()
                    progressItem.isEnabled = false

                    footerAdapter.add(progressItem)
                }

                handler.postDelayed({
                    if (currentPage > 0) {
                        presenter.goToNextPage()
                    } else {
                        presenter.loadCurrentPage()
                    }
                }, 100)
            }
        }
        binding.rvCats.addOnScrollListener(endlessRecyclerOnScrollListener)
    }

    override fun setData(items: List<CatItem>, result: DiffUtil.DiffResult) {
        if (catsAdapter.adapterItems.isEmpty()) {
            catsAdapter.setNewList(items)
        } else {
            YFastAdapterDiffUtil[catsAdapter.itemAdapter, items] = result
        }
        checkEmptyFeed()
    }

    private fun checkEmptyFeed() {
        binding.emptyText.isVisible = catsAdapter.itemCount < 0
    }

    override fun hideProgress() {
        binding.swipeRefreshLayout.isRefreshing = false
        footerAdapter.clear()
        checkEmptyFeed()
    }

    override fun onDestroyView() {
        binding.rvCats.removeOnScrollListener(endlessRecyclerOnScrollListener)
        super.onDestroyView()
    }
}