package ac.id.untad.capstoneproject2021.Activity

import ac.id.untad.capstoneproject2021.Model.User
import ac.id.untad.capstoneproject2021.ViewModel.MainViewModel
import ac.id.untad.capstoneproject2021.databinding.FragmentProfileBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        val userData = viewModel.getData()
        setData(userData)

    }

    private fun setData(userData: User) {
        binding.textEmailProfilUmum.text = userData.email
    }


}