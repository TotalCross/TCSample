package totalcross.sample.main;

import totalcross.io.IOException;
import totalcross.sample.components.Home;
import totalcross.sample.components.cards.CardsSample;
import totalcross.sample.components.crypto.CipherSample;
import totalcross.sample.components.crypto.DigestSample;
import totalcross.sample.components.crypto.SignatureSample;
import totalcross.sample.components.io.FileSample;
import totalcross.sample.components.json.JSONSample;
import totalcross.sample.components.lang.thread.ThreadSample;
import totalcross.sample.components.layout.CalculatorHBoxVBoxSample;
import totalcross.sample.components.layout.HBoxVBoxSample;
import totalcross.sample.components.layout.NestedLayouts;
import totalcross.sample.components.net.SocketSample;
import totalcross.sample.components.phone.PhoneDialerSample;
import totalcross.sample.components.sql.SQLiteBenchSample;
import totalcross.sample.components.sql.SQLiteFormGridTabbedContainer;
import totalcross.sample.components.sys.CommandLine;
import totalcross.sample.components.sys.SettingsSample;
import totalcross.sample.components.ui.AccordionSample;
import totalcross.sample.components.ui.AlignedLabelsSample;
import totalcross.sample.components.ui.ButtonSample;
import totalcross.sample.components.ui.CameraSample;
import totalcross.sample.components.ui.CheckRadioSample;
import totalcross.sample.components.ui.ComboListSample;
import totalcross.sample.components.ui.DynScrollContainerSample;
import totalcross.sample.components.ui.EditSample;
import totalcross.sample.components.ui.FontSample;
import totalcross.sample.components.ui.GraphicsSample;
import totalcross.sample.components.ui.ImageAnimationSample;
import totalcross.sample.components.ui.ImageModifiersSample;
import totalcross.sample.components.ui.Login;
import totalcross.sample.components.ui.MaterialIconsSample;
import totalcross.sample.components.ui.MaterialWindowSample;
import totalcross.sample.components.ui.MessageBoxSample;
import totalcross.sample.components.ui.MultiTouchSample;
import totalcross.sample.components.ui.NinePatchSample;
import totalcross.sample.components.ui.OtherControlsSample;
import totalcross.sample.components.ui.ProgressBoxSample;
import totalcross.sample.components.ui.SliderSwitchSample;
import totalcross.sample.components.ui.SpinnerSample;
import totalcross.sample.components.ui.TabbedContainerSample;
import totalcross.sample.components.ui.TopMenuSample;
import totalcross.sample.components.ui.VelocimeterSample;
import totalcross.sample.components.ui.charts.ArcChartSample;
import totalcross.sample.components.ui.charts.ColumnChartSample;
import totalcross.sample.components.ui.charts.LineChartSample;
import totalcross.sample.components.ui.charts.PieChartSample;
import totalcross.sample.components.util.ZLibSample;
import totalcross.sample.components.xml.XMLParseSample;
import totalcross.sample.util.Colors;
import totalcross.sys.Settings;
import totalcross.ui.Container;
import totalcross.ui.Control;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.SideMenuContainer;
import totalcross.ui.SideMenuContainer.Sub;
import totalcross.ui.font.Font;
import totalcross.ui.gfx.Color;
import totalcross.ui.icon.MaterialIcons;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;
import totalcross.util.UnitsConverter;

public class TCSample extends MainWindow {
	SideMenuContainer sideMenu;

	static {
		Settings.applicationId = "VKSS";
		Settings.appVersion = "5.0.1";
		Settings.iosCFBundleIdentifier = "com.totalcross.tcsample";
	}

	public TCSample() {
		super("TotalCross Showcase", NO_BORDER);

		setUIStyle(Settings.MATERIAL_UI);
		uiAdjustmentsBasedOnFontHeightIsSupported = false;
		setBackForeColors(Colors.BACKGROUND, Colors.SURFACE);
	}

