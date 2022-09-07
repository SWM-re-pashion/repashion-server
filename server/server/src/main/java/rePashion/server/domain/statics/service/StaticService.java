package rePashion.server.domain.statics.service;

import org.springframework.stereotype.Service;
import rePashion.server.domain.statics.model.bodyshape.BodyShape;
import rePashion.server.domain.statics.model.color.BottomColor;
import rePashion.server.domain.statics.model.color.TopColor;
import rePashion.server.domain.statics.dto.DividedStaticRequestDto;
import rePashion.server.domain.statics.dto.StaticVarFormat;
import rePashion.server.domain.statics.exception.StaticVariableNotExisted;
import rePashion.server.domain.statics.model.fit.BottomFit;
import rePashion.server.domain.statics.model.fit.TopFit;
import rePashion.server.domain.statics.model.gender.Gender;
import rePashion.server.domain.statics.model.length.BottomLength;
import rePashion.server.domain.statics.model.length.TopLength;
import rePashion.server.domain.statics.model.size.BottomSize;
import rePashion.server.domain.statics.model.size.TopSize;
import rePashion.server.domain.statics.model.style.Style;
import rePashion.server.global.error.exception.ErrorCode;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class StaticService {

    public static enum Type{
        color, fit, gender, length, size, style, bodyShape
    }

    public Object setMethodBy(Type type){
        switch (type){
            case color :
                return getColors();
            case fit :
                return getFits();
            case gender :
                return getGenders();
            case length :
                return getLengths();
            case size :
                return getSizes();
            case style :
                return getStyles();
            case bodyShape :
                return getBodyShape();
        }
        throw new StaticVariableNotExisted(ErrorCode.STATIC_VARIABLE_NOT_EXISTED);
    }

    private ArrayList<StaticVarFormat> getBodyShape() {
        ArrayList<StaticVarFormat> bodyShapes = new ArrayList<>();
        Arrays.stream(BodyShape.values()).forEach((e) -> bodyShapes.add(new StaticVarFormat(e.getName(), e.getCode())));
        return bodyShapes;
    }

    private ArrayList<StaticVarFormat> getStyles() {
        ArrayList<StaticVarFormat> style = new ArrayList<>();
        Arrays.stream(Style.values()).forEach((e) -> style.add(new StaticVarFormat(e.getName(), e.getCode())));
        return style;
    }

    private DividedStaticRequestDto getSizes() {
        ArrayList<StaticVarFormat> top = new ArrayList<>();
        ArrayList<StaticVarFormat> bottom = new ArrayList<>();
        Arrays.stream(TopSize.values()).forEach((e) -> top.add(new StaticVarFormat(String.valueOf(e.getSize()), String.valueOf(e.getSize()))));
        Arrays.stream(BottomSize.values()).forEach((e) -> bottom.add(new StaticVarFormat(String.valueOf(e.getSize()), String.valueOf(e.getSize()))));
        return new DividedStaticRequestDto(top, bottom);
    }

    private DividedStaticRequestDto getLengths() {
        ArrayList<StaticVarFormat> top = new ArrayList<>();
        ArrayList<StaticVarFormat> bottom = new ArrayList<>();
        Arrays.stream(TopLength.values()).forEach((e) -> top.add(new StaticVarFormat(e.getName(), e.getCode())));
        Arrays.stream(BottomLength.values()).forEach((e) -> bottom.add(new StaticVarFormat(e.getName(), e.getCode())));
        return new DividedStaticRequestDto(top, bottom);
    }

    private ArrayList<StaticVarFormat> getGenders() {
        ArrayList<StaticVarFormat> gender = new ArrayList<>();
        Arrays.stream(Gender.values()).forEach((e) -> gender.add(new StaticVarFormat(e.getName(), e.getCode())));
        return gender;
    }

    private DividedStaticRequestDto getFits() {
        ArrayList<StaticVarFormat> top = new ArrayList<>();
        ArrayList<StaticVarFormat> bottom = new ArrayList<>();
        Arrays.stream(TopFit.values()).forEach((e) -> top.add(new StaticVarFormat(e.getName(), e.getCode())));
        Arrays.stream(BottomFit.values()).forEach((e) -> bottom.add(new StaticVarFormat(e.getName(), e.getCode())));
        return new DividedStaticRequestDto(top, bottom);
    }

    private DividedStaticRequestDto getColors() {
        ArrayList<StaticVarFormat> top = new ArrayList<>();
        ArrayList<StaticVarFormat> bottom = new ArrayList<>();
        Arrays.stream(TopColor.values()).forEach((e) -> top.add(new StaticVarFormat(e.getName(), e.getCode())));
        Arrays.stream(BottomColor.values()).forEach((e) -> bottom.add(new StaticVarFormat(e.getName(), e.getCode())));
        return new DividedStaticRequestDto(top, bottom);
    }
}
