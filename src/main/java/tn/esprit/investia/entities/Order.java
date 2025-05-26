package tn.esprit.investia.entities;

import jakarta.persistence.*;
import lombok.Data;
import tn.esprit.investia.domain.OrderStatus;
import tn.esprit.investia.domain.OrderType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders") // Use "orders" as the table name to avoid conflicts with SQL keywords
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use IDENTITY for auto-increment
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Use LAZY fetching for performance
    @JoinColumn(name = "user_id", nullable = false) // Define the foreign key column
    private User user;

    @Enumerated(EnumType.STRING) // Store enum as a string in the database
    @Column(nullable = false)
    private OrderType orderType;

    @Column(nullable = false, precision = 19, scale = 2) // Define precision and scale for BigDecimal
    private BigDecimal price;

    @Column(nullable = false, updatable = false) // Prevent updates to the timestamp
    private LocalDateTime timestamp = LocalDateTime.now();

    @Enumerated(EnumType.STRING) // Store enum as a string in the database
    @Column(nullable = false)
    private OrderStatus status;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) // Ensure orphan removal
    private OrderItem orderItem;

    // Helper method to manage the bidirectional relationship with OrderItem
    public void setOrderItem(OrderItem orderItem) {
        if (orderItem == null) {
            if (this.orderItem != null) {
                this.orderItem.setOrder(null);
            }
        } else {
            orderItem.setOrder(this);
        }
        this.orderItem = orderItem;
    }


}