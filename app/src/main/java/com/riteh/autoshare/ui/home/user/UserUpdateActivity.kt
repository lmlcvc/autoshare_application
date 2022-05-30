package com.riteh.autoshare.ui.home.user

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.riteh.autoshare.R
import com.riteh.autoshare.data.UserPreferences
import com.riteh.autoshare.network.RemoteDataSource
import com.riteh.autoshare.network.UserApi
import com.riteh.autoshare.repository.UserRepository
import com.riteh.autoshare.responses.User
import kotlinx.android.synthetic.main.activity_user_update.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*

class UserUpdateActivity : AppCompatActivity() {
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_update)

        val repo = UserRepository(RemoteDataSource().buildApi(UserApi::class.java))
        val activityContext = this

        runBlocking {
            async {
                getUserFromPreferences(activityContext)
            }
        }

        btn_save.setOnClickListener {
            runBlocking {
                async {
                    repo.userUpdate(
                        user.id,
                        update_user_name.text.toString(),
                        update_user_surname.text.toString(),
                        update_user_email.text.toString(),
                        "24543643",
                        Date(2000 - 1900, 0, 1)
                    )
                    updateUser(
                        update_user_name.text.toString(),
                        update_user_surname.text.toString(),
                        update_user_email.text.toString()
                    )
                    Toast.makeText(
                        this@UserUpdateActivity,
                        R.string.profile_updated,
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }

        }

        arrow_back.setOnClickListener {
            this.finish()
        }
    }

    private suspend fun updateUser(name: String, surname: String, email: String) {
        val userPreferences = UserPreferences(this@UserUpdateActivity)

        userPreferences.updateUserInfo(name, surname, email)
    }

    @SuppressLint("SimpleDateFormat")
    private suspend fun getUserFromPreferences(context: UserUpdateActivity) {
        val userPreferences = UserPreferences(context)
        val df = SimpleDateFormat("dd/MM/yyyy")

        GlobalScope.launch(
            Dispatchers.IO
        ) {
            userPreferences.getUserFromDataStore().catch { e ->
                e.printStackTrace()
            }.collect {
                withContext(Dispatchers.Main) {
                    context.user = it

                    update_user_name.setText(it.name)
                    update_user_surname.setText(it.surname)
                    update_user_email.setText(it.email)
                    update_user_birthday.setText(df.format(it.date_of_birth))
                    update_user_license.setText(it.license_id)
                }
            }
        }
    }
}