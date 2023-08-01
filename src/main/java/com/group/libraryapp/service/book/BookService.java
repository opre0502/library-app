package com.group.libraryapp.service.book;
import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnResquest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;
    private final UserRepository userRepository;

    public BookService(
            BookRepository bookRepository,
            UserLoanHistoryRepository userLoanHistoryRepository,
            UserRepository userRepository
    ) {
        this.bookRepository = bookRepository;
        this.userLoanHistoryRepository = userLoanHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveBook(BookCreateRequest request) {
        System.out.println(request.getName());
        bookRepository.save(new Book(request.getName()));
    }

    @Transactional
    public void loanBook(BookLoanRequest request){
        // 책정보 가져오기
        Book book = bookRepository.findByName(request.getBookName())
                    .orElseThrow(IllegalArgumentException::new);

        // 대출 기록 정보를 확인 해서 대출 중인지 확인
        // 확인 했는데 대출 중이라면 예외 발생
        if(userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(), false)) {
            throw new IllegalArgumentException("진작 대출되어 있는 책입니다.");
        }

        // 유저 정보 가져오기
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);

        // 유저 정보와 책 정보를 기반으로 UserLoanHistoty를 저장
//        userLoanHistoryRepository.save(new UserLoanHistory(user.getId(), book.getName(), false));
        userLoanHistoryRepository.save(new UserLoanHistory(user, book.getName()));

    }

    @Transactional
    public void returnBook(BookReturnResquest request){
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);
        UserLoanHistory history = userLoanHistoryRepository.findByUserIdAndBookName(user.getId(), request.getBookName())
                .orElseThrow(IllegalArgumentException::new);
        history.doReturn();
//        userLoanHistoryRepository.save(history); //@Transactional 사용으로 생략가능.
    }


}
