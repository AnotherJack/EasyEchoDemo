package com.github.anotherjack.easyechodemo.easyEcho;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.github.anotherjack.easyechodemo.R;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;


public class EasyEcho {
    public static void echoMap(Map map, View parent, IdStrConverterForMap idStrConverter) {
        //遍历map
        Set<Map.Entry> entries = map.entrySet();
        for (Map.Entry entry : entries) {
            //获取到键（String）
            String key = entry.getKey().toString();
            //获取到值（Object）
            Object valueObj = entry.getValue();
            //如果值的类型是map，递归
            if (Map.class.isAssignableFrom(valueObj.getClass())) {
                echoMap((Map) valueObj, parent, idStrConverter);
                continue;
            }
            //如果值不是map，直接回显toString
            String value = valueObj.toString();

            //根据map的键获取到对应的id字符串
            String idStr = idStrConverter.convert(key, map);
            //根据id字符串，通过反射获取int型id
            int integerId = getIntegerId(idStr);

            //最后控件赋值，大功告成
            TextView tv = (TextView) parent.findViewById(integerId);
            if (tv != null) {
                tv.setText(value);
            }
        }
    }

    //不传converter，默认idStr和map的key相同
    public static void echoMap(Map map, View parent) {
        echoMap(map, parent, new IdStrConverterForMap() {
            @Override
            public String convert(String key, Map map) {
                return key;
            }
        });
    }

    public static void echoMap(Map map, Activity activity, IdStrConverterForMap idStrConverter) {
        View rootView = activity.getWindow().getDecorView();
        echoMap(map, rootView, idStrConverter);
    }

    //不传converter，默认idStr和map的key相同
    public static void echoMap(Map map, Activity activity) {
        echoMap(map, activity, new IdStrConverterForMap() {
            @Override
            public String convert(String key, Map map) {
                return key;
            }
        });
    }

    public static Map saveAsMap(Map defaultMap, View parent, IdStrConverterForMap idStrConverter) {
        //遍历map
        Set<Map.Entry> entries = defaultMap.entrySet();
        for (Map.Entry entry : entries) {
            //获取到键（String）
            String key = entry.getKey().toString();
            //获取到值（Object）
            Object valueObj = entry.getValue();
            //获取值的类型
            Class valueClass = valueObj.getClass();
            //如果值的类型是map，递归获取valueMap，并赋值给entry，记得要continue
            if (Map.class.isAssignableFrom(valueClass)) {
                Map valueMap = saveAsMap((Map) valueObj, parent, idStrConverter);
                if(valueMap!=null){
                    entry.setValue(valueMap);
                }
                continue;
            }

            //否则，走下面代码，把textview的值赋给entry

            //根据map的键获取到对应的id字符串
            String idStr = idStrConverter.convert(key, defaultMap);
            //根据id字符串，通过反射获取int型id
            int integerId = getIntegerId(idStr);
            //获取控件
            TextView tv = (TextView) parent.findViewById(integerId);
            //获取textview里的字符串
            String tvStr = "";
            if (tv != null) {
                tvStr = tv.getText().toString();
            }

            //最终要赋给entry的value Object
            Object finalValueObj = str2Obj(tvStr, valueClass);
            if(finalValueObj!=null){
                entry.setValue(finalValueObj);
            }

        }

        return defaultMap;
    }

    public static Map saveAsMap(Map defaultMap, View parent) {
        return saveAsMap(defaultMap, parent, new IdStrConverterForMap() {
            @Override
            public String convert(String key, Map map) {
                return key;
            }
        });
    }

    public static Map saveAsMap(Map defaultMap, Activity activity, IdStrConverterForMap idStrConverter) {
        View rootView = activity.getWindow().getDecorView();
        return saveAsMap(defaultMap, rootView, idStrConverter);
    }

    public static Map saveAsMap(Map defaultMap, Activity activity) {
        return saveAsMap(defaultMap, activity, new IdStrConverterForMap() {
            @Override
            public String convert(String key, Map map) {
                return key;
            }
        });
    }


    public static void echoBean(Object obj, View parent, IdStrConverterForBean idStrConverter) {
        //获取bean的运行时类
        Class clazz = obj.getClass();
        //遍历bean
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            //获取属性名称（String）
            String fieldName = field.getName();

            //获取值（Object）
            Object fieldValueObj = null;
            try {
                fieldValueObj = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            //如果值的类型不是基本类型，不是基本类型的封装类，也不是字符串类型，即是Bean，递归
            Class fieldValueClass = fieldValueObj.getClass();
            if (!fieldValueClass.isPrimitive() && !isWrapClass(fieldValueClass) && !String.class.isAssignableFrom(fieldValueClass)) {
                echoBean(fieldValueObj, parent, idStrConverter);
                continue;
            }

            //否则（是基本类型或字符串的话），直接回显toString
            String fieldValue = fieldValueObj.toString();

            //根据属性名称获取到对应的id字符串
            String idStr = idStrConverter.convert(fieldName, clazz);

            //根据id字符串，通过反射获取int型id
            int integerId = getIntegerId(idStr);

            //最后控件赋值，大功告成
            TextView tv = (TextView) parent.findViewById(integerId);
            if (tv != null) {
                tv.setText(fieldValue);
            }

        }
    }

