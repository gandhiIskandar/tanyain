package org.d3ifcool.tanyain.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import org.d3ifcool.tanyain.R
import org.d3ifcool.tanyain.data.Pertanyaan
import org.d3ifcool.tanyain.data.PertanyaanDb
import org.d3ifcool.tanyain.databinding.DialogInsertBinding
import org.d3ifcool.tanyain.viewModels.MainViewModel


class InsertDialog(private val data: Pertanyaan?, private val userId: String) : DialogFragment() {
    private lateinit var binding: DialogInsertBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = layoutInflater
        binding = DialogInsertBinding.inflate(inflater, null, false)

        val builder = AlertDialog.Builder(requireContext()).apply {
            setView(binding.root)
        }


        binding.btnCancel.setOnClickListener { dismiss() }

        binding.btnAdd.setOnClickListener {
            val pertanyaan = if (data != null) updateData(userId) else getData(userId)

            if (data != null) {
                if (pertanyaan != null) {
                    viewModel.updateData(pertanyaan)
//                    listener.updateDialog(pertanyaan)
                }
            } else {
                if (pertanyaan != null) {

                    viewModel.insertData(pertanyaan)
//                    listener.insertDialog(pertanyaan)
                }
            }
            dismiss()
        }
        if (data != null) binding.textFieldPertanyaan.setText(data.pertanyaan)
        if (data != null) binding.btnAdd.text = getString(R.string.selesai)

      //  binding.textFieldPertanyaan.filters = arrayOf(inputFilter())


        return builder.create()
    }

    private fun showMessage(messageResId: Int) {
        Toast.makeText(requireContext(), messageResId, Toast.LENGTH_LONG).apply {
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
    }

    private fun getData(userId: String): Pertanyaan? {
        if (binding.textFieldPertanyaan.text!!.isEmpty()) {
            showMessage(R.string.empty_message)
            return null
        }
        return Pertanyaan(
            pertanyaan = checkTandaTanya(),
            userId = userId
        )
    }

    private fun checkTandaTanya(): String {

        val pertanyaan = binding.textFieldPertanyaan.text.toString()

        return if (pertanyaan.last() != '?') {
            "$pertanyaan?"
        } else {
            pertanyaan
        }

    }

    private fun updateData(userId: String): Pertanyaan? {
        if (binding.textFieldPertanyaan.text!!.isEmpty()) {
            showMessage(R.string.empty_message)
            return null
        }
        return Pertanyaan(
            id = data!!.id,
            pertanyaan = checkTandaTanya(),
            userId = userId,
        )
    }

    private fun inputFilter():InputFilter{
        val filter = InputFilter { source, start, end, _, _, _ ->
            for(i in start until end){
                if(!Character.isLetterOrDigit(source[i]) && source[i] != '?' && source[i]!=' '){
                    return@InputFilter ""
                }
            }
            return@InputFilter null
        }

        return filter
    }
}