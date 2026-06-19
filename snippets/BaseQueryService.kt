package dev.portfolio.base.query

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

abstract class BaseQueryService<E, ID> {
    abstract suspend fun query(pageable: Pageable): Page<E>
    abstract suspend fun query(id: ID): E
    abstract suspend fun query(ids: Collection<ID>): List<E>
}
