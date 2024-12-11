package com.eventhub.dti.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.eventhub.dti.entity.base.BaseEntity;
import com.eventhub.dti.entity.enums.PromotionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "promotions", uniqueConstraints = {
        @UniqueConstraint(name = "uk_promotions_event_code", columnNames = { "event_id", "code" })
}, indexes = {
        @Index(name = "idx_promotions_code", columnList = "code"),
        @Index(name = "idx_promotions_dates", columnList = "start_date, end_date")
})
public class Promotion extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotNull(message = "Event is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @NotBlank(message = "Promotion code is required")
    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @NotNull(message = "Promotion type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PromotionType type;

    @NotNull(message = "Promotion amount is required")
    @Positive(message = "Promotion amount must be positive")
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @NotNull(message = "Promotion max uses is required")
    @Min(value = 0, message = "Max uses must be greater than or equal to 0")
    @Column(name = "max_uses", nullable = false)
    private Integer maxUses;

    @NotNull(message = "Start date is required")
    @Future(message = "Start date must be in the future")
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;
}
