package com.musinsa.minsungson.exception;

public class IllegalCategoryIndexException extends RuntimeException {

    private static final String MESSAGE = "잘못된 카테고리 인덱스 번호 입니다.";

    public IllegalCategoryIndexException() {
        super(MESSAGE);
    }
}
