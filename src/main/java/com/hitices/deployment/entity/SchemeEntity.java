package com.hitices.deployment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wangteng
 * @email willtynn@outlook.com
 * @date 2024/3/11 18:18
 */
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "deployment_scheme")
public class SchemeEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = true)
    private String name;
    @Column(name = "namespace", nullable = true)
    private String namespace;
    @Column(name = "status", nullable = true)
    private Integer status;
    @Column(name = "data", nullable = true)
    private String data;
    @Column(name = "time", nullable = true)
    private Date time;

    public SchemeEntity() {

    }

    public SchemeEntity(String name, String namespace, String json) {
        this.name = name;
        this.namespace = namespace;
        this.data = json;
        this.time = new Date();
        this.status = 1;
    }
}
