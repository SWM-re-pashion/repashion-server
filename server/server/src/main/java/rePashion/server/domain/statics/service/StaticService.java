package rePashion.server.domain.statics.service;

import org.springframework.stereotype.Service;
import rePashion.server.domain.statics.bodyshape.BodyShape;
import rePashion.server.domain.statics.color.BottomColor;
import rePashion.server.domain.statics.color.TopColor;
import rePashion.server.domain.statics.dto.DividedSingleStaticRequestDto;
import rePashion.server.domain.statics.dto.DividedStaticRequestDto;
import rePashion.server.domain.statics.dto.SingleStaticVarFormat;
import rePashion.server.domain.statics.dto.StaticVarFormat;
import rePashion.server.domain.statics.fit.BottomFit;
import rePashion.server.domain.statics.fit.TopFit;
import rePashion.server.domain.statics.gender.Gender;
import rePashion.server.domain.statics.length.BottomLength;
import rePashion.server.domain.statics.length.TopLength;
import rePashion.server.domain.statics.size.BottomSize;
import rePashion.server.domain.statics.size.TopSize;
import rePashion.server.domain.statics.style.Style;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class StaticService {

    public static enum Type{
        color, fit, gender, length, size, style, bodyShape
    }

    public Object setMethodBy(Type type){
        return switch (type) {
            case color -> getColors();
            case fit -> getFits();
            case gender -> getGenders();
            case length -> getLengths();
            case size -> getSizes();
            case style -> getStyles();
            case bodyShape -> getBodyShape();
        };
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
