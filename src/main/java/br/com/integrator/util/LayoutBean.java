package br.com.integrator.util;

import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class LayoutBean {
	
	public LayoutBean() {
		
	}

	private String theme = "hot-sneaks";
	/*private String[] themes = { "dark-hive", "aristo", "black-tie", "blitzer",
			"casablanca", "cupertino", "dark-hive", "dot-luv", "eggplant",
			"excite-bike", "flick ", "hot-sneaks", "humanity", "le-frog",
			"midnight (wijmo)", "mint-choc", "overcast", "pepper-grinder",
			"redmond", "rocket (wijmo)", "sam", "smoothness", "south-street",
			"start", "sunny", "swanky-purse", "trontastic", "ui-darkness",
			"ui-lightness", "vader", "bluesky", "glass-x", "home" };*/
	private String[] themes = { "none", "aristo", "cupertino", "dark-hive", "trontastic",
			"hot-sneaks", "start", "dot-luv" };

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public List<String> getListThemes() {
		
		return Arrays.asList(themes);
	}
	
	public String trocar() {
		System.out.println("escolhido: " + this.theme);
		return "";
		
	}
	

}