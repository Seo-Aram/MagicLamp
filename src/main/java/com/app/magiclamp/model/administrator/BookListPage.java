package com.app.magiclamp.model.administrator;

import com.app.magiclamp.entity.Book;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookListPage {

    private int conuntperpage;  // 페이지 당 출력할 게시물의 수
    private int pagenum;    // 현재 페이지 번호
    private List<Book> list;    // 게시물의 리스트
    private int totalcount;     // 전체 게시물의 개수
    private int startnum;       // 페이징의 시작 번호
    private int endnum;     // 페이징의 끝 번호
    private boolean prev;   // 이전 버튼
    private boolean next;   // 다음 버튼

    public BookListPage(int conuntperpage, int pagenum, List<Book> list, int totalcount) {
        this.conuntperpage = conuntperpage;
        this.pagenum = pagenum;
        this.list = list;
        this.totalcount = totalcount;
        calPageInfo();
    }

    private void calPageInfo() {

        this.endnum = (int) (Math.ceil((this.pagenum * 1.0) / 10) *10);
        this.startnum = this.endnum-9;

        // 전체 페이지의 끝 번호
        int realendnum = (int) Math.ceil((this.totalcount * 1.0) / conuntperpage);

        this.endnum = realendnum < this.endnum ? realendnum : this.endnum;

        this.prev = this.startnum > 1;
        this.next = this.endnum < realendnum;
    }

}
