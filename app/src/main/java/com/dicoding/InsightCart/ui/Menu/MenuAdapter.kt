import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.InsightCart.R

class MenuAdapter(private val menuItems: List<MenuResponseItem>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menuItem = menuItems[position]
        holder.bind(menuItem)
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }

    // ViewHolder untuk setiap item menu
    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textMenuName: TextView = itemView.findViewById(R.id.text_menu_name)
        private val textMenuPrice: TextView = itemView.findViewById(R.id.text_menu_price)

        fun bind(menuItem: MenuResponseItem) {
            textMenuName.text = menuItem.namaMenu
            textMenuPrice.text = "Rp.${menuItem.harga}"

            // Anda bisa bind data lainnya sesuai kebutuhan
        }
    }
}
