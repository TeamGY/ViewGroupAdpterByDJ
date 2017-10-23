# ViewGroupAdpterByDJ
求点评噻

阅读引导：
从ViewGroupAdpterByDJ\containers\view目录入手，进去看有两个自定义的ListView和GridView，
自定义的适配器在文件ViewGroupAdpterByDJ\containers\adapter\CustomAdapter.java

主要方便的，个人认为是对于ListView和GridView的自定义，添加了一个itemlayout属性，可以直接在layout.xml中直接引用添加item的layout布局文件。

缺陷：itemView中用到的控件都必须在ViewGroupAdpterByDJ\containers\view\common中定义，并调用同一个接口（implements ItemContainView），如下，
ViewGroupAdpterByDJ/containers/view/common (master)
$ ls
ButtonItemView.java  ItemContainLayout.java  SpinnerItemView.java
CheckItemView.java   ItemContainView.java    TextItemView.java
EditItemView.java    ItemLayout.java
ImageItemView.java   RadioItemView.java

否则，适配器无法自动处理view。
