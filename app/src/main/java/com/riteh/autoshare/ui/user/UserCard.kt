package com.riteh.autoshare.ui.user

class UserCard(
    var cardNumber: String,
    var expirationMonth: String,
    var expirationYear: String,
    var cvv: String,
    var cardholderName: String,
    var cardholderSurname: String,
    var address: String,
    var city: String,
    var postalCode: String,
    var countryCode: String,
    var mobileNumber: String
){
    var cardsList = mutableListOf<UserCard>()
}

