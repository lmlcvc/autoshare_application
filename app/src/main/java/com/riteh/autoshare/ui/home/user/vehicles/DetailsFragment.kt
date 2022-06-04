package com.riteh.autoshare.ui.home.user.vehicles

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.riteh.autoshare.R
import com.riteh.autoshare.data.UserPreferences
import com.riteh.autoshare.databinding.FragmentDetailsBinding
import kotlinx.android.synthetic.main.fragment_brand.*
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_details.iv_close
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.regex.Pattern
import kotlinx.coroutines.flow.collect


class DetailsFragment : Fragment() {

    private var binding: FragmentDetailsBinding? = null

    private val sharedViewModel: VehicleInfoViewModel by activityViewModels()

    val SELECT_PICTURE = 200
    private lateinit var selectedImageBase64: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.detailsFragment = this

        setOnClickListeners()
        setKeyChangeListeners()
        button.isClickable = false
    }


    private suspend fun appendUserIdToVehicle(context: VehicleAddActivity) {
        val userPreferences = UserPreferences(context)

        GlobalScope.launch(
            Dispatchers.IO
        ) {
            userPreferences.getUserFromDataStore().catch { e ->
                e.printStackTrace()
            }.collect {
                withContext(Dispatchers.Main) {
                    sharedViewModel.setOwnerID(it.id)
                    sharedViewModel.createVehicle()
                }
            }
        }
    }


    private fun setOnClickListeners() {
        iv_close.setOnClickListener {
            requireActivity().finish()
        }

        button.setOnClickListener {
            sharedViewModel.setDetails(
                et_seats.text.toString(),
                et_doors.text.toString(),
                et_year.text.toString(),
                et_license.text.toString(),
                et_expiration.text.toString(),
                selectedImageBase64,
                et_description.text.toString()
            )

            runBlocking {
                async {
                    appendUserIdToVehicle(requireActivity() as VehicleAddActivity)
                    // sharedViewModel.createVehicle()
                }
            }

            findNavController().navigate(R.id.action_detailsFragment_to_vehicleAddingCompletedFragment)
        }

        btn_add_image.setOnClickListener {
            loadImageFromGallery()
        }
    }


    /**
     * Set listeners for editable items.
     *
     * - required fields input watchers for button enabling/disabling
     * - formatters for license and date fields
     */
    private fun setKeyChangeListeners() {
        et_seats.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                checkRequiredInputs()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        et_doors.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                checkRequiredInputs()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        et_year.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                checkRequiredInputs()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        et_license.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                checkRequiredInputs()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                applyLicenseFormatting()
            }
        })

        et_expiration.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                checkRequiredInputs()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (before <= 0) {  // if text is being written, not deleted
                    applyDateFormatting(before)
                }
            }
        })
    }


    /**
     * Check if required input fields have been filled and change button functionality accordingly.
     */
    private fun checkRequiredInputs() {
        if (et_seats.text.toString().isNotEmpty()
            && et_doors.text.toString().isNotEmpty()
            && et_year.text.toString().isNotEmpty()
            && et_license.text.toString().isNotEmpty()
            && dateIsValid(et_expiration.text.toString())
            && this::selectedImageBase64.isInitialized
        ) {
            button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.basic_red))
            button.isClickable = true
        } else {
            button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_grey))
            button.isClickable = false
        }
    }


    /**
     * Add a "-" after day or month input.
     * Handle moving the cursor to end of string when setText is applied.
     */
    private fun applyDateFormatting(before: Int) {
        if (et_expiration.text.toString().length <= before) return

        when (et_expiration.text.toString().length) {
            2 -> {
                val tmpExpirationText = "${et_expiration.text.toString()}-"
                et_expiration.setText(tmpExpirationText)
                et_expiration.setSelection(et_expiration.length())
            }
            5 -> {
                val tmpExpirationText = "${et_expiration.text.toString()}-"
                et_expiration.setText(tmpExpirationText)
                et_expiration.setSelection(et_expiration.length())
            }
            else -> return

        }
    }


    /**
     * Format license string to be uppercase and contain only alphanumeric characters.
     * Handle moving the cursor to end of string when setText is applied.
     */
    private fun applyLicenseFormatting() {
        val currentLicenseText = et_license.text.toString()
        if (currentLicenseText.lastIndex >= 0
            && !currentLicenseText[currentLicenseText.lastIndex].isLetterOrDigit()
        ) {
            val tmpLicenseText = et_license.text.toString().dropLast(1)

            et_license.setText(tmpLicenseText)
            et_license.setSelection(et_license.length())
        }
    }


    /**
     * @return if date matches DD-MM-YYYY pattern
     */
    private fun dateIsValid(date: String): Boolean {
        val pattern: Pattern = Pattern.compile(
            "^\\d{2}-\\d{2}-\\d{4}$"
        )

        return pattern.matcher(date).matches()
    }


    /**
     * Specify intent for loading image and launch handler.
     */
    private fun loadImageFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        selectedImageHandler.launch(intent)
    }


    /**
     * Triggered when the user selects an image from gallery.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {
                val selectedImageUri: Uri? = data?.data
                if (null != selectedImageUri) {
                    iv_add_image.setImageURI(selectedImageUri)
                }
            }
        }
    }


    /**
     * Load image URI, apply default resolution and perform necessary UI changes.
     */
    private var selectedImageHandler =
        registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                if (data != null && data.data != null) {
                    val selectedImageUri: Uri? = data.data

                    try {
                        val selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                            requireActivity().contentResolver,
                            selectedImageUri
                        )

                        val maxSize = 720
                        val outWidth: Int
                        val outHeight: Int
                        if (selectedImageBitmap.width > selectedImageBitmap.height) {
                            outWidth = maxSize
                            outHeight =
                                selectedImageBitmap.height * maxSize / selectedImageBitmap.width
                        } else {
                            outHeight = maxSize
                            outWidth =
                                selectedImageBitmap.width * maxSize / selectedImageBitmap.height
                        }

                        val resizedBitmap =
                            Bitmap.createScaledBitmap(
                                selectedImageBitmap,
                                outWidth,
                                outHeight,
                                false
                            )

                        selectedImageBase64 = encodeBitmapToBase64(resizedBitmap)

                        iv_added_image.setImageBitmap(selectedImageBitmap)
                        iv_added_image.visibility = VISIBLE
                        iv_add_image.visibility = INVISIBLE
                        tv_add_image.visibility = INVISIBLE

                        btn_add_image.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.light_grey
                            )
                        )
                        btn_add_image.text = getString(R.string.change)

                        checkRequiredInputs()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }


    private fun encodeBitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }


    /**
     * Clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}