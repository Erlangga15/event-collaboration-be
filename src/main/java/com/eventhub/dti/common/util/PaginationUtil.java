package com.eventhub.dti.common.util;

import com.eventhub.dti.common.response.PaginatedResponse;
import org.springframework.data.domain.Page;

public class PaginationUtil {
  public static <T> PaginatedResponse<T> toPaginatedResponse(Page<T> page) {
    PaginatedResponse<T> response = new PaginatedResponse<>();
    response.setContent(page.getContent());
    response.setPageNumber(page.getNumber());
    response.setPageSize(page.getSize());
    response.setTotalElements(page.getTotalElements());
    response.setTotalPages(page.getTotalPages());
    return response;
  }
}
