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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.broodcamp.data.entity.BaseEntity;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 * 
 * @since 0.0.1
 * @version 0.0.1
 */
@Entity
@Table(name = "sales_order_item" //
        , uniqueConstraints = @UniqueConstraint(columnNames = { "product_model_id", "serial_no", "date_sold", "invoice_no" }) //
        , indexes = { @Index(columnList = "warranty_card_no", unique = false) } //
)
@SequenceGenerator(allocationSize = 1, name = "ID_GENERATOR", sequenceName = "sales_order_item_seq")
@NamedQueries({
        @NamedQuery(name = "OrderItem.countByMonth", query = "SELECT i.productModel.id, b.id, SUM(i.quantity), SUM(i.amountWithoutTax) from OrderItem i JOIN i.order o JOIN o.tradingPromoterBranch b where o.orderStatus=:orderStatus AND i.dateSold>=:startDate AND i.dateSold<=:endDate GROUP BY b.promoter.id, i.productModel.id, b.id") })
public class OrderItem extends BaseEntity {

    private static final long serialVersionUID = -2829917627087992153L;

    @QueryInit({ "tradingPromoterBranch.promoter" })
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, updatable = false)
    private Order order;

    @NotNull
    @Column(name = "product_id", columnDefinition = "uuid", updatable = false)
    private UUID productId;

    @Column(name = "serial_no", length = 100)
    private String serialNo;

    @NotNull
    @Column(name = "invoice_no", length = 100)
    private String invoiceNo;

    @NotNull
    @Column(name = "sidr", length = 100)
    private String sidr;

    @NotNull
    @Column(name = "unit_amount_without_tax", precision = NB_PRECISION, scale = NB_DECIMALS)
    private BigDecimal unitAmountWithoutTax;

    @NotNull
    @Column(name = "quantity", precision = NB_PRECISION, scale = NB_DECIMALS)
    private BigDecimal quantity;

    @Column(name = "amount_without_tax", precision = NB_PRECISION, scale = NB_DECIMALS)
    private BigDecimal amountWithoutTax;

    @NotNull
    @Column(name = "srp", precision = NB_PRECISION, scale = NB_DECIMALS)
    @Digits(integer = NB_PRECISION, fraction = NB_DECIMALS)
    private BigDecimal srp;

    @Column(name = "remarks", length = 250)
    private String remarks;

    @NotNull
    @Column(name = "date_sold")
    @Temporal(TemporalType.DATE)
    private Date dateSold;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "sales_order_attachment", joinColumns = @JoinColumn(name = "order_item_id"))
    public List<OrderAttachment> attachments;

    @Column(name = "for_delivery")
    private boolean forDelivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "warranty_card_no", length = 100)
    private String warrantyCardNo;

}
