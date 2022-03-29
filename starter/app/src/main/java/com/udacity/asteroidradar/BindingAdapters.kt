package com.udacity.asteroidradar

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    val context = imageView.context
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription =
            context.getString(R.string.content_dangerous_asteroids_image_item)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription = context.getString(R.string.content_safe_asteroids_image_item)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    val context = imageView.context
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription = context.getString(R.string.content_dangerous_asteroids_image)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription = context.getString(R.string.content_safe_asteroids_image)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
    textView.contentDescription = textView.context.getString(R.string.astronomica_unit_explanation)
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

    val context = view.context

    when (imageStatus) {
        Constants.PICTURE_DONE -> {
            Picasso.get().load(pictureOfDay.url).into(view)
            view.contentDescription =
                String.format(context.getString(R.string.init_image_of_day), pictureOfDay.title)
        }
        Constants.PICTURE_LOADING -> {
            view.setImageResource(R.drawable.loading_animation)
            view.contentDescription = context.getString(R.string.loading_picture_of_day)
        }
        Constants.PICTURE_ERROR -> {
            view.setImageResource(R.drawable.ic_connection_error)
            view.contentDescription = context.getString(R.string.error_picture_of_day)
        }
    }

}

@BindingAdapter("setCodeName")
fun setCodeName(view: TextView, codeName: String){
    val context = view.context
    view.text = String.format(context.getString(R.string.item_codename), codeName)
}

@BindingAdapter("setApproachDate")
fun setApproachDate(view: TextView, setApproachDate: String){
    val context = view.context
    view.text = String.format(context.getString(R.string.item_approachdate), setApproachDate)
}

@BindingAdapter("setContentDescriptionToCardView")
fun insertContentDescription(view: View, asteroid: Asteroid){
    val context = view.context
    view.contentDescription = String.format(context.getString(R.string.card_view_content_init), asteroid.codename)
}
