package by.tska.courseapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.tska.courseapp.R
import by.tska.courseapp.dto.Content
import com.squareup.picasso.Picasso

class ContentAdapter(products: Array<Content>) : RecyclerView.Adapter<ContentAdapter.ContentViewHolder>() {

    companion object {
        const val TYPE_MINE = 0
    }

    private val products: Array<Content> = products

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.product_item, parent,
                false)
        return ContentViewHolder(viewType, itemView)

    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun getItemViewType(position: Int): Int {
        val repo = products[position]
        return TYPE_MINE
    }

    class ContentViewHolder(repoType: Int, itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val productIv: ImageView by lazy {
            itemView.findViewById<ImageView>(R.id.product_img)
        }
        private val productNameTv by lazy {
            itemView.findViewById<TextView>(R.id.tv_product_name)
        }
        private val productDescriptionTv by lazy {
            itemView.findViewById<TextView>(R.id.tv_product_description)
        }
        private val productPriceTv by lazy {
            itemView.findViewById<TextView>(R.id.tv_product_price)
        }
        private val productOwnerTv by lazy {
            itemView.findViewById<TextView>(R.id.tv_product_owner)
        }

        private val repoLayout by lazy {
            itemView.findViewById<RelativeLayout>(R.id.rl_repo_holder)
        }

        fun bind(product: Content) {
            productNameTv.text = product.itemName
            productDescriptionTv.text = product.description
            productPriceTv.text = product.cost
            productOwnerTv.text = product.userProfile.firstName
            repoLayout.background = itemView.context.getDrawable(R.drawable.circ_bg_other)

/*            repoLayout.setOnClickListener {
                val url = product.html_url
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                itemView.context.startActivity(i)
            }*/

            Picasso.with(itemView.context)
                .load(product.pictureUrl)
                .into(productIv)
        }
    }

}