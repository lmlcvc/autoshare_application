package com.riteh.autoshare.ui.user

class CardNumbersFormat {
    fun formatCardNumber(cardNumber: String): String {
        var formattedNumber = ""
        for(i in cardNumber.indices){
            formattedNumber +=
            if (i > 1 && i < cardNumber.length - 4){
                'X'
            } else{
                cardNumber[i]
            }
        }
        return formattedNumber
    }
}