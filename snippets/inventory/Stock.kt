package dev.portfolio.inventory

import dev.portfolio.base.domain.BaseAggregateRoot
import dev.portfolio.base.domain.DomainEvent
import dev.portfolio.base.query.BaseQueryController
import dev.portfolio.base.query.BaseQueryService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@JvmInline value class StockId(val value: String)

class Stock(
    val id: StockId,
    private var quantity: Int,
) : BaseAggregateRoot<Stock>() {

    fun deduct(amount: Int) {
        require(quantity >= amount) { "insufficient" }
        quantity -= amount
        registerEvent(StockDeducted(id, amount))
    }
}

data class StockDeducted(val id: StockId, val amount: Int) : DomainEvent

class StockQueryService : BaseQueryService<Stock, StockId>() {
    override suspend fun query(pageable: Pageable): Page<Stock> = TODO("repository 위임")
    override suspend fun query(id: StockId): Stock = TODO("없으면 ErrorCode.STOCK_NOT_FOUND")
    override suspend fun query(ids: Collection<StockId>): List<Stock> = TODO("repository 위임")
}

class StockController(service: StockQueryService) : BaseQueryController<Stock, StockId>(service)
