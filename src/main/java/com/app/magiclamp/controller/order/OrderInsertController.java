package com.app.magiclamp.controller.order;

import com.app.magiclamp.model.AuthUserDTO;
import com.app.magiclamp.model.order.RequestPaymentBook;
import com.app.magiclamp.service.order.OrderInsertService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/order")
public class OrderInsertController{

    @Autowired
    private OrderInsertService orderInsertService;

    @PostMapping("/payment")
    public ResponseEntity<Integer> insert(@AuthenticationPrincipal AuthUserDTO userDTO,
                                          @RequestBody RequestPaymentBook paymentBook){

        log.info(" post order ... ");
        log.info(" user >>> " + userDTO.getUserindex());
        log.info(" >>>>> insert 전 order >>>>>> " + paymentBook);

        if(userDTO==null || paymentBook==null){

            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.EXPECTATION_FAILED);
        }

        log.info(" >>>>> insert 후 order >>>>>> " + paymentBook);

        int result = 0;

        try {
            result = orderInsertService.Order(paymentBook, userDTO.getUserindex());
        } catch (Exception e){
            log.error(e.getMessage());
            log.error(e.getStackTrace());
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
        }

        log.info("result  >>> " + result);

        return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);

    }

}
