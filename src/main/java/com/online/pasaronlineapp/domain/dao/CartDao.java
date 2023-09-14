package com.online.pasaronlineapp.domain.dao;

import com.online.pasaronlineapp.domain.common.BaseDao;
import com.online.pasaronlineapp.domain.common.UserItemKey;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CARTS")
@SQLDelete(sql = "UPDATE CARTS SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
@IdClass(UserItemKey.class)
public class CartDao extends BaseDao {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id
    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @MapsId(value = "userId")
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserDao user;

    @ManyToOne
    @MapsId(value = "itemId")
    @JoinColumn(name = "item_id")
    @ToString.Exclude
    private ItemDao items;
}
