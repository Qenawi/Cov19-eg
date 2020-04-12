package q.tjw.cov19_eg.map.ui.status

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.databinding.CasesItemBinding
import q.tjw.cov19_eg.map.data_layer.model.world_cases.CasesResponse
import java.util.*

class CountryCasesAdapter(
    private val countryCases: List<CasesResponse>) :
    RecyclerView.Adapter<CountryCasesAdapter.CountryCaseViewHolder>() , Filterable {

    lateinit var binding: CasesItemBinding
    private lateinit var intent: Intent
    lateinit var context: Context
    lateinit var activity: Activity
    private var countryCasesFilter: List<CasesResponse> = countryCases

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryCaseViewHolder {
        context = parent.context
        activity = context as Activity
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.cases_item,
            parent, false)

        return CountryCaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryCaseViewHolder, position: Int) {

        holder.binding.activeCases.text = countryCasesFilter[position].cases
        holder.binding.recovered.text = countryCasesFilter[position].recovered
        holder.binding.deaths.text = countryCasesFilter[position].deaths
        holder.binding.countryName.text = countryCasesFilter[position].country
        Glide.with(context).load(countryCasesFilter[position].countryInfo?.flag).into(holder.binding.countryIcon)

    }

    override fun getItemCount(): Int {
        return countryCasesFilter.size
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    countryCasesFilter = countryCases
                 //   searchWord = ""
                } else {
                    var filteredList: MutableList<CasesResponse> = ArrayList<CasesResponse>()
                    for (country in countryCases) {
                        if (country.country!!.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(country)
                   //         searchWord = charString.toLowerCase()
                        }
                    }
                    countryCasesFilter = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = countryCasesFilter
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                countryCasesFilter = filterResults.values as ArrayList<CasesResponse>
                notifyDataSetChanged()
            }
        }

    }


    inner class CountryCaseViewHolder(val binding: CasesItemBinding) : RecyclerView.ViewHolder(binding.root)

}
