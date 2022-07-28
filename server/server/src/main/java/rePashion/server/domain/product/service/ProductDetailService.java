package rePashion.server.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.dto.ProductDetailDto;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductDetailService {

    public ProductDetailDto of(Long id){

        Product product = getProduct();
        ProductDetailDto.SellerInfo sellerInfo = getSellerInfo();
        ProductDetailDto.Basic basic = getBasic(product);
        ProductDetailDto.SellerNotice sellerNotice = getSellerNotice(product);
        ProductDetailDto.Measure measure = getMeasure(product);

        return ProductDetailDto.builder()
                .sellerInfo(sellerInfo)
                .basic(basic)
                .sellerNotice(sellerNotice)
                .measure(measure)
                .opinion(product.getOpinion())
                .price(product.getPrice())
                .isIncludeDelivery(product.isIncludeDelivery())
                .updatedAt(product.getUpdatedAt())
                .like(getLikes())
                .view(product.getView())
                .build();
    }

    private int getLikes() {
        int likes = 17;
        return likes;
    }

    private ProductDetailDto.Measure getMeasure(Product product) {
        return new ProductDetailDto.Measure(product.getTotalLength(), product.getShoulderWidth(), product.getChestSection(), product.getSleeveLength());
    }

    private ProductDetailDto.Basic getBasic(Product product) {
        String productInfo = product.getGender() + "용/" + product.getSize();
        String styleInfo = product.getMaterial() + "/" + product.getColor() + "/" + product.getTag();
        return new ProductDetailDto.Basic(product.getTitle(), product.getCategory(), product.getBrand(), productInfo, styleInfo);
    }

    private ProductDetailDto.SellerNotice getSellerNotice(Product product) {
        return new ProductDetailDto.SellerNotice(product.getCondition(),
                product.getPollution(), String.valueOf(product.getHeight()),
                product.getLength(), product.getBodyShape(),
                product.getFit(), product.getPurchaseTime(),
                product.getPurchasePlace());
    }

    private ProductDetailDto.SellerInfo getSellerInfo() {
        ArrayList<String> images = new ArrayList<>();
        String profileImage = "https://webserver0712.s3.ap-northeast-2.amazonaws.com/profile/%EA%B8%B0%EB%B3%B8%ED%94%84%EB%A1%9C%ED%95%84.png";
        images.add("https://webserver0712.s3.ap-northeast-2.amazonaws.com/product/KakaoTalk_20220726_150206046_01.jpg");
        images.add("https://webserver0712.s3.ap-northeast-2.amazonaws.com/product/KakaoTalk_20220726_150206046.jpg");
        return new ProductDetailDto.SellerInfo(profileImage, "test01", images);
    }

    private Product getProduct() {
        return Product.MockProduct();
    }
}