	@Override
	public void initUI() {
		MainWindow.getMainWindow().addTimer(100);
		
		SideMenuContainer.Item home = new SideMenuContainer.Item("Home", MaterialIcons._HOME, Color.BLACK, false, () -> { return new Home(); });
		SideMenuContainer.Sub uiGroup = createUISubGroup();
		SideMenuContainer.Sub layoutGroup = createLayoutSubGroup();
		SideMenuContainer.Sub sqlGroup = createSQLSubGroup();
		SideMenuContainer.Sub chartGroup = createChartSubGroup();
		SideMenuContainer.Sub cryptoGroup = createCryptoSubGroup();
		SideMenuContainer.Sub ioGroup = createIOSubGroup();
		SideMenuContainer.Sub jsonGroup = createJSONSubGroup();
		SideMenuContainer.Sub langGroup = createLangSubGroup();
		SideMenuContainer.Sub netGroup = createNetSubGroup();
		SideMenuContainer.Sub phoneGroup = createPhoneSubGroup();
		SideMenuContainer.Sub sysGroup = createSystemSubGroup();
		SideMenuContainer.Sub utilGroup = createUtilSubGroup();
		SideMenuContainer.Sub xmlGroup = createXMLSubGroup();
		
		sideMenu = new SideMenuContainer(null,
				home, 
				uiGroup,
				layoutGroup,
				chartGroup,
				sqlGroup, 
				cryptoGroup, 
				ioGroup, 
				jsonGroup, 
				langGroup, 
				netGroup, 
				phoneGroup, 
				sysGroup, 
				utilGroup,
				xmlGroup);

		sideMenu.topMenu.header = new Container() {
			@Override
			public void initUI() {
				try {
					setBackColor(Colors.SECONDARY);

					Label title = new Label("Showcase", LEFT, Color.WHITE, false);
					title.setFont(Font.getFont("Lato Bold", false, this.getFont().size + 5));
					title.setForeColor(Color.WHITE);
					add(title, LEFT + UnitsConverter.toPixels(Control.DP + 10), BOTTOM - UnitsConverter.toPixels(Control.DP + 10), FILL, DP + 56);

					ImageControl profile = new ImageControl(new Image("images/logoV.png"));
					profile.scaleToFit = true;
					profile.transparentBackground = true;
					add(profile, LEFT + UnitsConverter.toPixels(Control.DP + 10), UnitsConverter.toPixels(Control.DP + 25), PREFERRED, FIT);

				} catch (IOException | ImageException e) {
					e.printStackTrace();
				}
			}
		};

		sideMenu.setBarFont(Font.getFont(Font.getDefaultFontSize() + 5));
		sideMenu.setBackColor(Colors.PRIMARY);
		sideMenu.setForeColor(Color.WHITE);
		sideMenu.setItemForeColor(Color.BLACK);
		sideMenu.topMenu.drawSeparators = false;
		sideMenu.topMenu.itemHeightFactor = 3;

		add(sideMenu, LEFT, TOP, PARENTSIZE, PARENTSIZE);
	}

  	private Sub createIOSubGroup() {
  		return new SideMenuContainer.Sub("IO", 
			new SideMenuContainer.Item("File System Check", MaterialIcons._FOLDER, Color.BLACK, () -> { return new FileSample(); }));
  	}

  	private Sub createSQLSubGroup() {
  		return new SideMenuContainer.Sub("SQL", 
			new SideMenuContainer.Item("SQLite / Grid", MaterialIcons._STORAGE, Color.BLACK,  () -> { return new SQLiteFormGridTabbedContainer(); }),
			new SideMenuContainer.Item("SQLite Bench", MaterialIcons._INPUT, Color.BLACK, () -> { return new SQLiteBenchSample(); }));
  	}

  	private Sub createXMLSubGroup() {
  		return new SideMenuContainer.Sub("XML",
  			new SideMenuContainer.Item("XML", MaterialIcons._CODE, Color.BLACK, () -> { return new XMLParseSample(); }));
	}

