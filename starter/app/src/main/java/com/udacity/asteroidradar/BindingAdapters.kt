package com.udacity.asteroidradar

import android.os.Build
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.main.MainViewModel


@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription = R.string.content_dangerous_asteroids_image.toString()
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription = R.string.content_safe_asteroids_image.toString()
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@RequiresApi(Build.VERSION_CODES.N)
@BindingAdapter("pictureOfDay", "imageStatus")
fun setImageUrl(view: ImageView, pictureOfDay: PictureOfDay, imageStatus: Int) {
    when (imageStatus) {
        Constants.PICTURE_DONE -> {
            Log.v("TEST_DESCRIPTION",pictureOfDay.title)
            Picasso.get().load(pictureOfDay.url).into(view)
            view.contentDescription = view.context.getString(R.string.init_image_of_day) + pictureOfDay.title
            Log.v("TEST_DESCRIPTION",view.contentDescription.toString())
        }
        Constants.PICTURE_LOADING -> {
            view.setImageResource(R.drawable.loading_animation)
            view.contentDescription = R.string.loading_picture_of_day.toString()
        }
        Constants.PICTURE_ERROR -> {
            view.setImageResource(R.drawable.ic_connection_error)
            view.contentDescription = R.string.error_picture_of_day.toString()
        }
    }

}
