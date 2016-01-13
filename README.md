![Logo 1][10]

[![Build Status](https://api.travis-ci.org/ppamorim/Dragger.svg?branch=master)](https://travis-ci.org/ppamorim/Dragger)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Dragger-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1673)
[![JitPack](https://img.shields.io/github/release/ppamorim/Dragger.svg?label=JitPack%20Maven)](https://jitpack.io/#ppamorim/Dragger)
[![Join the chat at https://gitter.im/ppamorim/Dragger](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/ppamorim/Dragger?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

If you want to use these transitions with fragments, take a look at [PrismView][123456789] library!

The library was created in order to provide new animations for activities on Android.
Using the ViewDragHelper class, it is possible to create smooth animations that allow full control of the view by the user.

This new component has been created using some concepts described on [Flavien Laurent Blog][1] and [Denevell Blog][2].

Dragger now uses [Rebound][14](tiny, 41.7kb) from Facebook to provide more realistic animations and improve performance for old devices.

This library should work on API 10 (but not tested [yet][1337]).

![Demo 1][11]

Usage
-----

You can use this library like a view, you just need to do the following:

* 1. Add ''DraggerView'' view to your root layout and add two layouts inside it.
You can add a shadow view if you want (the first one) and it needs to be invisible.

```xml
<com.github.ppamorim.library.DraggerView
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

</com.github.ppamorim.library.DraggerView>
```

In your ''styles'' file, you need a config like this:

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

Or, if you need it to be fast:

```java
public class YourActivity extends DraggerActivity {
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setShadowView(R.layout.custom_shadow); // It is not necessary, use if you want.
    setContentView(R.layout.layout_content);
  }
}
```

Now you can control the slide of the view, just use the method expand() when you want.

Some methods that you can use:

```java

setDraggerCallback(DraggerCallback) //Interface that's provides some infos of the animation.
setSlideEnabled(boolean) //Enable or disable the drag, useful to use with ScrollViews.
setHorizontalDragRange(float) //Draggable distance that the draggableView can use, horizontally.
setVerticalDragRange(float) //Draggable distance that the draggableView can use, vertically.
setRunAnimationOnFinishInflate(boolean) //Run the initial animation, useful if you only want the drag function.
setDraggerLimit(float) //Set the max limit drag, default is 0.5 (center of the screen).
setDraggerPosition(DraggerPosition) //Set the position of archor.
setTension(float) //Tension of the animation. This represent with the friction, how much time the animation will be executed.
setFriction(float) //Friction of the animation. This represent with the tension, how much friction is applied at the tension animation.
show() //Show the drag view with Rebound animation.
closeActivity() //Simply close the activity with Rebound animation, based of the DraggerPosition choosen.

```

Sample
------

<a href="https://play.google.com/store/apps/details?id=com.github.dragger">
  <img alt="Get it on Google Play"
       src="https://developer.android.com/images/brand/en_generic_rgb_wo_60.png" />
</a>

Import dependency
--------------------------------

This library use `appcompat-v7:23.1.0`.

But why not to add in MavenCentral?
Because it is so much bureaucratic.

JitPack is there and is the future!

Into your build.gradle:

```groovy

repositories {
  maven {
    url "https://jitpack.io"
  }
}

dependencies {
  compile 'com.github.ppamorim:dragger:1.2'
}
```


Will you use with lists(RecyclerView, List/GridView, ScrollView)?
--------------------------------------------------------------------

I decided that need to be Out-Of-Box, then...
You must use the [Android-ObservableScrollView][13], like this:

```groovy
dependencies {
  compile 'com.github.ksoichiro:android-observablescrollview:VERSION'
}
```

```java

public void configRecyclerView() {
  ...
  recyclerView.setScrollViewCallbacks(onObservableScrollViewCallbacks);
}

private ObservableScrollViewCallbacks onObservableScrollViewCallbacks =
    new ObservableScrollViewCallbacks() {
  @Override public void onScrollChanged(int scrollY, boolean firstScroll,
      boolean dragging) {
    draggerView.setSlideEnabled(scrollY != 0);
  }
  @Override public void onDownMotionEvent() { }
  @Override public void onUpOrCancelMotionEvent(ScrollState scrollState) { }
};
```

Contributors
------------

* [Pedro Paulo de Amorim][3]
* [Falkirks][12]

This was only possible because [Pedro Vicente Gómez Sánchez][4] helped me and I am very grateful for that! Thank you. :)
And thank you Facebook for provide [Rebound][14] library!

Developed By
------------

* Pedro Paulo de Amorim - <pp.amorim@hotmail.com>

<a href="https://www.linkedin.com/profile/view?id=185411359">
  <img alt="Add me to Linkedin" src="http://imageshack.us/a/img41/7877/smallld.png" />
</a>

Libraries used on the sample project
------------------------------------

* [Butterknife][5]

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
[10]: ./art/dragger_new.png
[11]: https://github.com/ppamorim/Dragger/blob/master/art/app_sample_uncompressed.gif?raw=true
[12]: https://github.com/Falkirks
[13]: https://github.com/ksoichiro/Android-ObservableScrollView
[14]: https://github.com/facebook/rebound
[1337]: https://www.youtube.com/watch?v=eQyjP2O1S40
[123456789]: https://github.com/ppamorim/PrismView
