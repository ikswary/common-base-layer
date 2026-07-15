package dev.portfolio.base.domain

import org.springframework.data.domain.AbstractAggregateRoot
import java.time.Instant

interface DomainEvent

abstract class BaseAggregateRoot<T : BaseAggregateRoot<T>> : AbstractAggregateRoot<T>() {
    var createdAt: Instant? = null
    var updatedAt: Instant? = null
}
