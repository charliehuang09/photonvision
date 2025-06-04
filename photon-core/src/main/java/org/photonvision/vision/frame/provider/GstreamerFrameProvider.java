/*
 * Copyright (C) Photon Vision.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.photonvision.vision.frame.provider;

import org.opencv.core.*;
import org.opencv.core.Mat;
import org.photonvision.common.logging.LogGroup;
import org.photonvision.common.logging.Logger;
import org.photonvision.common.util.math.MathUtils;
import org.photonvision.vision.camera.csi.GstreamerSettables;
import org.photonvision.vision.frame.Frame;
import org.photonvision.vision.frame.FrameProvider;
import org.photonvision.vision.frame.FrameThresholdType;
import org.photonvision.vision.opencv.CVMat;
import org.photonvision.vision.opencv.ImageRotationMode;
import org.photonvision.vision.pipe.impl.HSVPipe.HSVParams;

public class GstreamerFrameProvider extends FrameProvider {
  private final GstreamerSettables settables;
  static final Logger logger = new Logger(GstreamerFrameProvider.class, LogGroup.Camera);

  public GstreamerFrameProvider(GstreamerSettables visionSettables) {
    this.settables = visionSettables;

    var vidMode = settables.getCurrentVideoMode();
    settables.setVideoMode(vidMode);
  }

  @Override
  public String getName() {
    return "GstreammerCamera";
  }

  int badFrameCounter = 0;

  @Override
  public Frame get() {
    ++sequenceID;

    int width = 512;
    int height = 512;
    Mat hsvImage = new Mat(height, width, CvType.CV_8UC3);
    Scalar blueHSV = new Scalar(120, 255, 255);
    hsvImage.setTo(blueHSV);
    CVMat mat = new CVMat(hsvImage);

    return new Frame(
        sequenceID,
        mat,
        mat,
        FrameThresholdType.HSV,
        MathUtils.wpiNanoTime(),
        settables.getFrameStaticProperties());
  }

  @Override
  public void requestFrameThresholdType(FrameThresholdType type) {}

  @Override
  public void requestFrameRotation(ImageRotationMode rotationMode) {}

  @Override
  public void requestHsvSettings(HSVParams params) {}

  @Override
  public void requestFrameCopies(boolean copyInput, boolean copyOutput) {}

  @Override
  public void release() {}

  @Override
  public boolean checkCameraConnected() {
    return true;
  }

  @Override
  public boolean isConnected() {
    return true;
  }
}
