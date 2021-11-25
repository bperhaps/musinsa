package com.musinsa.minsungson.exception;

public class ParentNotFoundException extends RuntimeException {
    private static final String MESSAGE = "부모 카테고리를 찾을 수 없습니다.";

    public ParentNotFoundException() {
        super(MESSAGE);
    }
}
