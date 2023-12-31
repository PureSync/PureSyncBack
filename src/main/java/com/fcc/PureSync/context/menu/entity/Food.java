package com.fcc.PureSync.context.menu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="tb_food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodSeq;
    private String foodName;
    private Double foodCar;
    private Double foodPro;
    private Double foodFat;
    private Double foodSugar;
    private Integer foodNa;
    private Double foodCol;
    private Integer foodKcal;
    private String foodCheckbox;

}
