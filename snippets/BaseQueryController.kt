package dev.portfolio.base.query

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

abstract class BaseQueryController<E, ID>(
    private val service: BaseQueryService<E, ID>,
) {
    suspend fun list(pageable: Pageable): Page<E> = service.query(pageable)
    suspend fun get(id: ID): E = service.query(id)
}
