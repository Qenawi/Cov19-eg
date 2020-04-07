package q.tjw.cov19_eg.map.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import carbon.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import kotlinx.android.synthetic.main.map_profile_fragment.*
import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.databinding.MapProfileFragmentBinding
import q.tjw.cov19_eg.map.core.base.BaseActivity
import q.tjw.cov19_eg.map.core.base.BaseFragment
import q.tjw.cov19_eg.map.core.data.CaseModule
import q.tjw.cov19_eg.map.core.data.mToCaseModule
import q.tjw.cov19_eg.map.core.extentions.getDeviceUniqueFootPrint
import q.tjw.cov19_eg.map.core.extentions.myToast
import q.tjw.cov19_eg.map.core.extentions.showToast
import q.tjw.cov19_eg.map.di.app.CO19Application
import q.tjw.cov19_eg.model.mToUserModule
import javax.inject.Inject

class ProfileFragment : BaseFragment() {
    lateinit var  adapter:ProfileCasesAdapter
    private val profileData = MutableLiveData<q.tjw.cov19_eg.model.User>()
    private val userAdds = MutableLiveData<ArrayList<CaseModule>>()
    override fun layoutId() = R.layout.map_profile_fragment
    override fun view_life_cycle_owner() = viewLifecycleOwner
    @Inject
    lateinit var fireStore: FirebaseFirestore
    companion object {
        fun newInstance() = ProfileFragment()
    }
    override fun onResume() {
        super.onResume()
        (activity as BaseActivity<*>).toolbarTitle.postValue(getString(R.string.l_UserProfile))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CO19Application.appComponent.inject(this)
    }
    //todo 7amda Fix Get Profile and get user Status
    private fun getProfile() {
        fireStore.collection("users").document(context.getDeviceUniqueFootPrint()).get()
            .addOnSuccessListener { documentSnapshot -> profileData.postValue(documentSnapshot.data?.mToUserModule()) }
            .addOnFailureListener { exception ->
                context?.showToast(exception.localizedMessage)
            }
    }
    private fun getUserStatus() {
        fireStore.collection("caseReports").whereEqualTo("uid", context.getDeviceUniqueFootPrint())
            .get().addOnSuccessListener { querySnapshot ->
                val res = ArrayList<CaseModule>()
                for (doc in querySnapshot) res.add(doc.data.mToCaseModule())
                adapter.update(res)
            }.addOnFailureListener {
                context?.showToast(it.localizedMessage)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)
         adapter= ProfileCasesAdapter()
        (binding as MapProfileFragmentBinding).user=profileData
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProfile()
        getUserStatus()
        l_recycler.layoutManager=LinearLayoutManager(context)
        l_recycler.adapter=adapter
    }
}