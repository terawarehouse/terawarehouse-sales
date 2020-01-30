/**
 * An Open Source Inventory and Sales Management System
 * Copyright (C) 2019 Edward P. Legaspi (https://github.com/czetsuya)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.terawarehouse.data.entity.sales;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.broodcamp.data.entity.BaseEntity;
import com.broodcamp.data.entity.DatePeriod;
import com.broodcamp.data.entity.IObservable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 * 
 * @since 0.0.1
 * @version 0.0.1
 */
@Entity
@IObservable
@Table(name = "sales_order", uniqueConstraints = @UniqueConstraint(columnNames = { "date_from", "date_to", "trading_promoter_branch_id" }))
@NamedQueries({
        @NamedQuery(name = "Order.findDatePeriodByMonthAndPromoterBranch", query = "SELECT o FROM Order o WHERE TO_CHAR(o.datePeriod.from, 'YYYY-MM')=:yearMonth AND o.tradingPromoterBranch.id=:tradingPromoterBranchId AND o.orderStatus=:orderStatus"), //
        @NamedQuery(name = "Order.findDatePeriodByMonth", query = "SELECT o FROM Order o WHERE TO_CHAR(o.datePeriod.from, 'YYYY-MM')=:yearMonth AND o.orderStatus=:orderStatus"), //
        @NamedQuery(name = "Order.updateDatePeriodByMonth", query = "UPDATE Order o SET o.orderStatus=:toOrderStatus WHERE TO_CHAR(o.datePeriod.from, 'YYYY-MM')=:yearMonth AND o.orderStatus=:fromOrderStatus") //
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class Order extends BaseEntity {

    private static final long serialVersionUID = -7948792649994805076L;

    @NotNull
    @AttributeOverrides({ @AttributeOverride(name = "from", column = @Column(name = "date_from")), @AttributeOverride(name = "to", column = @Column(name = "date_to")) })
    private DatePeriod datePeriod;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "trading_promoter_branch_id", nullable = false, updatable = false)
    private TradingPromoterBranch tradingPromoterBranch;

    @Column(name = "no_sale")
    private boolean noSale;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", length = 10)
    private OrderStatusEnum orderStatus = OrderStatusEnum.OPEN;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethodEnum paymentMethod = PaymentMethodEnum.CASH;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    private List<OrderItem> items;
}
