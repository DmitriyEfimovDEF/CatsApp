package ru.dimadef.cats.app.features.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import ru.dimadef.cats.app.App
import ru.dimadef.cats.app.R
import ru.dimadef.cats.app.databinding.FragmentMainBinding
import ru.dimadef.cats.app.features.base.BaseFragment
import ru.dimadef.cats.app.features.main.adapters.MainStateAdapter

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var adapter: MainStateAdapter

    override fun onAttach(context: Context) {
        App.appComponent.inject(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MainStateAdapter(this)
        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavigation.menu.getItem(position).isChecked = true
            }
        })

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            val numPage = when (it.itemId) {
                R.id.nav_favorites -> 1
                else -> 0
            }
            binding.viewPager.setCurrentItem(numPage, true)

            return@setOnNavigationItemSelectedListener false
        }
    }
}