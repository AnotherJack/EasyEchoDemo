EasyEcho 轻松实现页面数据回显和保存，让你从无尽的setText和getText中解脱出来
===============================

# 集成方法
不需要集成，所有的方法都在EasyEcho.java中，直接当工具类拷贝到项目中使用
# 主要功能介绍
通常，我们服务器获得json数据后，会需要解析为java bean对象，或解析为Map、List数据，再显示到页面上。
以下面这个json数据为例
```
{
    "name": "小明",
    "age": 15,
    "sex": "男",
    "home": "北京",
    "hobby": "看电影",
    "extra": "其他",
    "grades": {
        "chinese": 95,
        "math": 100,
        "english": 90,
        "level": "优秀"
    }
}
```
如果是解析为Map，则是Map嵌套Map的形式；如果是转为Java Bean，也很简单了，用Gson，GsonFormat插件直接生成Bean类。

但是无论哪种方式，都逃不了往页面上赋值这一繁琐步骤，要findViewById获取所有的控件，还要给所有的控件setText（我们公司做政府执法软件的，经常一个页面几十个录入项 T﹏T）。

使用EasyEcho就可以一行代码解决这个问题啦！<（￣︶￣）>

当然这要求布局里id字符串（比如R.id.name，那这里说的id字符串就是"name"）和服务器给的数据字段名称有联系，以例子来说，姓名这一项字段为"name"，布局里的id字符串为"name"或是形如"tv_name"这种，只要可以根据数据字段转为id字符串，那就可以用。

## 方法介绍
### （一）public static void echoMap(Map map, View parent, IdStrConverterForMap idStrConverter)
#### 作用
把map型数据显示到页面上
#### 返回值 void
#### 参数介绍
1. Map map:要显示到页面上的数据
2. View parent:数据要显示的父布局，即决定了在哪个view上findViewById。
3. IdStrConverterForMap idStrConverter:接口，需要实现convert方法，用于把map数据字段根据你自己的命名规则转为id字符串
### （二）public static Map saveAsMap(Map defaultMap, View parent, IdStrConverterForMap idStrConverter)
#### 作用
根据一个默认map（模板map）把页面数据保存成map型
#### 返回值 Map
#### 参数介绍
1. Map defaultMap:默认的map，也相当于一个模板map，方法内所有的赋值均是在这个map上操作的，所以在这个方法执行完毕后，原来传入的map也改变了。事实上，返回的map就是这个defaultMap，Java是地址传递嘛。基于这个原因，建议每次调用这个方法时传入的defaultMap都是新生成的。
2. View parent:要保存哪个父布局下的数据
3. IdStrConverterForMap idStrConverter:id字符串转换器
### （三）public static void echoBean(Object obj, View parent, IdStrConverterForBean idStrConverter)
#### 作用
把Bean型数据显示到页面上
#### 返回值 void
#### 参数介绍
1. Object obj:要回显的数据，Java Bean实例
2. View parent:数据要显示的父布局
3. IdStrConverterForBean idStrConverter:id字符串转换器，注意这个是ForBean的，convert方法的第二个参数和ForMap的有所不同
### （四）public static \<T> T saveAsBean(Class\<T> clazz, View parent, IdStrConverterForBean idStrConverter)
#### 作用
把页面数据保存成相应Bean类型
#### 返回值 即为第一个参数传的Class类型的实例
#### 参数介绍
1. Class\<T> clazz:Java Bean类，要把页面数据保存成什么类型的实体类
2. View parent:要保存哪个父布局下的数据
3. IdStrConverterForBean idStrConverter:id字符串转换器
### 其他重载方法
以上是四个主要方法，还有一些重载方法总体说一下，不再一一介绍
1. 第二个参数除了可以传View，也可以传Activity，传Activity相当于显示（或保存）该activity根布局上的数据。
2. 可以不传idStrConverter，不传的话默认数据的字段和id字符串是相等关系。
## 注意事项
1. 数据类型为Map时，echoMap和saveAsMap不回显（或保存）内部嵌套的其他集合类，如List、Set等，只有嵌套的是Map的时候才会递归操作内部Map。
2. 数据类型为Bean时，echoBean和saveAsBean不回显（或保存）集合类（Collection和Map都不操作），对于基本类型和字符串会赋值到页面上，对于其他类型会当作Bean进行递归。
3. 关于null，显示数据时，Map和Bean类型都不对null进行显示；
保存数据时，defaultMap中值为null的键值对无法进行赋值，因为要通过Entry.getValue().getClass()来获取map值的类型，进而赋值，如果是null，会导致空指针。
4. 关于保存数据的默认值，比如例子中的"extra"字段，在我们的页面上是没有与之对应的TextView的，但是服务器又需要这个字段，就可以考虑设置默认值的方式。
在saveAsMap中，defaultMap里各个字段的值就是默认值，对于页面上没有控件的字段，会直接跳过不予赋值，也就是保持原来的值。
在saveAsBean中，默认值可以考虑在Bean实体类中给成员变量赋值，这样如果该属性在页面有与之对应的控件，就会用控件里的值将其覆盖；反之，没有对应的控件时会直接跳过。
5. 保存数据时，数据类型有误时会赋值失败，比如"age"字段应该是int型的，可是却在控件中输入了"abc"，这样就无法转成int型，所得到的Bean实例中age会保持为默认值。
## 大概就是这么多了，因为也是想到哪改到哪，可能会有不妥的地方，欢迎大家指出。有兴趣的可以看看代码，我的注释写的还挺详细的，如果觉得有用的话，欢迎star╰（￣▽￣）╭
