package rePashion.server.domain.product.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

public class ProductAdvanceInfo {
    private ArrayList<String> category;
    private ArrayList<String> gender;


    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Size{
        private ArrayList<String> top;
        private ArrayList<String> bottom;

        public Size(ArrayList<String> top, ArrayList<String> bottom) {
            this.top = top;
            this.bottom = bottom;
        }
    }
    private Size size;
    private ArrayList<String> condition;
    private ArrayList<String> pollution;
    private ArrayList<String> bodyShape;
    private ArrayList<String> length;
    public static class Fit{
        private ArrayList<String> top;
        private ArrayList<String> bottom;

        public Fit(ArrayList<String> top, ArrayList<String> bottom) {
            this.top = top;
            this.bottom = bottom;
        }
    }
    private Fit fit;
    private ArrayList<String> tag;
    public static class Colors{
        private String name;
        private String code;

        public Colors(String name, String code) {
            this.name = name;
            this.code = code;
        }
    }
    private ArrayList<Colors> colors;
    private ArrayList<String> materials;
    private int insertedHeight;
    private String insertedBodyShape;

    public void enterHeightAndBodyShapeInfo(int height, String bodyShape) {
        this.insertedHeight = height;
        this.insertedBodyShape = bodyShape;
    }


    @Builder
    public ProductAdvanceInfo(ArrayList<String> category, ArrayList<String> gender, Size size, ArrayList<String> condition, ArrayList<String> pollution, ArrayList<String> bodyShape, ArrayList<String> length, Fit fit, ArrayList<String> tag, ArrayList<Colors> colors, ArrayList<String> materials) {
        this.category = category;
        this.gender = gender;
        this.size = size;
        this.condition = condition;
        this.pollution = pollution;
        this.bodyShape = bodyShape;
        this.length = length;
        this.fit = fit;
        this.tag = tag;
        this.colors = colors;
        this.materials = materials;
    }
}
