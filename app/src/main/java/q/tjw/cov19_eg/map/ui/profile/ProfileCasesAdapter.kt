package q.tjw.cov19_eg.map.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.databinding.MapCaseItemBinding
import q.tjw.cov19_eg.map.core.data.CaseModule


class ProfileCasesAdapter() : RecyclerView.Adapter<ProfileCasesAdapter.ViewHolder>() {
    private val data = ArrayList<CaseModule>()

    class ViewHolder(val binding: MapCaseItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CaseModule?) {
            item?.let { case -> binding.caseModule = case }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<MapCaseItemBinding>(
            inflater,
            R.layout.map_case_item,
            parent,
            false
        )
        val viewHolder = ViewHolder(binding)
        return viewHolder
    }

    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun update(data_in: ArrayList<CaseModule>) {
        data.addAll(data_in)
        notifyDataSetChanged()
    }
}