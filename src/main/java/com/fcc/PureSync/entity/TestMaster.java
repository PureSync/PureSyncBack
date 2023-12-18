package com.fcc.PureSync.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_test_master")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testSeq;
    private String testName;
}
