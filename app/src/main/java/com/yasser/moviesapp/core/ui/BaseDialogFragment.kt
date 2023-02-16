package com.yasser.moviesapp.core.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragment<vBinding:ViewBinding,viewModel: BaseViewModel>(@LayoutRes contentLayout: Int): DialogFragment(contentLayout) {

    protected abstract val binding: vBinding
    protected abstract val viewModel: viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        subscribe()
    }

    open fun init() {}

    open fun subscribe() {}

}

