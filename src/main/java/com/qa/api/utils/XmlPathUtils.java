package com.qa.api.utils;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class XmlPathUtils {


    private static String getXmlPath(Response response) {
        return response.getBody().asString();
    }

    public static <T> T read(Response response,String xmlPathExpression) {

        ReadContext ctx= JsonPath.parse(getXmlPath(response));
        return ctx.read(xmlPathExpression);

    }

    public static <T> List<T> readList(Response response, String xmlPathExpression) {

        ReadContext ctx=JsonPath.parse(getXmlPath(response));
        return ctx.read(xmlPathExpression);

    }

    public static <T> List<Map<String, T>> readListOfMaps(Response response, String xmlPathExpression) {

        ReadContext ctx=JsonPath.parse(getXmlPath(response));
        return ctx.read(xmlPathExpression);

    }
}
