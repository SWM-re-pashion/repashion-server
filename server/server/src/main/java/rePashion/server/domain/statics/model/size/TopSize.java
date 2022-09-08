package rePashion.server.domain.statics.model.size;

import lombok.Getter;

import java.util.HashMap;

@Getter
public enum TopSize {

    XS("XS"),
    S("S"),
    M("M"),
    L("L"),
    XL("XL"),
    XXL("2XL"),
    XXXL("3XL")
    ;

    private final String size;

    TopSize(String size) {
        this.size = size;
    }

    public HashMap<String, String> getAllSizesByMap(){
        HashMap<String, String> sizes = new HashMap<>();
        for(TopSize size : TopSize.values())
            sizes.put(size.getSize(), String.valueOf(size));
        return sizes;
    }
}
