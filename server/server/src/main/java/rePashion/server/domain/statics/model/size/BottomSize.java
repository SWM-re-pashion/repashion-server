package rePashion.server.domain.statics.model.size;

import lombok.Getter;

import java.util.HashMap;

@Getter
public enum BottomSize {

    B_22(22),
    B_23(23),
    B_24(24),
    B_25(25),
    B_26(26),
    B_27(27),
    B_28(28),
    B_29(29),
    B_30(30),
    B_31(31),
    B_32(32),
    B_33(33),
    B_34(34),
    B_35(35),
    B_36(36),
    B_37(37)
    ;

    private final Integer size;

    BottomSize(Integer size) {
        this.size = size;
    }

    public HashMap<Integer, String> getAllSizesByMap(){
        HashMap<Integer, String> sizes = new HashMap<>();
        for(BottomSize size:BottomSize.values())
            sizes.put(size.getSize(), String.valueOf(size));
        return sizes;
    }
}
