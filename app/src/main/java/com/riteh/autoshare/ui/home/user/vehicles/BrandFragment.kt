package com.riteh.autoshare.ui.home.user.vehicles

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.riteh.autoshare.R
import com.riteh.autoshare.adapters.BrandListAdapter
import com.riteh.autoshare.data.BrandInfo
import com.riteh.autoshare.databinding.FragmentBrandBinding
import kotlinx.android.synthetic.main.fragment_brand.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream


class BrandFragment : Fragment() {

    private var binding: FragmentBrandBinding? = null
    private lateinit var viewModel: VehicleInfoViewModel

    private lateinit var adapter: BrandListAdapter
    private lateinit var brandsList: List<BrandInfo>

    private val sharedViewModel: VehicleInfoViewModel by activityViewModels()

    companion object {
        fun newInstance() = BrandFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentBrandBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.brandFragment = this

        fillBrandsList()
        setUpRecyclerView(listOf())

        setOnClickListeners()
        setKeyChangeListeners()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[VehicleInfoViewModel::class.java]
        // TODO: Use the ViewModel
    }


    /**
     * Fill brands list object with BrandInfo objects to optimize key change suggestions.
     * Sort list alphabetically for suggestions optimisation because suggestion list length is limited.
     */
    private fun fillBrandsList() {
        try {
            val gson = Gson()
            val inputStream: InputStream = resources.openRawResource(R.raw.brands)
            val byteArrayOutputStream = ByteArrayOutputStream()

            var ctr: Int
            try {
                ctr = inputStream.read()
                while (ctr != -1) {
                    byteArrayOutputStream.write(ctr)
                    ctr = inputStream.read()
                }
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val jsonString: String = byteArrayOutputStream.toString()
            brandsList = gson.fromJson(jsonString, Array<BrandInfo>::class.java).asList()
                .sortedBy { it.Make_Name }

        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }


    /**
     * Set up recyclerview for brand suggestions.
     */
    private fun setUpRecyclerView(brands: List<BrandInfo>) {
        rv_brands.layoutManager = GridLayoutManager(requireContext(), 1)

        adapter = BrandListAdapter(brands, requireContext(), sharedViewModel)
        rv_brands.adapter = adapter
    }


    /**
     * Set navigation click listeners.
     */
    private fun setOnClickListeners() {
        iv_back.setOnClickListener {
            // TODO navigate back
        }

        iv_close.setOnClickListener {
            // TODO cancel vehicle adding
        }
    }


    /**
     * Set listeners for changes in brand name edit text view.
     * On any key change, a list of brand suggestions is made.
     */
    private fun setKeyChangeListeners() {
        et_brand.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) { }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                handleDisplayedBrands(et_brand.text.toString())
            }
        })
    }


    /**
     * Method for making brand name suggestions in recyclerview.
     * Max length is 10 as to optimise performance and better the user experience.
     *
     * @param substring: value of brand name in edit text
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun handleDisplayedBrands(substring: String) {
        val tmpBrandsList = mutableListOf<BrandInfo>()

        brandsList.forEach { brand ->
            if(brand.Make_Name.startsWith(substring)) {
                tmpBrandsList.add(brand)
            }

            if(tmpBrandsList.size >= 10) {
                setUpRecyclerView(tmpBrandsList)
                return
            }
        }

        Log.v("tmp brands", tmpBrandsList.toString())

        adapter.setItems(tmpBrandsList)
        adapter.notifyDataSetChanged()
    }


    /**
     * Clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}