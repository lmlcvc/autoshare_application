package com.riteh.autoshare.ui.home.user.vehicles

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.riteh.autoshare.adapters.ModelListAdapter
import com.riteh.autoshare.data.dataholders.ModelInfo
import com.riteh.autoshare.databinding.FragmentModelBinding
import com.riteh.autoshare.network.BrandModelApi
import kotlinx.android.synthetic.main.fragment_model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ModelFragment : Fragment() {

    private var binding: FragmentModelBinding? = null

    private lateinit var adapter: ModelListAdapter
    private lateinit var modelsList: MutableList<ModelInfo>

    private val sharedViewModel: VehicleInfoViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentModelBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.modelFragment = this

        makeAPIRequest()
        setUpRecyclerView(mutableListOf())

        setOnClickListeners()
        setKeyChangeListeners()
    }


    /**
     * Normalise characters to English alphabet, e.g. "č" becomes "c".
     */
    private fun String.normalise(): String {
        val original = arrayOf("ę", "š")
        val normalized = arrayOf("e", "s")

        return this.map {
            val index = original.indexOf(it.toString())
            if (index >= 0) normalized[index] else it
        }.joinToString("")
    }


    /**
     * Fetch all models of brand and store them to list.
     * Apply formatting: to uppercase and normalised.
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun makeAPIRequest() {
        val api: BrandModelApi = Retrofit.Builder()
            .baseUrl(BrandModelApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(BrandModelApi::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getModelsByBrand(sharedViewModel.brand.value.toString())

                modelsList = response.Results
                modelsList.forEach { model ->
                    model.Model_Name = model.Model_Name.uppercase()
                    model.Model_Name.normalise()
                }
            } catch (e: Exception) {
                println(e.toString())
            }
        }
    }


    /**
     * Set up recyclerview with brand suggestions.
     */
    private fun setUpRecyclerView(models: MutableList<ModelInfo>) {
        rv_models.layoutManager = GridLayoutManager(requireContext(), 1)

        adapter = ModelListAdapter(models, requireContext(), sharedViewModel)
        rv_models.adapter = adapter
    }


    /**
     * Set listeners for changes in brand name edit text view.
     * On any key change, a list of brand suggestions is made.
     */
    private fun setKeyChangeListeners() {
        et_brand.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(!modelsList.isNullOrEmpty()) {
                    handleDisplayedModels(et_brand.text.toString())
                }
            }
        })
    }


    /**
     * Method for making model name suggestions in recyclerview.
     * Max length is 10 as to better the user experience.
     *
     * @param substring: value of model name in edit text
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun handleDisplayedModels(substring: String) {
        val tmpModelsList = mutableListOf<ModelInfo>()

        modelsList.forEach { model ->
            if (model.Model_Name.replace("\\s".toRegex(), "")
                    .startsWith(substring.replace("\\s".toRegex(), ""))
            ) {
                tmpModelsList.add(model)
            }

            if (tmpModelsList.size >= 10) {
                adapter.setItems(tmpModelsList)
                adapter.notifyDataSetChanged()
                return
            }
        }

        adapter.setItems(tmpModelsList)
        adapter.notifyDataSetChanged()
    }


    /**
     * Set navigation click listeners.
     */
    private fun setOnClickListeners() {
        iv_back.setOnClickListener {
            // navigate back
        }

        iv_close.setOnClickListener {
            // cancel vehicle adding
        }
    }

    /**
     * Clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}