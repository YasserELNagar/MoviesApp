package com.yasser.moviesapp.core.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<vBinding:ViewBinding,viewModel: BaseViewModel>(@LayoutRes contentLayout: Int): BottomSheetDialogFragment(contentLayout) {

    protected abstract val binding: vBinding
    protected abstract val viewModel: viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        subscribe()
        val bottomSheet = view.parent as View
        bottomSheet.backgroundTintMode = PorterDuff.Mode.CLEAR
        bottomSheet.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT);
        bottomSheet.setBackgroundColor(Color.TRANSPARENT);
    }

    open fun init() {}

    open fun subscribe() {}
}

