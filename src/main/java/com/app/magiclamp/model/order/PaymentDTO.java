package com.app.magiclamp.model.order;

import com.app.magiclamp.entity.Order;
import com.app.magiclamp.model.book.BookInfoDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PaymentDTO {

    // 여러 개의 책 정보를 가진 주문서를 보낼 때 사용할 DTO(server 사용)

    private int userindex;
    private int orderindex;
    private int realprice;
    private String pg;
    private String paytype;

    // 책 정보를 갖는 List
    private List<BookInfoDTO> bookInfos;

    // 이전 페이지(도서 상세)에서 가지고 올 data
    private String isbn;
    private int bookcount;

    // 필요한 data
    private String title; // 책 이름
    private int price; // 책 정가
    private Integer saleprice; // 판매가

    // 구매액과 마일리지

    private int mileage;
    private int usemileage; // 사용 마일리지

    // 배송정보
    private String recipient;
    private String phone;
    private String postnum;
    private String address1;
    private String address2;

    // 주문서 넘기고 결제완료시 필요한 data
    private LocalDate orderdate; // 주문날짜
    private boolean purchasestate; // 0: 구매, 1: 반품


    // DB에 없는 data
    private int orderTotalPrice; // 주문시 총 결제금액

    private int saveMileage; // 권당 적립 마일리지
    private int totalSaveMileage; // 주문시 총 적립 마일리지

    private List<RequestPaymentBook> orders;


    // 주문시 실 결제금액 계산 메서드
    /*public void calTotalprice(){

        for(RequestPaymentBook order : orders){
            orderTotalPrice += order.getTotalPrice();
            totalSaveMileage += order.getTotalMileage();
        }

        realprice = orderTotalPrice - usemileage + totalSaveMileage;

    }*/

    public Order toOrderEntity(){
        return Order.builder()
                .isbn(isbn)
                .bookcount(bookcount)
                .realprice(realprice)
                .mileage(totalSaveMileage)
                .name(recipient)
                .phone(phone)
                .postnum(postnum)
                .address1(address1)
                .address2(address2)
                .userindex(userindex)
                .build();
    }

}
