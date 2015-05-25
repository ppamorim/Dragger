package com.github.ppamorim.dragger;

public class LazyDraggerActivity extends BaseDraggerActivity {

  private LazyDraggerPanel lazyDraggerPanel;

  @Override public void setContentView(int layoutResID) {
    configDraggerView();
    configViews(layoutResID);
    super.setContentView(lazyDraggerPanel);
  }

  public LazyDraggerPanel getDraggerPanel() {
    return lazyDraggerPanel;
  }

  private void configDraggerView() {
    lazyDraggerPanel = new LazyDraggerPanel(this);
    lazyDraggerPanel.initializeView();
  }

  private void configViews(int layoutResID) {
    lazyDraggerPanel.addViewOnDrag(inflateLayout(layoutResID));
    if (shadowResID == -1) {
      shadowResID = R.layout.layout_shadow;
    }
    lazyDraggerPanel.addViewOnShadow(inflateLayout(shadowResID));
  }

  public void setDraggerPosition(DraggerPosition dragPosition) {
    lazyDraggerPanel.setLazyDraggerPosition(dragPosition);
  }

  public void setDraggerLimit(float dragLimit) {
    lazyDraggerPanel.setLazyDraggerLimit(dragLimit);
  }

  public void setDraggerCallback(DraggerCallback draggerCallback) {
    lazyDraggerPanel.setLazyDraggerCallback(draggerCallback);
  }

  public void setSlideEnabled(boolean enabled) {
    lazyDraggerPanel.setSlideEnabled(enabled);
  }

  public void closeActivity() {
    lazyDraggerPanel.closeActivity();
  }

  public void expand() {
    lazyDraggerPanel.expand();
  }

  public void setAnimationDuration(int baseSettleDuration, int maxSettleDuration) {
    lazyDraggerPanel.setAnimationDuration(baseSettleDuration, maxSettleDuration);
  }

  public void setAnimationDuration(int miliseconds, float multipler) {
    lazyDraggerPanel.setAnimationDuration(miliseconds, multipler);
  }

}
