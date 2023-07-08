package com.pearlyadana.rakhapuraapp.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.annotation.PostConstruct;

@Component
@ApplicationScope
public class PaginationUtil {

    @Value("${pagesize}")
    public String pageSizeProp;

    private int pageSize = 0;

    @PostConstruct
    private void init(){
        this.pageSize = Integer.parseInt(this.pageSizeProp);
    }

    public int getPageSize() {
        return pageSize;
    }

    public static int pageNumber(int aPageNumber){
        return aPageNumber - 1;
    }

}
