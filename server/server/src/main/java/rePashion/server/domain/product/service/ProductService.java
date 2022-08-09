package rePashion.server.domain.product.service;

import rePashion.server.domain.product.dto.ProductCreateDto;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.model.ProductAdvanceInfo;
import rePashion.server.domain.product.model.ProductImage;
import rePashion.server.domain.product.model.embedded.BasicInfo;
import rePashion.server.domain.product.model.embedded.Measure;
import rePashion.server.domain.product.model.embedded.SellerNote;

public class ProductService {

    public Product save(ProductCreateDto dto){

        BasicInfo basicInfo = BasicInfo.builder()
                .title(dto.getBasicInfo().getTitle())
                .contact(dto.getContact())
                .category(dto.getBasicInfo().getCategory())
                .brand(dto.getBasicInfo().getBrand())
                .thumbnailImage(dto.getImgList().get(0))
                .price(dto.getPrice())
                .isIncludeDelivery(dto.getIsIncludeDelivery())
                .size(dto.getSize())
                .views(0)
                .build();

        SellerNote sellerNote = SellerNote.builder()
                .purchasePlace(dto.getAdditionalInfo().getPurchasePlace())
                .purchaseTime(dto.getAdditionalInfo().getPurchaseTime())
                .pollution(dto.getSellerNote().getPollution())
                .height(dto.getSellerNote().getHeight())
                .condition(dto.getSellerNote().getCondition())
                .bodyShape(dto.getSellerNote().getBodyShape())
                .length(dto.getSellerNote().getLength())
                .fit(dto.getSellerNote().getFit())
                .tag(dto.getStyle().getTag())
                .color(dto.getStyle().getColor())
                .material(dto.getStyle().getMaterial())
                .opinion(dto.getOpinion())
                .build();


        Measure measure = Measure.builder()
                .totalLength(dto.getMeasure().getLength())
                .shoulderWidth(dto.getMeasure().getShoulderWidth())
                .waistSection(dto.getMeasure().getWaistSection())
                .chestSection(dto.getMeasure().getChestSection())
                .thighSection(dto.getMeasure().getThighSection())
                .bottomSection(dto.getMeasure().getBottomSection())
                .rise(dto.getMeasure().getRise())
                .sleeveLength(dto.getMeasure().getSleeveLength())
                .build();

        ProductAdvanceInfo advanceInfo = ProductAdvanceInfo.builder()
                .sellerNote(sellerNote)
                .measure(measure)
                .build();

        Product product = Product.builder()
                .basicInfo(basicInfo)
                .advanceInfo(advanceInfo)
                .build();

        advanceInfo.setProduct(product);

        for(String image : dto.getImgList()){
            ProductImage productImage = ProductImage.builder()
                    .product(product)
                    .imagePath(image)
                    .build();
            product.setImages(productImage);
        }

        return product;
    }

    public Long update(){return null;}

    public Long delete(){return null;}

    public ProductCreateDto get(){return null;}
}
