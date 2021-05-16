package ru.dimadef.cats.app.features.favorites

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.adapters.GenericFastItemAdapter
import com.mikepenz.fastadapter.adapters.GenericItemAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.scroll.EndlessRecyclerOnScrollListener
import com.mikepenz.fastadapter.ui.items.ProgressItem
import moxy.ktx.moxyPresenter
import ru.dimadef.cats.app.App
import ru.dimadef.cats.app.R
import ru.dimadef.cats.app.databinding.FragmentFavoritesBinding
import ru.dimadef.cats.app.features.base.BaseFragment
import ru.dimadef.cats.app.features.favorites.items.FavoriteCatItem
import ru.dimadef.cats.app.utils.diff.YFastAdapterDiffUtil
import javax.inject.Inject
import javax.inject.Provider

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(R.layout.fragment_favorites),
    FavoritesView {

    companion object {
        fun newInstance(): FavoritesFragment = FavoritesFragment()
    }

    private lateinit var catsAdapter: GenericFastItemAdapter
    private lateinit var footerAdapter: GenericItemAdapter
    private lateinit var endlessRecyclerOnScrollListener: EndlessRecyclerOnScrollListener

    @Inject
    lateinit var presenterProvider: Provider<FavoritesPresenter>
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
        catsAdapter = FastItemAdapter()
        footerAdapter = ItemAdapter.items()

        catsAdapter.addAdapter(1, footerAdapter)

        val layoutManager = LinearLayoutManager(activity)
        binding.rvCats.layoutManager = layoutManager
        binding.rvCats.adapter = catsAdapter

        endlessRecyclerOnScrollListener = object : EndlessRecyclerOnScrollListener(footerAdapter) {
            val handler = Handler()

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
                }, 1000)
            }
        }
        binding.rvCats.addOnScrollListener(endlessRecyclerOnScrollListener)
    }

    override fun setData(items: List<FavoriteCatItem>, result: DiffUtil.DiffResult) {
        if (catsAdapter.adapterItems.isEmpty()) {
            catsAdapter.setNewList(items)
        } else {
            YFastAdapterDiffUtil[catsAdapter.itemAdapter, items] = result
        }

        checkEmptyFeed()
    }

    private fun checkEmptyFeed() {
        binding.emptyText.visibility = if (catsAdapter.itemCount > 0) {
            View.GONE
        } else {
            View.VISIBLE
        }
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