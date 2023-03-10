package com.app.magiclamp.model.review;

import com.app.magiclamp.entity.Book;
import com.app.magiclamp.entity.Review;
import com.app.magiclamp.entity.User;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReviewDTO {
    private int reviewindex;
    private String isbn;
    private Integer reviewer;
    private Integer star;
    private String reviewcontent;

    private LocalDate regdate;

    private String username;

    private Integer cnt;

    public Review toReviewEntity(){
        return Review.builder()
                .reviewindex(reviewindex)
                .isbn(Book.builder().isbn(isbn).build())
                .reviewer(User.builder().userindex(reviewer).build())
                .star(star)
                .reviewcontent(reviewcontent)
                .build();
    }
}
