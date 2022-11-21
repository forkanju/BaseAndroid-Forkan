package com.learning.baseprojectforkan.ui.main.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.learning.baseprojectforkan.R
import com.learning.baseprojectforkan.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnFirstFragment.setOnClickListener {
            findNavController().navigate(R.id.action_first_to_second)
        }
    }

}

/**Safe Arguments -> to pass arguments in different fragments - see MindOrks docs*/

/**Safe Args is strongly recommended for navigating and passing data, because it ensures type-safety.
 * In some cases, for example if you are not using Gradle, you can't use the Safe Args plugin.
 * In these cases, you can use Bundles to directly pass data.*/