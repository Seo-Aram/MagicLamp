package com.app.magiclamp.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tbl_review")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@DynamicInsert
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer reviewindex;

    @Column(columnDefinition = "char(13) not null")
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "reviewer")
    private User reviewer;

    @Column(columnDefinition = "int not null")
    private Integer star;

    @Column(columnDefinition = "longtext not null")
    private String reviewcontent;

    @Column(columnDefinition = "timestamp not null default current_timestamp()", updatable = false)
    private LocalDate regdate;
}
