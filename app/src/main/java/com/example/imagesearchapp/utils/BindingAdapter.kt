package com.example.imagesearchapp.utils

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.imagesearchapp.R
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import de.hdodenhof.circleimageview.CircleImageView
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

@BindingAdapter("isUnderLined")
fun TextView.isUnderLined(underline : Boolean?){
    if(underline != null)
        this.paint.isUnderlineText = underline
}

@BindingAdapter("userImageUrl")
fun CircleImageView.userImageUrl(url: String?) {
    if (url != null) {
        val imgUri = url.toUri().buildUpon().scheme("https").build()
        val shimmer = Shimmer.ColorHighlightBuilder()
            .setBaseColor(Color.parseColor("#F3F3F3"))
            .setBaseAlpha(1F)
            .setHighlightColor(Color.parseColor("#E7E7E7"))
            .setHighlightAlpha(1F)
            .setDropoff(50F)
            .setFixedWidth(50)
            .build()

        val shimmerDrawable = ShimmerDrawable()
        shimmerDrawable.setShimmer(shimmer)

        Glide.with(this.context)
            .load(imgUri)
            .apply(RequestOptions().placeholder(R.drawable.ic_account_circle))
            .error(android.R.drawable.stat_notify_error)
            .into(this)
        this.contentDescription = "User Image"
        /*this.load(imgUri) {
            crossfade(1000)
            placeholder(android.R.drawable.stat_sys_download_done)
            transformations(CircleCropTransformation())
            error(android.R.drawable.stat_notify_error)
            contentDescription = "User Image"
        }*/
    }
}

@BindingAdapter("imageUrl")
fun ImageView.imageUrl(url: String?) {
    if (url != null) {
        val imgUri = url.toUri().buildUpon().scheme("https").build()
        val shimmer = Shimmer.ColorHighlightBuilder()
            .setBaseColor(Color.parseColor("#F3F3F3"))
            .setBaseAlpha(1F)
            .setHighlightColor(Color.parseColor("#E7E7E7"))
            .setHighlightAlpha(1F)
            .setDropoff(50F)
            .build()

        val shimmerDrawable = ShimmerDrawable()
        shimmerDrawable.setShimmer(shimmer)

        Glide.with(this.context)
            .load(imgUri)
            .centerCrop()
            .placeholder(shimmerDrawable)
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(android.R.drawable.stat_notify_error)
            .into(this)

        /*this.load(imgUri) {
            crossfade(true)
            placeholder(shimmerDrawable)
            transformations(RoundedCornersTransformation(12f))
            contentDescription = "User Image"
        }*/
    }
}

@BindingAdapter("userImage")
fun ImageView.userImage(url: String?) {
    if (url != null) {
        val imgUri = url.toUri().buildUpon().scheme("https").build()
        val shimmer = Shimmer.ColorHighlightBuilder()
            .setBaseColor(Color.parseColor("#F3F3F3"))
            .setBaseAlpha(1F)
            .setHighlightColor(Color.parseColor("#E7E7E7"))
            .setHighlightAlpha(1F)
            .setDropoff(50F)
            .build()

        val shimmerDrawable = ShimmerDrawable()
        shimmerDrawable.setShimmer(shimmer)

        Glide.with(this.context)
            .load(imgUri)
            .centerCrop()
            .placeholder(shimmerDrawable)
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(android.R.drawable.stat_notify_error)
            .into(this)

        /*this.load(imgUri) {
            crossfade(true)
            placeholder(shimmerDrawable)
            transformations(RoundedCornersTransformation(12f))
            contentDescription = "User Image"
        }*/
    }


}