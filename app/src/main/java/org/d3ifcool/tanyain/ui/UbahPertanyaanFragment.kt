package org.d3ifcool.tanyain.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import org.d3ifcool.tanyain.MainAdapter
import org.d3ifcool.tanyain.data.PertanyaanDb
import org.d3ifcool.tanyain.databinding.FragmentUbahPertanyaanBinding
import org.d3ifcool.tanyain.ui.dialog.InsertDialog
import org.d3ifcool.tanyain.viewModels.FirebaseUserViewModel
import org.d3ifcool.tanyain.viewModels.MainViewModel


class UbahPertanyaanFragment : Fragment() {

    private lateinit var binding: FragmentUbahPertanyaanBinding
    private lateinit var myAdapter: MainAdapter

    private val viewModel: MainViewModel by viewModels()

    private val userViewModel: FirebaseUserViewModel by lazy {
        ViewModelProvider(this)[FirebaseUserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUbahPertanyaanBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.authState.observe(viewLifecycleOwner) { firebaseUser ->
            if (firebaseUser != null) {

                myAdapter = MainAdapter(childFragmentManager, firebaseUser.uid)
                with(binding.recyclerViewPertanyaan) {
                    addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
                    setHasFixedSize(true)
                    adapter = myAdapter
                }
                viewModel.data.observe(viewLifecycleOwner) {
                    myAdapter.submitList(it)
                    Log.d("get data snapshot: ", it.toString())
                }

                binding.btnAdd.setOnClickListener {
                    InsertDialog(null, firebaseUser.uid).show(
                        childFragmentManager,
                        "InsertDialog"
                    )
                }
            }
        }


    }

//    override fun insertDialog(pertanyaan: Pertanyaan) {
//        viewModel.insertData(pertanyaan)
//    }
//
//    override fun updateDialog(pertanyaan: Pertanyaan) {
//        viewModel.updateData(pertanyaan)
//    }
//
//    override fun deleteDialog(pertanyaan: Pertanyaan) {
//        viewModel.deleteData(pertanyaan.id)
//    }
}