package com.msa.example.chapter05.controller;

import com.msa.example.chapter05.domain.FileDownloadException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
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

    /*
     * HttpMessageConverter 객체를 사용해 bytes[]로 클라이언트에 파일을 전달
     * */
    @GetMapping("/hotels/{hotelId}/rooms/{roomNumber}/reservations/{reservationId}")
    public ResponseEntity<byte[]> getInvoice(@PathVariable Long hotelId,
                                             @PathVariable String roomNumber,
                                             @PathVariable Long reservationId) {
        // 해당 프로젝트의 resources/pdf/hotel_invoice.pdf
        String filePath = "pdf/hotel_invoice.pdf";

        // 파일 경로 변수 filePath에서 InputStream을 가져옴
        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
            // inputStream 객체를 byte[] bytes으로 변환
            byte[] bytes = StreamUtils.copyToByteArray(inputStream);

            // 변환한 bytes와 200 OK 상태 코드로 ResponseEntity 객체를 생성해 리턴
            return new ResponseEntity<>(bytes, HttpStatus.OK);
        } catch (Throwable th) {
            th.printStackTrace();
            throw new FileDownloadException("file download error");
        }
    }

    /*
    * HttpServletResponse 객체를 사용해 클라이언트에 파일을 전달
    * */
    @GetMapping(value = "/hotels/{hotelId}/rooms/{roomNumber}/reservations/{reservationId}", produces = "application/pdf")
    public void downloadInvoice(@PathVariable Long hotelId,
                                @PathVariable String roomNumber,
                                @PathVariable Long reservationId,
                                // 런타임 과정에서 생성된 HttpServletResponse 객체 주입
                                HttpServletResponse response) {
        String filePath = "pdf/hotel_invoice.pdf";

        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream();
             OutputStream outputStream = response.getOutputStream()) {

            // HttpServletResponse 객체의 메소드를 사용해 HTTP 상태 코드와 헤더 설정
            // HttpServletResponse는 Content-type 헤더를 설정하는 setContentType 메소드 제공
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            response.setHeader("Content-Disposition", "filename=hotel_invoice.pdf");

            // StreamUtils.copy() 메소드를 이용해 inputStream에서 데이터를 읽고 outputStream으로 데이터를 쓺
            StreamUtils.copy(inputStream, outputStream);
        } catch (Throwable th) {
            th.printStackTrace();
            throw new FileDownloadException("file download error");
        }
    }
}