    //不传converter，默认idStr和Bean的fieldName相同
    public static void echoBean(Object obj, View parent) {
        echoBean(obj, parent, new IdStrConverterForBean() {
            @Override
            public String convert(String fieldName, Class<?> clazz) {
                return fieldName;
            }
        });
    }

    public static void echoBean(Object obj, Activity activity, IdStrConverterForBean idStrConverter) {
        View rootView = activity.getWindow().getDecorView();
        echoBean(obj, rootView, idStrConverter);
    }

    //不传converter，默认idStr和Bean的fieldName相同
    public static void echoBean(Object obj, Activity activity) {
        echoBean(obj, activity, new IdStrConverterForBean() {
            @Override
            public String convert(String fieldName, Class<?> clazz) {
                return fieldName;
            }
        });
    }

    public static <T> T saveAsBean(Class<T> clazz, View parent, IdStrConverterForBean idStrConverter) {
        T result = null;
        try {
            result = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //遍历bean
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            //获取属性名称（String）
            String fieldName = field.getName();
            //获取field的类型
            Class<?> fieldClass = field.getType();
            //如果不是基本类型，不是基本类型的封装类，也不是String类型，则认为是Bean，递归，并赋值，记得continue
            if (!fieldClass.isPrimitive() && !isWrapClass(fieldClass) && !String.class.isAssignableFrom(fieldClass)) {
                Object fieldValueObj = saveAsBean(fieldClass, parent, idStrConverter);
                try {
                    field.set(result, fieldValueObj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                continue;
            }

            //否则，走下面代码，把textview的值赋给result的相应field

            //根据属性名称获取到对应的id字符串
            String idStr = idStrConverter.convert(fieldName, clazz);

            //根据id字符串，通过反射获取int型id
            int integerId = getIntegerId(idStr);

            //拿到控件，获取的text就是对应bean的值（value）
            TextView tv = (TextView) parent.findViewById(integerId);
            String tvStr = "";
            if (tv != null) {
                tvStr = tv.getText().toString();
            }

            Object fieldValueObj = str2Obj(tvStr, fieldClass);


            //给result相应属性赋值
            if (fieldValueObj != null) {
                try {
                    field.set(result, fieldValueObj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }

        return result;
    }

    public static <T> T saveAsBean(Class<T> clazz, View parent) {
        return saveAsBean(clazz, parent, new IdStrConverterForBean() {
            @Override
            public String convert(String fieldName, Class<?> clazz) {
                return fieldName;
            }
        });
    }

    public static <T> T saveAsBean(Class<T> clazz, Activity activity, IdStrConverterForBean idStrConverter) {
        View rootView = activity.getWindow().getDecorView();
        return saveAsBean(clazz, rootView, idStrConverter);
    }

    public static <T> T saveAsBean(Class<T> clazz, Activity activity) {
        return saveAsBean(clazz, activity, new IdStrConverterForBean() {
            @Override
            public String convert(String fieldName, Class<?> clazz) {
                return fieldName;
            }
        });
    }



    //通过反射获取int型的id
    private static int getIntegerId(String idStr) {
        int integerId = 0;
        try {
            Field idField = R.id.class.getDeclaredField(idStr);
            integerId = idField.getInt(R.id.class);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return integerId;
    }

    //把字符串转成相应的运行时类型（基本类型或字符串，不会转Bean的）
    private static Object str2Obj(String str, Class clazz) {
        Object resultObj = null;
        try {
            //如果是基本类型，转成相应的类型
            if (clazz.equals(byte.class) || clazz.equals(Byte.class)) {
                resultObj = Byte.parseByte(str);
            } else if (clazz.equals(short.class) || clazz.equals(Short.class)) {
                resultObj = Short.parseShort(str);
            } else if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
                resultObj = Integer.parseInt(str);
            } else if (clazz.equals(long.class) || clazz.equals(Long.class)) {
                resultObj = Long.parseLong(str);
            } else if (clazz.equals(float.class) || clazz.equals(Float.class)) {
                resultObj = Float.parseFloat(str);
            } else if (clazz.equals(double.class) || clazz.equals(Double.class)) {
                resultObj = Double.parseDouble(str);
            } else if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
                resultObj = Boolean.parseBoolean(str);
            } else {
                //如果不是以上类型，直接把字符串赋给resultObj
                resultObj = str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultObj;
    }

    //判断类是否是基本类型的封装类
    private static boolean isWrapClass(Class clz) {
        try {
            return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }


    public interface IdStrConverterForMap {
        String convert(String key, Map map);
    }

    public interface IdStrConverterForBean {
        String convert(String fieldName, Class<?> clazz);
    }
}
