package com.example.mediaservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_generator", sequenceName = "orders_seq", allocationSize = 1)
public class Orders extends GenericModel{

    @Column (name = "rent_date", nullable = false)
    private LocalDateTime rentDate;

    @Column (name = "rent_period", nullable = false)
    private LocalDateTime rentPeriod;

    @Column(name = "purchase")
    private boolean purchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", foreignKey = @ForeignKey(name = "FK_ORDER_USER"))
    private Users users;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "films_id", foreignKey = @ForeignKey(name = "FK_ORDER_FILM"))
    private Films films;

}
