package rePashion.server.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rePashion.server.domain.product.exception.ProductNotExistedException;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.dto.ProductDetailDto;
import rePashion.server.domain.product.model.embedded.BasicInfo;
import rePashion.server.domain.product.model.embedded.SellerNote;
import rePashion.server.domain.product.repository.ProductRepository;
import rePashion.server.domain.statics.model.bodyshape.BodyShape;
import rePashion.server.domain.statics.category.exception.CategoryNotExisted;
import rePashion.server.domain.statics.category.repository.GenderCategoryRepository;
import rePashion.server.domain.statics.category.repository.ParentCategoryRepository;
import rePashion.server.domain.statics.category.repository.SubCategoryRepository;
import rePashion.server.domain.statics.exception.DetailTypeError;
import rePashion.server.domain.statics.exception.StaticVariableNotExisted;
import rePashion.server.domain.statics.model.fit.BottomFit;
import rePashion.server.domain.statics.model.fit.TopFit;
import rePashion.server.domain.statics.model.length.BottomLength;
import rePashion.server.domain.statics.model.length.TopLength;
import rePashion.server.domain.statics.model.style.Style;
import rePashion.server.global.error.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class ProductDetailService {

    private final ProductRepository productRepository;
    private final ParentCategoryRepository parentCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final GenderCategoryRepository genderCategoryRepository;
    private Product product;
    private Type type;

    @Value("${secret.profile.base_url}")
    private String BASE_URL;

    private static enum Type{
        bottom, top
    }

    public ProductDetailDto get(Long id){
        product = getProduct(id);
        setType();
        return ProductDetailDto.builder()
                .isMe(getIsMe())
                .status(getStatus())
                .sellerInfo(getSellerInfo())
                .basic(getBasic())
                .sellerNotice(getSellerNotice())
                .measure(product.getAdvanceInfo().getMeasure().getMap())
                .opinion(product.getAdvanceInfo().getSellerNote().getOpinion())
                .price(product.getBasicInfo().getPrice())
                .isIncludeDelivery(product.getBasicInfo().isIncludeDelivery())
                .updatedAt(product.getModifiedDate())
                .like(getLike())
                .view(getView())
                .build();
    }


    private Boolean getIsMe() {
        return null;
    }

    private String getStatus(){
        return null;
    }

    private ProductDetailDto.SellerInfo getSellerInfo() {
        return new ProductDetailDto.SellerInfo(getUserProfileImage(),getCurrentUser(), product.toStringArray());
    }

    private String getCurrentUser(){
        return null;
    }
    private String getUserProfileImage(){
        return BASE_URL;
    }

    private ProductDetailDto.Basic getBasic() {
        BasicInfo basicInfo = product.getBasicInfo();
        String[] splitCategory = basicInfo.getCategory().split("/");
        return new ProductDetailDto.Basic(
                basicInfo.getTitle(),
                getNameOfParentCategory(splitCategory[1]) + "/" + getNameOfSubCategory(splitCategory[2]),
                basicInfo.getBrand(),
                getNameOfGenderCategory(splitCategory[0]) + "/" + basicInfo.getSize(),
                product.getAdvanceInfo().getSellerNote().getMaterial()
                        + "/" + product.getAdvanceInfo().getSellerNote().getColor()
                        + "/" + getNameOfTag(product.getAdvanceInfo().getSellerNote().getTag())
        );
    }

    private String getNameOfGenderCategory(String code){
        return genderCategoryRepository.findGenderCategoryByCode(code).orElseThrow(CategoryNotExisted::new);
    }

    private String getNameOfTag(String code){
        try{
            return Style.valueOf(code).getName();
        }catch (IllegalArgumentException e){
            throw new StaticVariableNotExisted(code, ErrorCode.STATIC_VARIABLE_NOT_EXISTED);
        }
    }
    private String getNameOfParentCategory(String code){
        return parentCategoryRepository.findParentCategoryByCode(code).orElseThrow(CategoryNotExisted::new);
    }

    private String getNameOfSubCategory(String code){
        return subCategoryRepository.findSubCategoryByCode(code).orElseThrow(CategoryNotExisted::new);
    }
    private ProductDetailDto.SellerNotice getSellerNotice() {
        SellerNote sellerNote = product.getAdvanceInfo().getSellerNote();
        return new ProductDetailDto.SellerNotice(
                sellerNote.getConditions(),
                sellerNote.getPollution(),
                String.valueOf(sellerNote.getHeight()),
                getNameOfLength(sellerNote.getLength()),
                getNameOfFit(sellerNote.getFit()),
                getNameOfBodyShape(sellerNote.getBodyShape()),
                sellerNote.getPurchaseTime(),
                sellerNote.getPurchasePlace()
        );
    }

    private String getNameOfFit(String code){
        String name = "";
        try{
            switch(this.type){
                case bottom :
                    name = BottomFit.valueOf(code).getName();
                    break;
                case top :
                    name = TopFit.valueOf(code).getName();
                    break;
            }
        }catch (IllegalArgumentException e){
            throw new StaticVariableNotExisted(code, ErrorCode.STATIC_VARIABLE_NOT_EXISTED);
        }
        return name;
    }

    private String getNameOfBodyShape(String code){
        try{
            return BodyShape.valueOf(code).getName();
        }catch (IllegalArgumentException e){
            throw new StaticVariableNotExisted(code ,ErrorCode.STATIC_VARIABLE_NOT_EXISTED);
        }
    }
    private String getNameOfLength(String code){
        String name = "";
        try{
            switch(this.type){
                case bottom :
                    name = BottomLength.valueOf(code).getName();
                case top :
                    name = TopLength.valueOf(code).getName();
            }
        }catch (IllegalArgumentException e){
            throw new StaticVariableNotExisted(code, ErrorCode.STATIC_VARIABLE_NOT_EXISTED);
        }
        return name;
    }

    private int getLike() {
        return 0;
    }

    private int getView(){
        return 0;
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(ProductNotExistedException::new);
    }

    private void setType(){
        try{
            this.type = Type.valueOf(product.getBasicInfo().getCategory().split("/")[1]);
        }catch(IllegalArgumentException e){
            throw new DetailTypeError();
        }
    }
}
