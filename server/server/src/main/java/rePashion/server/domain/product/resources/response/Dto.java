package rePashion.server.domain.product.resources.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rePashion.server.domain.product.dto.ProductPreviewDto;

import java.util.List;

public class Dto {

    @Data
    public static class Shop{
        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class Pagination{
            private Boolean isEndOfPage;
            private Long totalItemCount;
            private int totalPageCount;

            public Pagination(Boolean isEndOfPage, Long totalItemCount, int totalPageCount) {
                this.isEndOfPage = isEndOfPage;
                this.totalItemCount = totalItemCount;
                this.totalPageCount = totalPageCount;
            }
        }

        Object items;
        Pagination pagination;

        public Shop(Object items, Pagination pagination) {
            this.items = items;
            this.pagination = pagination;
        }
    }
}
