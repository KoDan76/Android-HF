package hu.bme.aut.android.g36rde_hf.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import hu.bme.aut.android.g36rde_hf.R
import hu.bme.aut.android.g36rde_hf.data.BookListDatabase
import hu.bme.aut.android.g36rde_hf.databinding.FragmentPieChartBinding
import kotlinx.coroutines.NonCancellable.join
import kotlin.concurrent.thread

class PieChartFragment : DialogFragment() {

    private lateinit var database: BookListDatabase
    private lateinit var binding : FragmentPieChartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPieChartBinding.inflate(layoutInflater, container, false)
        database = BookListDatabase.getDatabase(requireContext().applicationContext);
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btBack.setOnClickListener {
            dismiss()
        }
        loadStatistics()
    }
    @UiThread
    private fun loadStatistics(){
        var ownedNum = 0.0f;
        var unReadNum = 0.0f;
        var readNum = 0.0f;
        var itemNum = 0.0f;

        thread{
            val items = database.bookListItemDAO().getAll()
            for(item in items){
                if(item.isRead) readNum += 1.0f;
                if(item.isBought && !item.isRead) unReadNum += 1.0f;
                if(item.isBought) ownedNum += 1.0f;
                itemNum += 1.0f;
            }
            thread {
                val ownedRatioEntries = listOf(
                    PieEntry(ownedNum, "Owned"),
                    PieEntry(itemNum-ownedNum, "Not Owned yet")
                )

                val ownedRatioDataSet = PieDataSet(ownedRatioEntries, "Owned books")
                ownedRatioDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

                val ownedRatioData = PieData(ownedRatioDataSet)
                binding.chartOwnedRatio.data = ownedRatioData
                binding.chartOwnedRatio.invalidate()

                val readRatioEntries = listOf(
                    PieEntry(readNum, "Read"),
                    PieEntry(unReadNum, "On the Shelf")
                )

                val readRatioDataSet = PieDataSet(readRatioEntries, "Read Books")
                readRatioDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

                val readRatioData = PieData(readRatioDataSet)
                binding.chartReadRatio.data = readRatioData
                binding.chartReadRatio.invalidate()
            }
        }
    }

    companion object {
        const val TAG = "PieChartFragment"
    }
}