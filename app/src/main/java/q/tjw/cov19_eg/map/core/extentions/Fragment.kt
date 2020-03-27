package q.tjw.cov19_eg.map.core.extentions

import android.content.Context
import androidx.databinding.ViewDataBinding
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import q.tjw.cov19_eg.map.core.base.BaseFragment

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().addToBackStack(null).func().commit()



inline fun <reified T: ViewModel> Fragment.viewModel(factory: ViewModelProvider.Factory, body: T.() -> Unit): T
{
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}
fun BaseFragment.close() = fragmentManager?.popBackStack()
fun <B: ViewDataBinding> BaseFragment.binding(container: ViewGroup):B =
    DataBindingUtil.inflate(layoutInflater, layoutId(), container, false)

val BaseFragment.appContext: Context get() = activity?.applicationContext!!