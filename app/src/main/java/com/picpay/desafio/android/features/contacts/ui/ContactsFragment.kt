package com.picpay.desafio.android.features.contacts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.databinding.FragmentContactsBinding
import com.picpay.desafio.android.features.contacts.ui.adapter.ContactsAdapter
import com.picpay.desafio.android.features.contacts.ui.viewmodel.ContactViewModel
import com.picpay.desafio.android.shared.hidden
import com.picpay.desafio.android.shared.show
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ContactViewModel by viewModel()
    private val adapter: ContactsAdapter by lazy { ContactsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contactListState.collect { state ->
                    state.handle(
                        loading = { binding.progressBar.show() },
                        success = {
                            adapter.submitList(it)
                            binding.progressBar.hidden()
                            binding.recyclerView.show()
                        },
                        error = {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            binding.progressBar.hidden()
                            binding.recyclerView.hidden()
                        }
                    )
                }
            }
        }
        viewModel.listContacts()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}