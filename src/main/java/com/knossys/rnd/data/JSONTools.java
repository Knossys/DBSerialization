package com.knossys.rnd.data;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue.ValueType;

public class JSONTools {
	/**
	 * 
	 */
	public JsonObject parseJSON(String aMessage) {
		JsonReader jsonReader = Json.createReader(new StringReader(aMessage));
		JsonObject jsonObject = jsonReader.readObject();
		jsonReader.close();
		return (jsonObject);
	}

	/**
	 * 
	 */
	public JsonArray parseJSONArray(String aMessage) {
		JsonReader jsonReader = Json.createReader(new StringReader(aMessage));
		JsonArray jArray = jsonReader.readArray();
		jsonReader.close();
		return (jArray);
	}
	
	/**
	 * 
	 */
	public static String displayType (JsonObject anObject) {
		ValueType type = anObject.getValueType();
		
    if (ValueType.STRING == type)
      return ("String");
    else if (ValueType.ARRAY == type)
    	 return ("Array");
    else if (ValueType.OBJECT == type)
    	 return ("Object");
    else if (ValueType.NULL == type)
    	 return ("Null");
    else if (ValueType.NUMBER == type) {
    	 return ("Number");
    } else if (ValueType.FALSE == type)
    	 return ("Boolean (true)");
    else if (ValueType.TRUE == type)
    	 return ("Boolean (false");

    return ("Uknown value type");
	}
}
