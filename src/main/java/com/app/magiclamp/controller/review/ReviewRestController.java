package com.app.magiclamp.controller.review;

import com.app.magiclamp.model.review.ReviewDTO;
import com.app.magiclamp.entity.Review;
import com.app.magiclamp.service.review.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/review")
@Log4j2
public class ReviewRestController {

    @Autowired
    private ReviewInsertService reviewInsertService;

    @Autowired
    private ReviewListService reviewListService;

    @Autowired
    private ReviewDeleteService reviewDeleteService;

    @Autowired
    private ReviewModifyService reviewModifyService;

    private final UserOrderChkService userOrderChkService;

    public ReviewRestController(UserOrderChkService userOrderChkService) {
        this.userOrderChkService = userOrderChkService;
    }


    @PostMapping
    public ResponseEntity<Review> insertReview(@RequestBody ReviewDTO reviewDTO){
        log.info("insert 전 : " + reviewDTO);

        Review resultReview = reviewInsertService.insertReview(reviewDTO);

        log.info("insert 후 : " + resultReview);
        return new ResponseEntity<>(resultReview, HttpStatus.OK);
    }

    @GetMapping(value = "/{isbn}/{pageNum}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getReviewList(@PathVariable("isbn") String isbn, @PathVariable("pageNum") int pageNum) {
        List<ReviewDTO> list = reviewListService.getList(isbn, pageNum);
        long total = reviewListService.countPeople(isbn);

        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return map;
    }

    @GetMapping(value = "/like/{isbn}/{pageNum}")
    public Map<String, Object> getOrderByLikeReviewList(@PathVariable("isbn") String isbn, @PathVariable("pageNum") int pageNum){
        List<ReviewDTO> list = reviewListService.findAllByLike(isbn, pageNum);
        long total = reviewListService.countPeople(isbn);

        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return map;
    }

    @GetMapping(value = "/star/{isbn}/{pageNum}")
    public Map<String, Object> getOrderByStarReviewList(@PathVariable("isbn") String isbn, @PathVariable("pageNum") int pageNum){
        List<ReviewDTO> list = reviewListService.findAllByHighRating(isbn, pageNum);
        long total = reviewListService.countPeople(isbn);

        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return map;
    }

    @DeleteMapping("/{reviewindex}")
    public ResponseEntity<Integer> deleteReview(@PathVariable("reviewindex") int reviewindex){
        return new ResponseEntity<>(reviewDeleteService.deleteByReviewIndex(reviewindex), HttpStatus.OK);
    }

    @PutMapping("/{reviewindex}")
    public ResponseEntity<Integer> modifyReview(@PathVariable("reviewindex") int reviewindex, @RequestBody ReviewDTO reviewDTO){
        reviewDTO.setReviewindex(reviewindex);
        return new ResponseEntity<>(reviewModifyService.modifyReview(reviewDTO), HttpStatus.OK);
    }

    @GetMapping("/register/check/{isbn}/{userindex}")
    public ResponseEntity<Long> checkOrder(@PathVariable("isbn") String isbn, @PathVariable("userindex") int userindex){
        return new ResponseEntity<>(userOrderChkService.checkOrder(userindex, isbn), HttpStatus.OK);
    }
}
