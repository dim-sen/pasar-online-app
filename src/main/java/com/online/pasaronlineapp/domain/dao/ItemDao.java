package com.online.pasaronlineapp.domain.dao;

import com.online.pasaronlineapp.domain.comman.BaseDao;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ITEMS")
@SQLDelete(sql = "UPDATE ITEMS SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
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

    @Column(name = "item_image", nullable = false)
    private String itemImage;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<PackageItemDao> packageItemDaos;
}
