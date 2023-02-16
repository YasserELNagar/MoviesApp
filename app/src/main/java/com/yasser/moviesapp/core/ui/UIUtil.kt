package com.yasser.moviesapp.core.ui

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.squareup.picasso.Picasso
import com.yasser.data_remote.common.AppException
import com.yasser.moviesapp.R
import timber.log.Timber


fun RecyclerView.paginateWithScrolling(
    canLoadMore: () -> Boolean, loadingAction: () -> Unit
) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1)) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val positionOfFirstVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (canLoadMore() && visibleItemCount + positionOfFirstVisibleItem >= totalItemCount)
                    loadingAction()
            }
        }
    })
}


fun ImageView.loadImageByUrl(url: String) {

    val fullUrl = "https://image.tmdb.org/t/p/w500$url"

    Picasso.get().load(fullUrl)
        .placeholder(getDrawableProgress(context))
        .error(R.drawable.img_no_image)
        .into(this)

}


fun getDrawableProgress(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        backgroundColor = ContextCompat.getColor(context, android.R.color.transparent)
        strokeWidth = 10.dpFromPx(context).toFloat()
        centerRadius = 50.dpFromPx(context).toFloat()
        colorFilter =
            BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                R.color.colorRed,
                BlendModeCompat.SRC_ATOP
            )

        start()
    }
}


fun Int.dpFromPx(context: Context): Int {
    return (this / context.resources.displayMetrics.density).toInt()
}

fun Int.pxFromDp(context: Context): Int {
    return this * context.resources.displayMetrics.density.toInt()
}

fun Context.handleError(t: Throwable?) {
    Timber.tag("yasso_testing").e(t)
    val errorMsg = when (t) {
        is AppException.NetworkException -> {
            getString(R.string.connection_error)
        }
        is AppException.UnAuthorizedException -> {
            getString(R.string.unauthorized_access)
        }
        else -> {
            getString(R.string.unknown_error)
        }
    }

    Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
}

fun Context.openKeyBoard(editText: EditText){
    editText.requestFocus()
    val imm: InputMethodManager? = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
}

fun Context.closeKeyBoard(editText: EditText){
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm!!.hideSoftInputFromWindow(editText.getWindowToken(), 0)
}