package org.d3ifcool.tanyain.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import org.d3ifcool.tanyain.data.Pertanyaan
import org.d3ifcool.tanyain.data.PertanyaanDb
import org.d3ifcool.tanyain.databinding.DialogDeleteBinding
import org.d3ifcool.tanyain.viewModels.MainViewModel


class DeleteDialog(private val data: Pertanyaan) : DialogFragment() {
    private lateinit var binding: DialogDeleteBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = layoutInflater
        binding = DialogDeleteBinding.inflate(inflater, null, false)

        val builder = AlertDialog.Builder(requireContext()).apply {
            setView(binding.root)
        }
        binding.btnCancel.setOnClickListener { dismiss() }
        binding.btnDelete.setOnClickListener {
//            val listener = parentFragment as DialogListener
//            listener.deleteDialog(data)
            viewModel.deleteData(data.id)
            dismiss()
        }
        return builder.create()
    }
}