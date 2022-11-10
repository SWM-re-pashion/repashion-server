package rePashion.server.domain.product.resources.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import rePashion.server.domain.product.dto.ProductDto;
import rePashion.server.domain.product.dto.ProductFlatDto;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.model.ProductAdvanceInfo;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mappings({
            @Mapping(source = ".", target = "."),
            @Mapping(source = "basicInfo", target = "."),
            @Mapping(source = "additionalInfo", target = "."),
            @Mapping(source = "sellerNote", target = "."),
            @Mapping(source = "style", target = ".")
    })
    ProductFlatDto productDtoToFlatDto(ProductDto body);

    @Mapping(source = ".", target = "basicInfo")
    @Mapping(source = "measureType", target = "basicInfo.type")
    Product flatDtoToProduct(ProductFlatDto productFlatDto);

    @Mappings({
            @Mapping(source = ".", target = "sellerNote")
    })
    ProductAdvanceInfo flatDtoToProductAdvanceIfo(ProductFlatDto productFlatDto);
}
