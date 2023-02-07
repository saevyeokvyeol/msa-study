package com.msa.example.chapter05.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class ReservationController {
    @GetMapping("/hotels/{hotelId}/rooms/{roomNumber}/reservation")
    public List<Long> getReservationsByPaging(@PathVariable Long hotelId,
                                              @PathVariable String roomNumber,
                                              /*
                                              * @RequestParam 없이도 page, size, sort 등의 파라미터 값을 매핑한 Pageable pageable 파라미터 전달
                                              * */
                                              Pageable pageable) {
        // pageable.getPageNumber() == page 파라미터 값 리턴
        System.out.println("Page param : " + pageable.getPageNumber());

        // pageable.getPageSize() == size 파라미터 값 리턴
        System.out.println("Size param : " + pageable.getPageSize());

        /*
        * pageable.getSort() == sort 파라미터 값 리턴
        * 단, sort 파라미터는 하나 이상의 값을 포함할 수 있음
        * */
        pageable.getSort().stream().forEach(order -> {
            System.out.println("Sort param : " + order.getProperty() + " : " + order.getDirection());
        });

        return Collections.emptyList();
    }
}
