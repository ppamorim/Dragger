/*
* Copyright (C) 2015 Pedro Paulo de Amorim
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.github.ppamorim.dragger;

/**
 * Represent the position of the dragView when this is dragged.
 *
 * @author Pedro Paulo de Amorim
 */
public enum DraggerPosition {
  LEFT(0),
  RIGHT(1),
  TOP(2),
  BOTTOM(3);

  private int position;

  DraggerPosition(int position) {
    this.position = position;
  }

  public int getPosition() {
    return position;
  }

  public static DraggerPosition getDragPosition(int position) {
    switch (position) {
      case 0:
        return DraggerPosition.LEFT;
      case 1:
        return DraggerPosition.RIGHT;
      case 3:
        return DraggerPosition.BOTTOM;
      case 2:
      default:
        return DraggerPosition.TOP;
    }
  }

}
