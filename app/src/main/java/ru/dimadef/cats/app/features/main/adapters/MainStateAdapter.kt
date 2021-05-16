package ru.dimadef.cats.app.features.main.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.dimadef.cats.app.features.favorites.FavoritesFragment
import ru.dimadef.cats.app.features.home.HomeFragment

class MainStateAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when(position){
        0 -> HomeFragment.newInstance()
        1 -> FavoritesFragment.newInstance()
        else -> HomeFragment.newInstance()
    }
}