package rePashion.server.domain.statics.fit;

import lombok.Getter;

import java.util.HashMap;

@Getter
public enum TopFit {

    all("전체", "all"),
    tight("타이트", "tight"),
    normal("노멀", "normal"),
    lose("루즈", "lose"),
    oversize("오버사이즈", "oversize"),
    ;
    private String name;
    private String code;

    TopFit(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public HashMap<String, String> getALlNamesByMap(){
        HashMap<String, String> fits = new HashMap<>();
        for(TopFit fit : TopFit.values())
            fits.put(fit.getCode(), fit.getName());
        return fits;
    }
}
