package com.studyForger.Study_Forger.Helper;

import com.studyForger.Study_Forger.Dto.PageableRespond;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class helper{

    public static <U,V>PageableRespond<V> getPageableResponse(Page<U> page, Class<V> classType) {
        List<U> entities = page.getContent();
        List<V> userDto = entities.stream().map(object-> new ModelMapper().map(object,classType)).collect(Collectors.toList());


        // Create a pageable response object
        PageableRespond<V> respond=new PageableRespond<>();
        respond.setContent(userDto);
        respond.setPageNumber(page.getNumber());
        respond.setPageSize(page.getSize());
        respond.setTotalElements(page.getTotalElements());
        respond.setTotalPages(page.getTotalPages());
        respond.setLastPage(page.isLast());

        return respond;
    }

}