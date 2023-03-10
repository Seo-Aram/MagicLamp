package com.app.magiclamp.service.order;

import com.app.magiclamp.entity.*;
import com.app.magiclamp.model.book.BookInfoDTO;
import com.app.magiclamp.model.order.RequestPaymentBook;
import com.app.magiclamp.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
public class OrderInsertService{
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MileageRepository mileageRepository;
    @Autowired
    private AddrBookRepository addrBookRepository;

    @Transactional
    public int Order(RequestPaymentBook paymentBook, int userindex) throws Exception{

        log.info(" order insert service ... ");

        int result = 0;

        // 주문할 책 정보
        List<BookInfoDTO> bookInfoDTO = paymentBook.getBookInfos();

        for(int i=0; i<bookInfoDTO.size(); i++) {

            log.info(" >>> is isbn exist? >>> " + bookInfoDTO.get(i).getIsbn());
            log.info(" >>> bookCount >>> " + bookInfoDTO.get(i).getBookcount());

            paymentBook.setIsbn(bookInfoDTO.get(i).getIsbn()); // 구매할 책 isbn set -> 주문내역 확인용
            int payCnt = bookInfoDTO.get(i).getBookcount(); // 구매할 책 권수

            // 최신 재고에 반영
            Book book = bookRepository.findById(paymentBook.getIsbn()).get();

            // DB의 재고 < 요청 들어온 수량일 때
            if(book.getStock()-payCnt < 0){
                throw new Exception();
            }

            book.setStock(book.getStock()-payCnt);
            bookRepository.updateStockByIsbn(book.getStock(), paymentBook.getIsbn());

            log.info(" stock update >>> " + book.getStock());

        }

        // paymentBook setting 해줘야함
        paymentBook.setRealprice(paymentBook.getOrderTotalPrice()-paymentBook.getUsemileage());
        paymentBook.setBookcount(paymentBook.getTotalBookCnt());

        log.info(" payment check >>>>>>>>>>>>>>>>> " + paymentBook);

        Order order = paymentBook.toOrderEntity();

        // DB에 주문 등록
        result = orderRepository.save(order) != null ? 1 : 0;

        if(result==1){

            for(int i=0; i<bookInfoDTO.size(); i++) {

                // DB에 주문한 아이템 등록
                result = orderItemRepository.save(
                        OrderItem.builder()
                                .orderindex(order.getOrderindex())
                                .isbn(bookInfoDTO.get(i).getIsbn())
                                .bookcount(bookInfoDTO.get(i).getBookcount())
                                .build()
                ) != null ? 1 : 0;

                // 결제된 상품 장바구니에서 삭제
                cartRepository.deleteByUserindexAndIsbn(userindex,
                                                        Book.builder()
                                                                .isbn(bookInfoDTO.get(i).getIsbn())
                                                                .build());

                log.info(" item insert >>> " + result);
            }

        }

        log.info(" result >>> " + result);

        // 사용한 마일리지 insert
        if(paymentBook.getUsemileage() > 0) {
            Mileage useMil = Mileage.builder().userindex(userindex).usemileage(paymentBook.getUsemileage()).build();
            mileageRepository.save(useMil);

            log.info(" used mileage >>> " + useMil);
        }

        // 적립 마일리지 insert
        if(paymentBook.getTotalSaveMileage() > 0) {
            Mileage saveMil = Mileage.builder().userindex(userindex).mileage(paymentBook.getTotalSaveMileage()).build();
            mileageRepository.save(saveMil);

            log.info(" save mileage >>> " + saveMil);
        }

        if(paymentBook.getAddrSelect() == 9){
            AddrBook registAdd = AddrBook.builder()
                    .userindex(userindex)
                    .recipient(paymentBook.getRecipient())
                    .phone(paymentBook.getPhone())
                    .postnum(paymentBook.getPostnum())
                    .address1(paymentBook.getAddress1())
                    .address2(paymentBook.getAddress2())
                    .addrname(paymentBook.getAddrname())
                    .priority(0)
                    .build();

            addrBookRepository.save(registAdd);
        }

        return result;

    }

}
