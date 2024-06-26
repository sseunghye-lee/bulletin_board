package com.seung.pilot.commons.config;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class SimplePageRequest extends PageRequest {

    public SimplePageRequest() {
        this(0, 5);
    }

    public SimplePageRequest(int page, int size) {
        this(page, size, Sort.unsorted());
    }

    public SimplePageRequest(int page, int size, Sort sort) {
        super(page, size, sort);
    }
}
