package dev.portfolio.base.event

import dev.portfolio.base.domain.DomainEvent
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

class DomainEventForwarder(
    private val broker: BrokerPublisher,
) {
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun on(event: DomainEvent) = broker.publish(event)
}

interface BrokerPublisher {
    fun publish(event: DomainEvent)
}
