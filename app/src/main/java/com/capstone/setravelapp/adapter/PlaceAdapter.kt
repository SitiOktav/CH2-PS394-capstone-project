import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.util.Pair
import java.text.NumberFormat
import java.util.Currency
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.setravelapp.data.remote.response.PlaceResponseItem
import com.capstone.setravelapp.databinding.DestinasiBinding
import com.capstone.setravelapp.view.activity.detail.DeatailDestinasiActivity

class PlaceAdapter : ListAdapter<PlaceResponseItem, PlaceAdapter.PlaceViewHolder>(PlaceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val binding = DestinasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class PlaceViewHolder(private val binding: DestinasiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(placeItem: PlaceResponseItem) {
            binding.apply {
                tvName.text = placeItem.placeName
                tvCity.text = placeItem.city
                tvRating.text = placeItem.rating
                tvDecription.text = placeItem.description

                // Format harga menjadi mata uang
                val price = placeItem.price?.toDoubleOrNull()
                if (price != null) {
                    val currencyFormat = NumberFormat.getCurrencyInstance()
                    currencyFormat.currency = Currency.getInstance("IDR")
                    tvPrice.text = currencyFormat.format(price)
                } else {
                    tvPrice.text = placeItem.price
                }

                Glide.with(itemView.context)
                    .load(placeItem.placeImage)
                    .centerCrop()
                    .into(ivPhoto)

                // OptionCompat
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DeatailDestinasiActivity::class.java)
                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(tvName, "placeName"),
                            Pair(tvCity, "city"),
                            Pair(tvRating, "rating"),
                            Pair(tvDecription, "description"),
                            Pair(tvPrice, "price"),
                            Pair(ivPhoto, "placeImange")
                        )
                    intent.putExtra(DeatailDestinasiActivity.EXTRA_PLACE_ITEM, placeItem)
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }

    private class PlaceDiffCallback : DiffUtil.ItemCallback<PlaceResponseItem>() {
        override fun areItemsTheSame(oldItem: PlaceResponseItem, newItem: PlaceResponseItem): Boolean {
            return oldItem.placeName == newItem.placeName
        }

        override fun areContentsTheSame(oldItem: PlaceResponseItem, newItem: PlaceResponseItem): Boolean {
            return oldItem == newItem
        }
    }
}
