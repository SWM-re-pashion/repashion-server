package rePashion.server.domain.statics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rePashion.server.domain.statics.repository.StaticsRepository;
import rePashion.server.domain.statics.model.Statics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class StaticsService implements CommandLineRunner {

    private final StaticsRepository staticsRepository;
    public static Map<String, String> lookups = new HashMap<>();
    private ArrayList<Statics> statics = new ArrayList<>();

    @Override
    public void run(String... args) throws Exception {

        // Style
        create("Style", "common", "트레디셔녈", "traditional");
        create("Style", "common", "매니시", "manish");
        create("Style", "common", "페미닌", "feminine");
        create("Style", "common", "에스닉", "ethnic");
        create("Style", "common", "컨템포러리", "contemporary");
        create("Style", "common", "내추럴", "natural");
        create("Style", "common", "젠더리스", "genderless");
        create("Style", "common", "서브컬쳐", "subculture");
        create("Style", "common", "캐주얼", "casual");
        create("Style", "common", "힙합", "hiphop");
        create("Style", "common", "펑크", "punk");
        create("Style", "common", "모던", "modern");
        create("Style", "common", "스트리트", "street");
        create("Style", "common", "키치/키덜트", "kitsch");
        create("Style", "common", "스포티", "sporty");
        create("Style", "common", "클래식", "classic");
        create("Style", "common", "레트로", "retro");
        create("Style", "common", "아방가르드", "avantgarde");
        create("Style", "common", "섹시", "sexy");
        create("Style", "common", "톰보이", "tomboy");
        create("Style", "common", "프레피", "preppy");

        // Color
        create("Color", "top", "Black", "#000");
        create("Color", "top", "White", "#fff");
        create("Color", "top", "Beige", "#e4d2c1");
        create("Color", "top", "Ivory", "#f4edda");
        create("Color", "top", "Silver", "#e3e1e1");
        create("Color", "top", "Gray", "#959595");
        create("Color", "top", "Red", "#ff5e5e");
        create("Color", "top", "Pink", "#ff61d7");
        create("Color", "top", "Orange", "#ff9635");
        create("Color", "top", "Yellow", "#ffd747");
        create("Color", "top", "Brown", "#967556");
        create("Color", "top", "Khaki", "#848974");
        create("Color", "top", "Green", "#7db768");
        create("Color", "top", "Light Green", "#aae396");
        create("Color", "top", "Mint", "#acfbdf");
        create("Color", "top", "Blue", "#4bb4ff");
        create("Color", "top", "Navy", "#38466c");
        create("Color", "top", "Purple", "#936dff");

        create("Color", "bottom", "Black", "#000");
        create("Color", "bottom", "White", "#fff");
        create("Color", "bottom", "Beige", "#e4d2c1");
        create("Color", "bottom", "Ivory", "#f4edda");
        create("Color", "bottom", "Silver", "#e3e1e1");
        create("Color", "bottom", "Gray", "#959595");
        create("Color", "bottom", "Red", "#ff5e5e");
        create("Color", "bottom", "Pink", "#ff61d7");
        create("Color", "bottom", "Orange", "#ff9635");
        create("Color", "bottom", "Yellow", "#ffd747");
        create("Color", "bottom", "Brown", "#967556");
        create("Color", "bottom", "Khaki", "#848974");
        create("Color", "bottom", "Green", "#7db768");
        create("Color", "bottom", "Light Green", "#aae396");
        create("Color", "bottom", "Mint", "#acfbdf");
        create("Color", "bottom", "Blue", "#4bb4ff");
        create("Color", "bottom", "Navy", "#38466c");
        create("Color", "bottom", "Purple", "#936dff");

        // Fit
        create("Fit", "top", "타이트", "tight");
        create("Fit", "top", "노멀", "normal");
        create("Fit", "top", "루즈", "loose");
        create("Fit", "top", "오버사이즈", "oversize");

        create("Fit", "bottom", "스키니", "skinny");
        create("Fit", "bottom", "노멀", "normal");
        create("Fit", "bottom", "와이드", "wide");
        create("Fit", "bottom", "루즈", "loose");

        // Length
        create("Length", "top", "크롭","crop");
        create("Length", "top", "허리","waist");
        create("Length", "top", "골반","pelvis");
        create("Length", "top", "엉덩이","hip");
        create("Length", "top", "허벅지","thigh");
        create("Length", "top", "정강이","shin");
        create("Length", "top", "발목","ankle");

        create("Length", "bottom", "미니","mini");
        create("Length", "bottom", "허벅지","thigh");
        create("Length", "bottom", "무릎","knee");
        create("Length", "bottom", "정강이","shin");
        create("Length", "bottom", "발목","ankle");
        create("Length", "bottom", "발","foot");

        // Gender
        create("Gender", "common", "남성", "men");
        create("Gender", "common", "여성", "women");

        // BodyShape
        create("BodyShape", "common","마름", "thin");
        create("BodyShape", "common","보통", "normal");
        create("BodyShape", "common","통통", "chubby");
        create("BodyShape", "common","뚱뚱", "fat");

        // PollutionCondition
        create("PollutionCondition","common","새 상품","new");
        create("PollutionCondition","common","거의 없음","none");
        create("PollutionCondition","common","보통","normal");
        create("PollutionCondition","common","조금 있음","little");
        create("PollutionCondition","common","많이 있음","many");

        create("OrderDate","common","최신순", "latest");
        create("OrderDate","common","인기순", "like");
        create("OrderDate","common","조회순", "view");
        create("OrderDate","common","낮은가격순","low_price");
        create("OrderDate","common","높은가격순","high_price");

        create("Size","top","XS", "XS");
        create("Size","top","S", "S");
        create("Size","top","M", "M");
        create("Size","top","L","L");
        create("Size","top","XL","XL");
        create("Size","top","2XL","2XL");
        create("Size","top","3XL","3XL");

        create("Size","bottom","22","22");
        create("Size","bottom","23","23");
        create("Size","bottom","24","24");
        create("Size","bottom","25","25");
        create("Size","bottom","26","26");
        create("Size","bottom","27","27");
        create("Size","bottom","28","28");
        create("Size","bottom","29","29");
        create("Size","bottom","30","30");
        create("Size","bottom","31","31");
        create("Size","bottom","32","32");
        create("Size","bottom","33","33");
        create("Size","bottom","34","34");
        create("Size","bottom","35","35");
        create("Size","bottom","36","36");
        create("Size","bottom","37","37");

        statics.forEach(o->{
            lookups.put(o.getName(), o.getCode());
        });

        if(isEmpty()) staticsRepository.saveAll(statics);
    }

    private void create(String type, String classification, String name, String code){
        statics.add(new Statics(Statics.Type.valueOf(type),Statics.Classification.valueOf(classification), name, code));
    }

    public List<Statics> getTops(Statics.Type type){
        return staticsRepository.findStaticsByTypeAndClassificationOrderById(type, Statics.Classification.top );
    }

    public List<Statics> getCommons(Statics.Type type){
        return staticsRepository.findStaticsByTypeAndClassificationOrderById(type, Statics.Classification.common);
    }

    public List<Statics> getBottoms(Statics.Type type){
        return staticsRepository.findStaticsByTypeAndClassificationOrderById(type, Statics.Classification.bottom);
    }

    private boolean isEmpty(){
        return staticsRepository.findAll().isEmpty();
    }
}
