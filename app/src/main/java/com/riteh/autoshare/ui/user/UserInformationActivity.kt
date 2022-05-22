package com.riteh.autoshare.ui.user

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.riteh.autoshare.R
import com.riteh.autoshare.data.UserPreferences
import com.riteh.autoshare.responses.User
import kotlinx.android.synthetic.main.activity_user_information.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect


class UserInformationActivity : AppCompatActivity() {

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)

        arrow_back.setOnClickListener {
            this.finish()
        }

        btn_edit.setOnClickListener {
            val intent = Intent(it.context, UserUpdateActivity::class.java)
            startActivity(intent)
        }

        val activityContext = this

        runBlocking {
            async {
                getUserFromPreferences(activityContext)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val activityContext = this

        runBlocking {
            async {
                getUserFromPreferences(activityContext)
            }
        }
    }

    private suspend fun getUserFromPreferences(context: UserInformationActivity) {
        val userPreferences = UserPreferences(context)

        GlobalScope.launch(
            Dispatchers.IO
        ) {
            userPreferences.getUserFromDataStore().catch { e ->
                e.printStackTrace()
            }.collect {
                withContext(Dispatchers.Main) {
                    context.user = it

                    name.text = it.name
                    surname.text = it.surname
                    date_of_birth.text = it.date_of_birth.toString()
                    email.text = it.email
                    license_id.text = it.license_id
                    avg_grade_renter.text = it.renter_avg_rating.toString()
                    avg_grade_rentee.text = it.rentee_avg_rating.toString()
                }
            }
        }
    }

}