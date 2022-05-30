package com.riteh.autoshare.ui.home.user

class UserCard(
    var cardNumber: String,
    var cardDue: String,
    var cardCvv: String,
    var cardholderName: String,
    var cardholderSurname: String,
    var address: String,
    var city: String,
    var postalCode: String,
    var country: String,
){
    var cardsList = mutableListOf<UserCard>()
}

