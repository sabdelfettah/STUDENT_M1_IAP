package lang;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import mvc.view.Explorer;
import mvc.view.MenuBar;
import mvc.view.Modifier;
import mvc.view.Popup;
import mvc.view.Toolbox;

public class Langage {
	private static String BUNDLE_NAME = "lang.Langage"; //$NON-NLS-1$

	private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Langage() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static void ModifieLangage(Locale locale) {
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, locale);
		MenuBar.getInstance().setText(locale);
		Toolbox.getInstance().setText();
		Modifier.getInstance().setText();
		Explorer.getInstance().setText();
		Popup.getDefaultInstance().setText();
	}
	
}
