package com.ssj.hulijie.pro.db.helper;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TemplateConfig {
	public static Map<String,Orm> mappings = new HashMap<>();
	
	public static Map<String,String> methodMappings = new HashMap<>();
	
	static{
		methodMappings.put( "java.lang.Integer","getInt");
		methodMappings.put("java.lang.String","getString");
		methodMappings.put("java.lang.Float","getFloat");
		methodMappings.put("java.lang.Double","getDouble");
		methodMappings.put("java.lang.Long","getLong");
		methodMappings.put("java.lang.Short","getShort");
	}
		
	public static Orm parse(InputStream is) throws Exception {  
        Orm orm = null;         
        Item item = null;
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "UTF-8");
        int eventType = parser.getEventType();  
        while (eventType != XmlPullParser.END_DOCUMENT) {  
            switch (eventType) {  
            case XmlPullParser.START_DOCUMENT:  
                orm = new Orm();  
                break;  
            case XmlPullParser.START_TAG:  
                if (parser.getName().equals("orm")) {  
                	orm.setTableName(parser.getAttributeValue(null, "tablename"));
                	orm.setBeanName(parser.getAttributeValue(null, "beanName"));
                	orm.setDaoName(parser.getAttributeValue(null, "daoName"));
                } else if (parser.getName().equals("key")) {  
                    Key key = new Key();
                    key.setColumn(parser.getAttributeValue(null, "column"));
                    key.setProperty(parser.getAttributeValue(null, "property"));
                    key.setType(parser.getAttributeValue(null, "type"));
                    key.setIdentity(Boolean.parseBoolean(parser.getAttributeValue(null, "identity")));
                    orm.setKey(key);
                } else if (parser.getName().equals("item")) {  
                    item = new Item();
                    item.setColumn(parser.getAttributeValue(null, "column"));
                    item.setProperty(parser.getAttributeValue(null, "property"));
                    item.setType(parser.getAttributeValue(null, "type"));
                    orm.getItems().add(item);
                }
                break;  
            case XmlPullParser.END_TAG:  
                if (parser.getName().equals("item")) {  
                    item = null;
                }  
                break;  
            }  
            eventType = parser.next();  
        }  
        return orm;  
    }  
}
