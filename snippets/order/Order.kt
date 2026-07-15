package dev.portfolio.order

import dev.portfolio.base.domain.BaseAggregateRoot
import dev.portfolio.base.domain.DomainEvent
import dev.portfolio.base.query.BaseQueryController
import dev.portfolio.base.query.BaseQueryService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@JvmInline value class OrderId(val value: String)

class Order(
    val id: OrderId,
    private var placed: Boolean = false,
) : BaseAggregateRoot<Order>() {

    fun place() {
        check(!placed) { "already placed" }
        placed = true
        registerEvent(OrderPlaced(id))
    }
}

data class OrderPlaced(val id: OrderId) : DomainEvent

class OrderQueryService : BaseQueryService<Order, OrderId>() {
    override suspend fun query(pageable: Pageable): Page<Order> = TODO("repository 위임")
    override suspend fun query(id: OrderId): Order = TODO("없으면 ErrorCode.ORDER_NOT_FOUND")
    override suspend fun query(ids: Collection<OrderId>): List<Order> = TODO("repository 위임")
}

class OrderController(service: OrderQueryService) : BaseQueryController<Order, OrderId>(service)
