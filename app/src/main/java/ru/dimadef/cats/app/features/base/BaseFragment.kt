package ru.dimadef.cats.app.features.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import moxy.MvpAppCompatFragment

abstract class BaseFragment<T : ViewDataBinding> constructor(
    @LayoutRes private val contentLayoutId: Int
) : MvpAppCompatFragment() {

    private lateinit var _binding: T

    protected val binding: T
        get() = _binding

    protected inline fun binding(block: T.() -> Unit): T = binding.apply(block)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, contentLayoutId, container, false)
        return _binding.root
    }

    open fun hideLoading() {}

    open fun showError(error: Throwable) {
        hideLoading()
        error.printStackTrace()
    }
}