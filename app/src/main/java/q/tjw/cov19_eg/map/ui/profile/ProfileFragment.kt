package q.tjw.cov19_eg.map.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.map_profile_fragment.*
import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.SplashActivity
import q.tjw.cov19_eg.databinding.MapProfileFragmentBinding
import q.tjw.cov19_eg.map.core.base.BaseActivity
import q.tjw.cov19_eg.map.core.base.BaseFragment
import q.tjw.cov19_eg.map.core.data.CaseModule
import q.tjw.cov19_eg.map.core.data.mToCaseModule
import q.tjw.cov19_eg.map.core.extentions.Navigation
import q.tjw.cov19_eg.map.core.extentions.getDeviceUniqueFootPrint
import q.tjw.cov19_eg.map.core.extentions.showToast
import q.tjw.cov19_eg.model.User
import q.tjw.cov19_eg.map.di.app.CO19Application
import q.tjw.cov19_eg.map.ui.MainMapActivity
import q.tjw.cov19_eg.map.ui.map_reportcase.AddCaseRepo
import q.tjw.cov19_eg.model.mToUserModule
import q.tjw.cov19_eg.views.RegisterActivity
import javax.inject.Inject

class ProfileFragment : BaseFragment() {
    private lateinit var adapter: ProfileCasesAdapter
    private val profileData = MutableLiveData<q.tjw.cov19_eg.model.User>()
    private val userCurrentState = MutableLiveData<CaseModule>()
    private val caseCount=MutableLiveData<Int>().apply { this.value=0 }
    override fun layoutId() = R.layout.map_profile_fragment
    override fun view_life_cycle_owner() = viewLifecycleOwner
    private lateinit var userDeviceId: String
    @Inject
    lateinit var fireStore: FirebaseFirestore

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainMapActivity?)?.currentFrag?.postValue(Navigation.Profile)
        (activity as BaseActivity<*>).toolbarTitle.postValue(getString(R.string.l_UserProfile))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CO19Application.appComponent.inject(this)
        userDeviceId = context.getDeviceUniqueFootPrint()
    }

    private fun getProfile() {
        fireStore.collection(SplashActivity.USERS_COLLECTION)
            .document(context.getDeviceUniqueFootPrint()).get()
            .addOnSuccessListener { documentSnapshot -> profileData.postValue(documentSnapshot.data?.mToUserModule()) }
            .addOnFailureListener { exception ->
                context?.showToast(exception.localizedMessage)
            }
    }

    private fun getUserStatus() {
        fireStore.collection(AddCaseRepo.CaseUrl)
            .whereEqualTo(AddCaseRepo.USER_ID, context.getDeviceUniqueFootPrint())
            .get().addOnSuccessListener { querySnapshot ->
                val res = ArrayList<CaseModule>()
                for (doc in querySnapshot) {
                    val toAdd = doc.data.mToCaseModule()
                    if (toAdd.uId == userDeviceId) userCurrentState.postValue(toAdd)
                    else res.add(toAdd)
                }
                adapter.update(res)
                caseCount.postValue(res.size)
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
        adapter = ProfileCasesAdapter()
        (binding as MapProfileFragmentBinding).user = profileData
        (binding as MapProfileFragmentBinding).caseCount=caseCount
        (binding as MapProfileFragmentBinding).include2.caseModule = userCurrentState

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProfile()
        getUserStatus()
        l_recycler.layoutManager = LinearLayoutManager(context)
        l_recycler.adapter = adapter
    }
}