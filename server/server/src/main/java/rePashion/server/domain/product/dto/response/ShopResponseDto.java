package rePashion.server.domain.product.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rePashion.server.domain.product.dto.ProductPreviewDto;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopResponseDto {
    public static class Pagination{
        private Boolean isEndOfPage;
        private Integer totalItemCount;
        private Integer totalPageCount;

        public Pagination(Boolean isEndOfPage, Integer totalItemCount, Integer totalPageCount) {
            this.isEndOfPage = isEndOfPage;
            this.totalItemCount = totalItemCount;
            this.totalPageCount = totalPageCount;
        }
    }

    List<ProductPreviewDto> items;
    Pagination pagination;

    public ShopResponseDto(List<ProductPreviewDto> items, Pagination pagination) {
        this.items = items;
        this.pagination = pagination;
    }
}
