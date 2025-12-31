package com.bronx.crm.common.dto;

import com.bronx.crm.application.division.dto.DivisionResponse;

//@Param T is Data Response
//@Param R is Data Request
//@Param E is Entity
public interface BaseService<R,T> {
    T create(R dto);
    T get(Long id);
    T delete(Long id);
    T update(Long id,R dto);

    default PageResponse<T> getAll(int page, int size, String orderBy, String sortBy, String query) {
        return null;
    }

    default PageResponse<T> getAllWithCompany(PageRequest pageRequest, Long companyId,String query) {
        return null;
    }
}
