package com.burlaka.vmpusher.sample.presentation.base

import android.graphics.Color
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import com.burlaka.utils.ext.hideKeyboardExt
import com.burlaka.utils.ext.showKeyboardExt
import com.burlaka.utils.ext.snackAlert
import com.burlaka.vmpusher.TaskExecutable
import com.burlaka.vmpusher.PusherViewModel


abstract class BaseActivity<T : ViewDataBinding> :
    AppCompatActivity(),
    LifecycleObserver,
    FragmentHost,
    TaskExecutable {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this@BaseActivity, layoutId())
        getBaseViewModel()?.activityBind()?.dataBind()
    }

    /**
     * Data binding
     */
    private var viewDataBinding: T? = null

    fun getViewDataBinding(): T {
        return viewDataBinding!!
    }

    /**
     * VM
     */
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var baseViewModel: PusherViewModel? = null

    /**
     * Base VM activityBind
     * @return base VM if exist
     */
    abstract fun getBaseViewModel(): PusherViewModel?

    /**
     * Override for set binding variable
     * @return variable id
     */
    abstract fun bindingVmVariable(): Int?

    private fun PusherViewModel.dataBind(): PusherViewModel {
        viewDataBinding?.let {
            it.setVariable(bindingVmVariable()!!, this)
            it.executePendingBindings()
        }
        return this
    }

    private fun PusherViewModel.activityBind(): PusherViewModel {
        if (baseViewModel == null) {
            baseViewModel = this
        }
        viewModelRegistration.add(this)
        return this
    }

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun layoutId(): Int

    override fun hideKeyboard() {
        this@BaseActivity.hideKeyboardExt()
    }

    override fun showKeyboard() {
        this@BaseActivity.showKeyboardExt()
    }

    override fun returnBack() {
        onBackPressed()
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    protected fun showFailedMess(errorMess: String) {
        hideKeyboard()
        getViewDataBinding().root.snackAlert(errorMess, Color.RED)
    }

    protected fun showMessage(errorMess: String) {
        hideKeyboard()
        getViewDataBinding().root.snackAlert(errorMess)
    }

    protected val viewModelRegistration = ArrayList<PusherViewModel>()

}