package com.ppamorim.library;

public interface DraggerListener {
  void onViewPositionChanged(float dragValue);
  float dragRange();
}
