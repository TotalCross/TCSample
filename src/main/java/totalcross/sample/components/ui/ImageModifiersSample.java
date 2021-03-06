package totalcross.sample.components.ui;

import totalcross.sample.components.BaseScreen;
import totalcross.sample.util.Colors;
import totalcross.sys.Convert;
import totalcross.sys.Vm;
import totalcross.ui.*;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Rect;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

public class ImageModifiersSample extends BaseScreen {
  private Slider slRotate, slScale, slContrast, slBrightness;
  private Label lbRotate, lbScale, lbContrast, lbBrightness;
  private ImageControl imgFrm;
  private Image img, imgRotated, imgContrasted;
  private Rect rectImg;
  private int rotateLevel; // -180 .. +180
  private int scaleLevel; // 1 .. infinity
  private int gap = 50;
  private byte contrastLevel; // -128 .. 127
  private byte brightnessLevel; // -128 .. 127

  public ImageModifiersSample () {
    super("https://totalcross.gitbook.io/playbook/components/image");
  }

  @Override
  public void onContent(ScrollContainer content) {
    setBackForeColors(Colors.BACKGROUND, Colors.ON_BACKGROUND);
    Label l;

    content.add(l = new Label("Rotate "), LEFT + gap*2, TOP+gap);
    content.add(slRotate = new Slider(ScrollBar.HORIZONTAL));
    slRotate.setValues(180, 1, 0, 361);
    slRotate.setUnitIncrement(5);
    slRotate.setBlockIncrement(30);
    slRotate.setLiveScrolling(true);
    slRotate.setRect(RIGHT - gap, SAME + 2, PARENTSIZE + 45, PREFERRED + fmH / 4, lbRotate);
    content.add(lbRotate = new Label("0000"), AFTER, SAME, FIT, SAME, l);

    content.add(l = new Label("Scale "), LEFT + gap*2, AFTER);
    content.add(slScale = new Slider(ScrollBar.HORIZONTAL));
    slScale.setValues(100, 1, 0, 400);
    slScale.setUnitIncrement(5);
    slScale.setBlockIncrement(20);
    slScale.setLiveScrolling(true);
    slScale.setRect(RIGHT - gap, SAME + 2, PARENTSIZE + 45, PREFERRED + fmH / 4, lbScale);
    content.add(lbScale = new Label("0000"), AFTER, SAME, FIT, SAME, l);

    content.add(l = new Label("Contrast "), LEFT + gap*2, AFTER);
    content.add(slContrast = new Slider(ScrollBar.HORIZONTAL));
    slContrast.setValues(128, 0, 0, 256);
    slContrast.setUnitIncrement(8);
    slContrast.setBlockIncrement(32);
    slContrast.setLiveScrolling(true);
    slContrast.setRect(RIGHT - gap, SAME + 2, PARENTSIZE + 45, PREFERRED + fmH / 4, lbContrast);
    content.add(lbContrast = new Label("0000"), AFTER, SAME, FIT, SAME, l);

    content.add(l = new Label("Brightness "), LEFT + gap*2, AFTER);
    content.add(slBrightness = new Slider(ScrollBar.HORIZONTAL));
    slBrightness.setValues(128, 0, 0, 256);
    slBrightness.setUnitIncrement(8);
    slBrightness.setBlockIncrement(32);
    slBrightness.setLiveScrolling(true);
    slBrightness.setRect(RIGHT - gap, SAME + 2, PARENTSIZE + 45, PREFERRED + fmH / 4, lbBrightness);
    content.add(lbBrightness = new Label("0000"), AFTER, SAME, FIT, SAME, l);

    lbRotate.setText("0");
    lbContrast.setText("0");
    lbBrightness.setText("0");
    lbScale.setText("100");

    slRotate.sliderColor = slScale.sliderColor = slContrast.sliderColor = slBrightness.sliderColor = Colors.P_600;

    content.add(imgFrm = new ImageControl());
    imgFrm.setRect(LEFT + 1, AFTER, FILL - 2, FILL - 2, lbBrightness);
    rectImg = imgFrm.getAbsoluteRect();
    imgFrm.allowBeyondLimits = false;
    imgFrm.centerImage = true;
    imgFrm.setEventsEnabled(false);

    loadImage("images/lenna.png");
  }

  private void loadImage(String dsc) {
    img = null;
    imgContrasted = null;
    imgRotated = null;

    try {
      img = new Image(dsc);
      int scaleW = (100 * rectImg.width) / img.getWidth();
      int scaleH = (100 * rectImg.height) / img.getHeight();
      if ((scaleH < 100) || (scaleW < 100)) {
        slScale.setValue((scaleH < scaleW) ? scaleH : scaleW);
      } else {
        slScale.setValue(100);
      }
      onEvent(getPressedEvent(slScale));
    } catch (ImageException ie) {
      tellUser("Error", "Cannot decode " + dsc);
    } catch (totalcross.io.IOException ioe) {
      tellUser("Error", "Error ocurred while processing the file " + dsc + "/n" + ioe.getMessage());
    }
  }

  private void setImage(Image img) {
    imgFrm.setImage(img);
  }

  @Override
  public void onEvent(Event event) {
    switch (event.type) {
    case ControlEvent.PRESSED:
      if (event.target instanceof ScrollBar) {
        try {
          if ((event.target == slContrast) || (event.target == slBrightness)) {
            byte newBrightnessLevel = (byte) (slBrightness.getValue() - 128);
            byte newContrastLevel = (byte) (slContrast.getValue() - 128);
            if (newBrightnessLevel == brightnessLevel && newContrastLevel == contrastLevel) {
              return;
            }
            brightnessLevel = newBrightnessLevel;
            contrastLevel = newContrastLevel;
            lbBrightness.setText(Convert.toString(brightnessLevel));
            lbContrast.setText(Convert.toString(contrastLevel));
            if (img != null) {
              try {
                if (imgRotated == null) {
                  imgContrasted = null;
                  Vm.gc();
                  imgRotated = img.getRotatedScaledInstance(scaleLevel, rotateLevel, 0);
                }
                setImage(imgRotated.getTouchedUpInstance(brightnessLevel, contrastLevel));
              } catch (ImageException e) {
                // TODO Auto-generated catch block
                totalcross.ui.dialog.MessageBox.showException(e, true);
              }
            }
          } else if ((event.target == slRotate) || (event.target == slScale)) {
            int newRotateLevel = slRotate.getValue() - 180;
            int newScaleLevel = slScale.getValue();
            if (newRotateLevel == rotateLevel && newScaleLevel == scaleLevel) {
              return;
            }
            rotateLevel = newRotateLevel;
            scaleLevel = newScaleLevel;
            lbRotate.setText(Convert.toString(rotateLevel));
            lbScale.setText(Convert.toString(scaleLevel));
            if (img != null) {
              if (imgContrasted == null) {
                imgRotated = null;
                Vm.gc();
                imgContrasted = img.getTouchedUpInstance(brightnessLevel, contrastLevel);
              }
              setImage(imgContrasted.getRotatedScaledInstance(scaleLevel, rotateLevel, getBackColor()));
            }
          }
        } catch (ImageException e) {
          Vm.alert(e.getMessage());
        }
      }
      
      img.applyChanges();
      repaint();
      break;
    }
  }

  private void tellUser(String topic, String what) {
    MessageBox mb = new MessageBox(topic, what);
    mb.setTextAlignment(LEFT);
    mb.setBackForeColors(Colors.ERROR, Colors.ON_ERROR);
    mb.popupNonBlocking();
  }
}

