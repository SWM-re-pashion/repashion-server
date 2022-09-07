package rePashion.server.domain.statics.model.fit;

import lombok.Getter;

import java.util.HashMap;

@Getter
public enum BottomFit {

    all("전체", "all"),
    skinny("스키니","skinny"),
    normal("노멀", "normal"),
    wide("와이드", "wide"),
    lose("루즈", "lose"),
    ;
    private String name;
    private String code;

    BottomFit(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public HashMap<String, String> getALlNamesByMap(){
        HashMap<String, String> fits = new HashMap<>();
        for(BottomFit fit : BottomFit.values())
            fits.put(fit.getCode(), fit.getName());
        return fits;
    }
}
