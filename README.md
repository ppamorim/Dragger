![Logo 1][10]

[![Build Status](https://api.travis-ci.org/ppamorim/Dragger.svg?branch=master)](https://travis-ci.org/ppamorim/Dragger)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.dragger/dragger/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.dragger/dragger)

The library was created in order to provide new animations for activities on Android.
Using the ViewDragHelper class, it was possible to create smooth animations and that's allow a full controls of the view for user.

This new component has been created using some concepts described on [Flavien Laurent Blog][1] and [Denevell Blog][2].

This library can works on API 8 (but not tested yet).

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
          android:layout_height="match_parent"
          android:background="@color/black_grey"
          android:gravity="center_horizontal"
          android:orientation="vertical"/>

</com.github.library.DraggerView>
```

Import dependency
--------------------------------

This library uses nineoldandroids:2.4.0 and appcompat-v7:21.0.3, you have to provide this dependencies from your local artifact repository or from maven central repository.

Or into your build.gradle

```groovy
dependencies {
    waiting sonatype...
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
[10]: ./art/dragger.png