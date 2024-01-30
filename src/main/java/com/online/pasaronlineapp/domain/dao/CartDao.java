package com.online.pasaronlineapp.domain.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.online.pasaronlineapp.domain.common.BaseDao;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CARTS")
public class CartDao extends BaseDao {

    @Id
    private Long id;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "pembeli_id")
    @ToString.Exclude
    private PembeliDao pembeli;

    @OneToMany(mappedBy = "cartDao", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<CartBarangDao> cartBarangDaos;
}
