/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.marklau;

public class ProgressBar {
  private final long day = 24 * 3600 * 1000;
  private final long hour = 3600 * 1000;
  private final long minute = 60 * 1000;
  private final long second = 1000;
  private final long maxStep;
  private long currentStep = 0;
  private long startTime;
  private int width = 50;

  ProgressBar(long maxStep) {
    this.maxStep = maxStep;
    this.startTime = System.currentTimeMillis();
  }

  public void update() {
    currentStep += 1;
    printProgress();
  }

  public void update(long step) {
    currentStep += step;
    printProgress();
  }

  private void printProgress() {
    long passTime = System.currentTimeMillis() - startTime;
    long remainTime = passTime * (maxStep - currentStep) / currentStep;
    System.out.print(
        String.format(
            "\r[%s%s] %15s/%15s",
            stringMultiply("#", currentStep * 50 / maxStep),
            stringMultiply(" ", 50 - currentStep * 50 / maxStep),
            formatTime(passTime),
            formatTime(remainTime + passTime)));
  }

  private String formatTime(long time) {
    StringBuilder result = new StringBuilder();
    boolean d = false, m = false;
    if (time > day) {
      result.append(time / day).append("d ");
      time %= day;
      d = true;
    }
    if (time > hour) {
      result.append(time / hour).append("h ");
      time %= hour;
      m = true;
    }
    if (time > minute) {
      result.append(time / minute).append("m ");
      time %= minute;
      m = true;
    }
    if (time > second && !d) {
      result.append(time / second).append("s ");
      time %= second;
    }
    if (time > 0 && !m) {
      result.append(time).append("ms");
    }
    return result.toString();
  }

  private String stringMultiply(String base, long time) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < time; i++) {
      sb.append(base);
    }
    return sb.toString();
  }
}
