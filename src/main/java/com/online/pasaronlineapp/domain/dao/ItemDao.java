package com.online.pasaronlineapp.domain.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.online.pasaronlineapp.domain.common.BaseDao;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ITEMS")
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_price", nullable = false)
    private Integer itemPrice;

    @Column(name = "item_weight", nullable = false)
    private Integer itemWeight;

    @Column(name = "item_stock", nullable = false)
    private Integer itemStock;

    @Column(name = "item_description")
    private String itemDescription;

    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "item_image", columnDefinition = "BYTEA")
    private byte[] itemImage;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ToString.Exclude
    private CategoryDao categoryDao;

    @OneToMany(mappedBy = "itemDao", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<PackageItemDao> packageItemDaos;

    @OneToMany(mappedBy = "item", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<CartDao> cartDaos;

    @OneToMany(mappedBy = "item")
    @ToString.Exclude
    private List<OrderDetailDao> orderDetailDaos;
}
