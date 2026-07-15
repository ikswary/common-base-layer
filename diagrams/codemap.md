# 코드맵 — 공통 베이스 위 두 서비스 (같은 모양)

공통 베이스(Aggregate·Query·Controller·ErrorCode) 한 벌 위에 도메인이 다른 두 서비스가
*같은 모양*으로 올라간다. 서비스가 2개든 10여 개든 운영자가 읽는 구조는 한 장이다.

```mermaid
flowchart TB
    subgraph Common["공통 베이스 / 컨벤션 (팀 공동 자산)"]
        BAR["BaseAggregateRoot<br/>이벤트 수집(registerEvent)"]
        BQS["BaseQueryService «E, ID»<br/>제네릭 조회(pageable·id·ids)"]
        BQC["BaseQueryController «E, ID»<br/>read 엔드포인트 → service 위임(list·get·list(ids))"]
        EC["ErrorCode «enum»<br/>E-PREFIX-NNNN"]
        DEF["DomainEventForwarder<br/>@TransactionalEventListener(AFTER_COMMIT)"]
    end
    subgraph SvcOrder["order 서비스"]
        O1["Order : BaseAggregateRoot"]
        O2["OrderQueryService : BaseQueryService"]
        O3["OrderController : BaseQueryController"]
    end
    subgraph SvcInv["inventory 서비스"]
        I1["Stock : BaseAggregateRoot"]
        I2["StockQueryService : BaseQueryService"]
        I3["StockController : BaseQueryController"]
    end
    BFF["GraphQL BFF<br/>10여 서비스 단일 스키마 집계 (N+1=DataLoader 배치)"]
    Common --> SvcOrder
    Common --> SvcInv
    SvcOrder -. same shape .- SvcInv
    BFF -. read 경로 .-> BQC
    BQC --> BQS
    BRK["브로커"]
    BAR -. "save 시 자동 발행 · 커밋 후 포워딩" .-> DEF
    DEF -. publish .-> BRK
    style Common fill:#f8fafc,stroke:#94a3b8;
    classDef base fill:#dbeafe,stroke:#2563eb,stroke-width:2px,color:#1f2937;
    classDef svc fill:#ffffff,stroke:#9ca3af,color:#1f2937;
    classDef ext fill:#ffffff,stroke:#9ca3af,stroke-dasharray:4 3,color:#1f2937;
    class BAR,BQS,BQC,EC,DEF base;
    class O1,O2,O3,I1,I2,I3 svc;
    class BFF,BRK ext;
```

> 베이스는 Aggregate·Query·**Controller**·ErrorCode를 덮고, write 경로의 **이벤트 발행**까지 표준으로 고정한다 — read는 `GraphQL BFF → BaseQueryController → BaseQueryService`, 이벤트는 `registerEvent → repository.save() 자동 발행 → @TransactionalEventListener(AFTER_COMMIT) 포워딩`.
> 새 서비스 = 베이스 상속 + prefix만. 이벤트는 애그리거트가 수집만 하고, 발행은 프레임워크가 맡는다.
