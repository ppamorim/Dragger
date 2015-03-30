![Logo 1][10]

[![Build Status](https://api.travis-ci.org/ppamorim/Dragger.svg?branch=master)](https://travis-ci.org/ppamorim/Dragger)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.dragger/dragger/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.dragger/dragger)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Dragger-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1673)
[![Join the chat at https://gitter.im/ppamorim/Dragger](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/ppamorim/Dragger?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

The library was created in order to provide new animations for activities on Android.
Using the ViewDragHelper class, it was possible to create smooth animations and that's allow a full controls of the view for user.

This new component has been created using some concepts described on [Flavien Laurent Blog][1] and [Denevell Blog][2].

This library can works on API 8 (but not tested yet).

![Demo 1][11]

Usage
-----


You can use this library like a view, you need just do it:

* 1. Add ''DraggerView'' view on your root layout and add two layouts inside this.
You can add a shadow view if you want(the first one) and it need to be invisible.

```xml
<com.github.library.DraggerView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    dragger_layout:drag_view_id="@+id/drag_view"
    dragger_layout:shadow_view_id="@+id/shadow_view"
    dragger_layout:drag_position="top">

    <FrameLayout
          android:id="@+id/shadow_view"
          android:layout_width="match_parent"
          android:layout_height="match_parent"
          android:background="@color/transparent"
          android:visibility="invisible"/>

      <LinearLayout
          android:id="@+id/drag_view"
          android:layout_width="match_parent"
          android:layout_height="match_parent"/>

</com.github.library.DraggerView>
```

In your ''styles'' file, you need config like this:

```xml
<style name="YourTheme" parent="Theme.AppCompat.Light.DarkActionBar">
    <item name="android:windowIsTranslucent">true</item>
      <item name="android:windowBackground">@android:color/transparent</item>
      <item name="android:windowNoTitle">true</item>
      <item name="windowActionBar">false</item>
      <item name="android:windowAnimationStyle">@null</item>
</style>
```

And your ''manifest'':

```xml
<activity
    android:name="com.github.dragger.BaseActivity"
    android:theme="@style/YourTheme"/>
```

Or, if you need to be fast:

```java
public class YourActivity extends DraggerActivity {
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setShadowView(R.layout.custom_shadow); //No necessary, use if you want.
    setContentView(R.layout.layout_content);
  }
}
```

Sample
--------------------------------

<a href="https://play.google.com/store/apps/details?id=com.github.dragger">
  <img alt="Get it on Google Play"
       src="https://developer.android.com/images/brand/en_generic_rgb_wo_60.png" />
</a>

Import dependency
--------------------------------

This library uses nineoldandroids:2.4.0 and appcompat-v7:21.0.3, you have to provide this dependencies from your local artifact repository or from maven central repository.

I'll release a day on MavenCentral, I haven't much time and had to deploy on jitPack

Into your build.gradle:

```groovy

repositories {
  maven {
    url "https://jitpack.io"
  }
}

dependencies {
  compile 'com.github.ppamorim:Dragger:2324e5f2a5'
}
```

Contributors
------------

* [Pedro Paulo de Amorim][3]

This was only possible because [Pedro Vicente Gómez Sánchez][4] helped me and I am very grateful for that! Thank you. :)

Developed By
------------

* Pedro Paulo de Amorim - <pp.amorim@hotmail.com>

<a href="https://twitter.com/manodocarvao">
  <img alt="Follow me on Twitter" src="http://imageshack.us/a/img812/3923/smallth.png" />
</a>
<a href="https://www.linkedin.com/profile/view?id=185411359">
  <img alt="Add me to Linkedin" src="http://imageshack.us/a/img41/7877/smallld.png" />
</a>

Libraries used on the sample project
------------------------------------

* [Butterknife][5]
* [NineOldAndroids][6]
* [Picasso][7]

License
-------

    Copyright 2015 Pedro Paulo de Amorim

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: http://flavienlaurent.com/blog/2013/08/28/each-navigation-drawer-hides-a-viewdraghelper/
[2]: http://blog.denevell.org/android-viewdraghelper-example-tutorial.html
[3]: https://github.com/ppamorim/
[4]: https://github.com/pedrovgs/
[5]: https://github.com/JakeWharton/butterknife
[6]: https://github.com/JakeWharton/NineOldAndroids/
[7]: https://github.com/square/picasso
[10]: ./art/dragger.png
[11]: http://i.imgur.com/goPWgq1.gif