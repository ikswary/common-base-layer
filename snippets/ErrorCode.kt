package dev.portfolio.base.error

enum class ErrorCode(val status: Int, val code: String, val message: String) {
    UNKNOWN(500, "E-CMN-0000", "Unknown error"),

    ORDER_NOT_FOUND(404, "E-ORD-0001", "Order not found"),
    ORDER_ALREADY_PLACED(409, "E-ORD-0002", "Order already placed"),

    STOCK_NOT_FOUND(404, "E-INV-0001", "Stock not found"),
    STOCK_INSUFFICIENT(409, "E-INV-0002", "Insufficient stock"),
    ;
}
