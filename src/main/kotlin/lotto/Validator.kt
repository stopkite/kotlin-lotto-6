package lotto

class Validator(private val input: String) {
    fun isPaymentValid(): Boolean = ErrorHandler().checkWithExceptionHandler(this::validateIsNumber,
        ErrorMessage.INVALID_PAYMENT_NUMBER.message, ErrorType.NumberFormatException) &&
            ErrorHandler().checkWithExceptionHandler(this::validateIsUpperToThousand, ErrorMessage.INVALID_MINIMUM_PAYMENT.message, ErrorType.IllegalArgumentException) &&
            ErrorHandler().checkWithExceptionHandler(this::validateIsCorrectUnit, ErrorMessage.DIVIDE_TO_THOUSAND.message, ErrorType.IllegalArgumentException)

    private fun validateIsNumber(): Boolean = input.toIntOrNull() is Int

    private fun validateIsUpperToThousand(): Boolean {
        val payment = input.toInt()
        return payment >= 1_000
    }

    private fun validateIsCorrectUnit(): Boolean {
        val payment = input.toInt()
        return payment % 1_000 == 0
    }

    fun isWinningNumbersValid(): Boolean =
            ErrorHandler().checkWithExceptionHandler(this::validateIsNotStartWithComma,
                ErrorMessage.COMMA_START.message, ErrorType.IllegalArgumentException) &&
            ErrorHandler().checkWithExceptionHandler(this::validateIsNotEndWithComma,
                ErrorMessage.COMMA_END.message, ErrorType.IllegalArgumentException) &&
            ErrorHandler().checkWithExceptionHandler(this::validateAreNumbers,
                ErrorMessage.INVALID_WINNING_NUMBER.message, ErrorType.NumberFormatException) &&
            ErrorHandler().checkWithExceptionHandler(this::validateIsUnique, ErrorMessage.SAME_NUMBER.message, ErrorType.IllegalArgumentException) &&
            ErrorHandler().checkWithExceptionHandler(this::validateNumberCount, ErrorMessage.LOTTO_NUMBER_COUNT.message, ErrorType.IllegalArgumentException) &&
            ErrorHandler().checkWithExceptionHandler(this::validateNumbersRange, ErrorMessage.NUMBER_RANG.message, ErrorType.IllegalArgumentException)

    private fun validateIsNotStartWithComma() :Boolean = !input.startsWith(",")

    private fun validateIsNotEndWithComma(): Boolean = !input.endsWith(",")

    private fun validateAreNumbers(): Boolean {
        val winningNumbers = input.split(",")
        return winningNumbers.all { it.toIntOrNull() is Int }
    }

    private fun validateIsUnique(): Boolean {
        val winningNumbers = input.split(",").map { it.toInt() }
        return winningNumbers.toSet().size == winningNumbers.size
    }

    private fun validateNumberCount(): Boolean {
        val winningNumbers = input.split(",").map { it.toInt() }
        return winningNumbers.size == LOTTO_NUMBER_COUNT
    }

    private fun validateNumbersRange(): Boolean {
        val winningNumbers = input.split(",").map { it.toInt() }
        return winningNumbers.all { it in MIN_LOTTO_NUMBER..MAX_LOTTO_NUMBER }
    }

    fun isBonusBallValid(winningNumbers:List<Int>) = ErrorHandler().checkWithExceptionHandler(this::validateIsNumber, ErrorMessage.INVALID_BONUS_NUMBER.message, ErrorType.NumberFormatException) &&
            ErrorHandler().checkWithExceptionHandler({validateIsBallSameWithWinningNumbers(winningNumbers)}, ErrorMessage.SAME_WITH_WINNING_NUMBER.message, ErrorType.IllegalArgumentException) &&
            ErrorHandler().checkWithExceptionHandler(this::validateBonusRange, ErrorMessage.NUMBER_RANG.message, ErrorType.IllegalArgumentException)

    private fun validateIsBallSameWithWinningNumbers(winningNumbers: List<Int>):Boolean {
        val bonusBall = input.toInt()
        return !winningNumbers.contains(bonusBall)
    }

    private fun validateBonusRange(): Boolean = input.toInt() in MIN_LOTTO_NUMBER..MAX_LOTTO_NUMBER

    companion object {
        const val MIN_LOTTO_NUMBER = 1
        const val MAX_LOTTO_NUMBER = 45
        const val LOTTO_NUMBER_COUNT = 6
    }
}