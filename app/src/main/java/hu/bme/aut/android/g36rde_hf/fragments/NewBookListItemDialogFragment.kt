package hu.bme.aut.android.g36rde_hf.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.g36rde_hf.R
import hu.bme.aut.android.g36rde_hf.data.BookListItem
import hu.bme.aut.android.g36rde_hf.databinding.DialogNewBookListItemBinding

class NewBookListItemDialogFragment : DialogFragment(){
    interface NewBookListItemDialogListener {
        fun onBookListItemCreated(newItem: BookListItem)
    }
    private lateinit var listener: NewBookListItemDialogListener
    private lateinit var binding: DialogNewBookListItemBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewBookListItemDialogListener
            ?: throw RuntimeException("Activity must implement the NewBookListItemDialogFragment interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewBookListItemBinding.inflate(LayoutInflater.from(context))
        binding.spCategory.adapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.category_items)
        )

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_Book_item)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i ->
                if (isValid()) {
                    listener.onBookListItemCreated(getBookListItem())
                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }

    private fun isValid() = binding.etName.text.isNotEmpty()

    private fun getBookListItem() = BookListItem(
        name = binding.etName.text.toString(),
        description = binding.etDescription.text.toString(),
        pageCount = binding.etPageCount.text.toString().toIntOrNull() ?: 0,
        category = BookListItem.Category.getByOrdinal(binding.spCategory.selectedItemPosition)
            ?: BookListItem.Category.ENTERTAINMENT,
        isBought = binding.cbAlreadyPurchased.isChecked,
        firstEdition = binding.etFirstEdRelease.text.toString(),
        isRead = binding.cbAlreadyRead.isChecked,
        subtitle = binding.etSubtitle.text.toString(),
        author = binding.etAuthor.text.toString(),
        isFavorite = binding.cbFavourite.isChecked,
        currentPage = 0
    )

    companion object {
        const val TAG = "NewBookListItemDialogFragement"
    }

}