	private Sub createUISubGroup() {
	   return new SideMenuContainer.Sub("UI",
		   new SideMenuContainer.Item("Accordion Container", MaterialIcons._FORMAT_ALIGN_LEFT, Color.BLACK,  () -> { return new AccordionSample(); }),
		   new SideMenuContainer.Item("Aligned Labels", MaterialIcons._MENU, Color.BLACK,  () -> { return new AlignedLabelsSample(); }),
		   new SideMenuContainer.Item("Button", MaterialIcons._TOUCH_APP, Color.BLACK,  () -> { return new ButtonSample(); }),
		   new SideMenuContainer.Item("Camera", MaterialIcons._PHOTO_CAMERA, Color.BLACK,  () -> { return new CameraSample(); }),
		   new SideMenuContainer.Item("Cards", MaterialIcons._VIEW_AGENDA, Color.BLACK,  () -> { return new CardsSample(); }),
		   new SideMenuContainer.Item("Check and Radio", MaterialIcons._CHECK_BOX, Color.BLACK,  () -> { return new CheckRadioSample(); }),
		   new SideMenuContainer.Item("Combo and List", MaterialIcons._ARROW_DROP_DOWN_CIRCLE, Color.BLACK,  () -> { return new ComboListSample(); }),
		   new SideMenuContainer.Item("Dynamic Scroll Container", MaterialIcons._TEXT_FORMAT, Color.BLACK,  () -> { return new DynScrollContainerSample(); }),
		   new SideMenuContainer.Item("Edit", MaterialIcons._TEXT_FORMAT, Color.BLACK,  () -> { return new EditSample(); }), 
		   new SideMenuContainer.Item("Font sizes", MaterialIcons._SORT_BY_ALPHA, Color.BLACK,  () -> { return new FontSample(); }),
		   new SideMenuContainer.Item("Graphics", MaterialIcons._GRADIENT, Color.BLACK, () -> { return new GraphicsSample(); }),
		   new SideMenuContainer.Item("Image Animation", MaterialIcons._GIF, Color.BLACK,  () -> { return new ImageAnimationSample(); }),
		   new SideMenuContainer.Item("Image Modifiers", MaterialIcons._IMAGE, Color.BLACK,  () -> { return new ImageModifiersSample(); }),
		   new SideMenuContainer.Item("Login", MaterialIcons._PERSON, Color.BLACK,  () -> { return new Login(); }),
		   new SideMenuContainer.Item("Material Window", MaterialIcons._DESKTOP_WINDOWS, Color.BLACK,  () -> { return new MaterialWindowSample(); }),
		   new SideMenuContainer.Item("Material Icons", MaterialIcons._FONT_DOWNLOAD, Color.BLACK,  () -> { return new MaterialIconsSample(); }),
		   new SideMenuContainer.Item("Message Box", MaterialIcons._QUESTION_ANSWER, Color.BLACK,  () -> { return new MessageBoxSample(); }),
		   new SideMenuContainer.Item("Nine Patch", MaterialIcons._CROP_PORTRAIT, Color.BLACK,  () -> { return new NinePatchSample(); }),
		   new SideMenuContainer.Item("Multi touch", MaterialIcons._TOUCH_APP, Color.BLACK,  () -> { return new MultiTouchSample(); }),
		   new SideMenuContainer.Item("Other Controls", MaterialIcons._SLIDESHOW, Color.BLACK,  () -> { return new OtherControlsSample(); }),	
		   new SideMenuContainer.Item("ProgressBox", MaterialIcons._INDETERMINATE_CHECK_BOX, Color.BLACK,  () -> { return new ProgressBoxSample(); }),
		   new SideMenuContainer.Item("Spinner Inside Loop", MaterialIcons._LOOP , Color.BLACK,  () -> { return new SpinnerSample(); }),	
		   new SideMenuContainer.Item("Slider and Switch", MaterialIcons._LINEAR_SCALE, Color.BLACK,  () -> { return new SliderSwitchSample(); }),	
		   new SideMenuContainer.Item("Tabbed Container", MaterialIcons._VIEW_ARRAY, Color.BLACK,  () -> { return new TabbedContainerSample(); }),
		   new SideMenuContainer.Item("Top Menu", MaterialIcons._BORDER_TOP, Color.BLACK,  () -> { return new TopMenuSample(); }),
		   new SideMenuContainer.Item("Velocimeter", MaterialIcons._LOOKS, Color.BLACK,  () -> { return new VelocimeterSample(); }));
	}
	
