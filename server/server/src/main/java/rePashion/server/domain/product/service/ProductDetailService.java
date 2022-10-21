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
import rePashion.server.domain.statics.category.model.Category;
import rePashion.server.domain.statics.category.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;
    private Product product;
    private Type type;

    @Value("${secret.profile.base_url}")
    private String BASE_URL;

    private static enum Type{
        bottom, top
    }

    public ProductDetailDto get(Long id){
//        product = getProduct(id);
//        setType();
//        return null;
////                ProductDetailDto.builder()
////                .isMe(getIsMe())
////                .status(getStatus())
////                .sellerInfo(getSellerInfo())
////                .basic(getBasic())
////                .sellerNotice(getSellerNotice())
////                //.measure(product.getAdvanceInfo().getMeasure().getMap())
////                .opinion(product.getAdvanceInfo().getSellerNote().getOpinion())
////                .price(product.getBasicInfo().getPrice())
////                .isIncludeDelivery(product.getBasicInfo().isIncludeDelivery())
////                .updatedAt(product.getModifiedDate())
////                .like(getLike())
////                .view(getView())
////                .build();
//    }
//
//
//    private Boolean getIsMe() {
//        return null;
//    }
//
//    private String getStatus(){
//        return null;
//    }
//
//    private ProductDetailDto.SellerInfo getSellerInfo() {
//        return new ProductDetailDto.SellerInfo(getUserProfileImage(),getCurrentUser(), product.toStringArray());
//    }
//
//    private String getCurrentUser(){
//        return null;
//    }
//    private String getUserProfileImage(){
//        return BASE_URL;
//    }
//
//    private ProductDetailDto.Basic getBasic() {
//        BasicInfo basicInfo = product.getBasicInfo();
//        String[] categories = getCategories(basicInfo.getCategory());
//        return null;
////        return new ProductDetailDto.Basic(
////                basicInfo.getTitle(),
////                categories[2] + "/" + categories[1],
////                basicInfo.getBrand(),
////                categories[0] + "/" + basicInfo.getSize(),
////                product.getAdvanceInfo().getSellerNote().getMaterial()
////                        + "/" + product.getAdvanceInfo().getSellerNote().getColor()
////                        + "/" + getNameOfTag(product.getAdvanceInfo().getSellerNote().getTag())
////        );
//    }
//
//    private String[] getCategories(String categoryId){
//        Category category = categoryRepository.findCategoryByCategoryId(Long.valueOf(categoryId)).orElseThrow(CategoryNotExisted::new);
//        Category middleCategory = category.getParentCategory();
//        return new String[]{category.getName(), middleCategory.getName(), middleCategory.getParentCategory().getName()};
//    }
//
//
//
//    private String getNameOfTag(String code){
//        try{
//            return Style.valueOf(code).getName();
//        }catch (IllegalArgumentException e){
//            throw new StaticVariableNotExisted(code, ErrorCode.STATIC_VARIABLE_NOT_EXISTED);
//        }
//    }
//
//    private ProductDetailDto.SellerNotice getSellerNotice() {
//        SellerNote sellerNote = product.getAdvanceInfo().getSellerNote();
//        return new ProductDetailDto.SellerNotice(
//                sellerNote.getConditions(),
//                sellerNote.getPollution(),
//                String.valueOf(sellerNote.getHeight()),
//                getNameOfLength(sellerNote.getLength()),
//                getNameOfFit(sellerNote.getFit()),
//                getNameOfBodyShape(sellerNote.getBodyShape()),
//                sellerNote.getPurchaseTime(),
//                sellerNote.getPurchasePlace()
//        );
//    }
//
//    private String getNameOfFit(String code){
//        String name = "";
//        try{
//            switch(this.type){
//                case bottom :
//                    name = BottomFit.valueOf(code).getName();
//                    break;
//                case top :
//                    name = TopFit.valueOf(code).getName();
//                    break;
//            }
//        }catch (IllegalArgumentException e){
//            throw new StaticVariableNotExisted(code, ErrorCode.STATIC_VARIABLE_NOT_EXISTED);
//        }
//        return name;
//    }
//
//    private String getNameOfBodyShape(String code){
//        try{
//            return BodyShape.valueOf(code).getName();
//        }catch (IllegalArgumentException e){
//            throw new StaticVariableNotExisted(code ,ErrorCode.STATIC_VARIABLE_NOT_EXISTED);
//        }
//    }
//    private String getNameOfLength(String code){
//        String name = "";
//        try{
//            switch(this.type){
//                case bottom :
//                    name = BottomLength.valueOf(code).getName();
//                case top :
//                    name = TopLength.valueOf(code).getName();
//            }
//        }catch (IllegalArgumentException e){
//            throw new StaticVariableNotExisted(code, ErrorCode.STATIC_VARIABLE_NOT_EXISTED);
//        }
//        return name;
//    }
//
//    private int getLike() {
//        return 0;
//    }
//
//    private int getView(){
//        return 0;
//    }
//
//    private Product getProduct(Long id) {
//        return productRepository.findById(id).orElseThrow(ProductNotExistedException::new);
//    }
//
//    private void setType(){
//        String category = product.getBasicInfo().getCategory();
//        switch(category.charAt(0)) {
//            case '1':
//                this.type = Type.top;
//            case '2':
//                this.type = Type.bottom;
//            default:
//                this.type = Type.top;
//        }
        return null;
    }
}
