package com.hungry.consultorang.common.parser;

public enum ParserType {
    POS_POWER("CT001"), COMPANY_ONE("CT002");
    private final String value;
    ParserType(String value){this.value=value;}
    public String getValue(){return value;}
    public static ParserType getParserType(String type){
        for(ParserType pt : ParserType.values()){
            if(pt.value.equals(type))
                return pt;
        }
        return null;
    }
}
