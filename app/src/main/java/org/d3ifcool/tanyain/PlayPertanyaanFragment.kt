package org.d3ifcool.tanyain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3ifcool.tanyain.data.Pertanyaan
import org.d3ifcool.tanyain.data.PertanyaanDb
import org.d3ifcool.tanyain.databinding.FragmentPlayPertanyaanBinding
import org.d3ifcool.tanyain.viewModels.MainViewModel

class PlayPertanyaanFragment : Fragment() {

    private lateinit var binding: FragmentPlayPertanyaanBinding

    private lateinit var currentPertanyaan:String

    private val viewModel: MainViewModel by viewModels()

    private lateinit var pertanyaanList: List<Pertanyaan>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayPertanyaanBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnPlay.setOnClickListener {
            if (pertanyaanList.isEmpty()) {

                binding.textViewPertanyaan.text = getString(R.string.pertanyaan_kosong)
            } else {
                val pertanyaan: String = randomPertanyaan()
                currentPertanyaan = pertanyaan
                binding.textViewPertanyaan.text = pertanyaan
            }
        }

        viewModel.data.observe(viewLifecycleOwner) { pertanyaanList = it }
    }

    //akan me random pertanyaan yang beda dari pertanyaan sebelumnya
    fun randomPertanyaan():String{
         val pertanyaan = pertanyaanList.random().pertanyaan

        return if (::currentPertanyaan.isInitialized) {
            if (pertanyaan == currentPertanyaan) {
                randomPertanyaan()
            } else {
                return pertanyaan
            }
        } else {
            return pertanyaan
        }

    }
}