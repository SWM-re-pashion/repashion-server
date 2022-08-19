package rePashion.server.domain.statics.fit;

import lombok.Getter;

import java.util.HashMap;

@Getter
public enum BottomFit {

    ALL("전체", "all"),
    SKINNY("스키니","skinny"),
    NORMAL("노멀", "normal"),
    WIDE("와이드", "wide"),
    LOSE("루즈", "lose"),
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
