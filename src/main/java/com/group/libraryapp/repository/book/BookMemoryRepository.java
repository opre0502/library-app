package com.group.libraryapp.repository.book;

import org.springframework.stereotype.Repository;

@Repository
public class BookMemoryRepository implements BookRepository{

    @Override
    public void saveBook(){
        System.out.printf("메모리");
    }
}
