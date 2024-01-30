package com.online.pasaronlineapp.domain.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "BARANGS")
public class BarangDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "barang_name", nullable = false)
    private String barangName;

    @Column(name = "barang_price", nullable = false)
    private Integer barangPrice;

    @Column(name = "barang_weight", nullable = false)
    private Integer barangWeight;

    @Column(name = "barang_stock", nullable = false)
    private Integer barangStock;

    @Column(name = "barang_description")
    private String barangDescription;

    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "barang_image", columnDefinition = "BYTEA")
    private byte[] barangImage;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ToString.Exclude
    private CategoryDao categoryDao;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    @ToString.Exclude
    private AdminDao adminDao;

    @OneToMany(mappedBy = "barangDao", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<PackageBarangDao> packageItemDaos;

    @OneToMany(mappedBy = "barangDao", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<CartBarangDao> cartBarangDaos;

    @OneToMany(mappedBy = "barang")
    @ToString.Exclude
    private List<OrderDetailDao> orderDetailDaos;
}
