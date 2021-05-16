package ru.dimadef.cats.app.features.activities

import android.os.Bundle
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.MvpView
import moxy.ktx.moxyPresenter
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dimadef.cats.app.App
import ru.dimadef.cats.app.R
import javax.inject.Inject
import javax.inject.Provider

@AddToEndSingle
interface MainActivityView : MvpView

class MainActivity : MvpAppCompatActivity(), MainActivityView {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val idMainContainer = R.id.mainContainer

    private val navigator: Navigator = object : AppNavigator(this, idMainContainer) {
        override fun applyCommands(commands: Array<out Command>) {
            super.applyCommands(commands)
            supportFragmentManager.executePendingTransactions()
        }
    }

    @Inject
    lateinit var presenterProvider: Provider<MainActivityPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        supportFragmentManager.findFragmentById(idMainContainer)?.let { super.onBackPressed() }
    }
}