	private Sub createLayoutSubGroup() {
  		return new SideMenuContainer.Sub("Layout",
  			new SideMenuContainer.Item("Calculator", MaterialIcons._PLUS_ONE, Color.BLACK,  () -> { return new CalculatorHBoxVBoxSample(); }),
  			new SideMenuContainer.Item("HBox", MaterialIcons._LANDSCAPE, Color.BLACK, () -> { return new HBoxVBoxSample(true); }),
			new SideMenuContainer.Item("VBox", MaterialIcons._PORTRAIT, Color.BLACK, () -> { return new HBoxVBoxSample(false); }),
			new SideMenuContainer.Item("Nested Layouts", MaterialIcons._PORTRAIT, Color.BLACK, () -> { return new NestedLayouts(); }));
	}
	
	private Sub createChartSubGroup() {
		return new SideMenuContainer.Sub("Chart", 
			new SideMenuContainer.Item("Arc Chart", MaterialIcons._BUBBLE_CHART, Color.BLACK,  () -> { return new ArcChartSample(); }),
			new SideMenuContainer.Item("Column Chart", MaterialIcons._INSERT_CHART, Color.BLACK,  () -> { return new ColumnChartSample(); }),
			new SideMenuContainer.Item("Line Chart", MaterialIcons._SHOW_CHART, Color.BLACK,  () -> { return new LineChartSample(); }),
			new SideMenuContainer.Item("Pie Chart", MaterialIcons._PIE_CHART, Color.BLACK,  () -> { return new PieChartSample(); }));
	}
	
	private Sub createCryptoSubGroup() {
		return new SideMenuContainer.Sub("Crypto", 
			new SideMenuContainer.Item("Cipher", MaterialIcons._INDETERMINATE_CHECK_BOX, Color.BLACK,  () -> { return new CipherSample(); }),
			new SideMenuContainer.Item("Digest", MaterialIcons._SLIDESHOW, Color.BLACK,  () -> { return new DigestSample(); }),
			new SideMenuContainer.Item("Signature", MaterialIcons._SETTINGS_ETHERNET, Color.BLACK,  () -> { return new SignatureSample(); }));
	}
	
	private Sub createJSONSubGroup() {
		return new SideMenuContainer.Sub("JSON", 
			new SideMenuContainer.Item("JSON", MaterialIcons._SETTINGS_ETHERNET, Color.BLACK, () -> { return new JSONSample();}));
	}

	private Sub createLangSubGroup() {
		return new SideMenuContainer.Sub("Lang", 
			new SideMenuContainer.Item("Thread", MaterialIcons._LINE_STYLE, Color.BLACK, () -> { return new ThreadSample(); }));
	}

	private Sub createNetSubGroup() {
		return new SideMenuContainer.Sub("Net",
				new SideMenuContainer.Item("Socket", MaterialIcons._HTTP, Color.BLACK, () -> { return new SocketSample(); }));
	}

	private Sub createPhoneSubGroup() {
		return new SideMenuContainer.Sub("Phone",
				new SideMenuContainer.Item("Dialer", MaterialIcons._CALL, Color.BLACK, () -> { return new PhoneDialerSample(); }));
	}

	private Sub createSystemSubGroup() {
		return new SideMenuContainer.Sub("System",
			new SideMenuContainer.Item("Settings", MaterialIcons._SETTINGS_APPLICATIONS, Color.BLACK, () -> { return new SettingsSample(); }),
			new SideMenuContainer.Item("Command Line", MaterialIcons._SETTINGS_APPLICATIONS, Color.BLACK, () -> { return new CommandLine(); }));
	}

	private Sub createUtilSubGroup() {
		return new SideMenuContainer.Sub("Util",
			new SideMenuContainer.Item("ZLib", MaterialIcons._SORT, Color.BLACK, () -> { return new ZLibSample(); }));
	}
}