package ld.graphics;

import ld.io.FileManager;

import org.newdawn.slick.opengl.Texture;

public class Textures
{
	// TILES //
	public static Texture tile_floor_black;
	public static Texture tile_floor_rainbow;
	public static Texture tile_wall_test;
	
	// ENTITIES //
	public static Texture player_1;
	public static Texture player_2;
	public static Texture player_3;
	public static Texture player_4;
	public static Texture player_5;
	public static Texture commoner_black;
	
	// PROJECTILES //
	public static Texture projectile_player_bullet;
	
	// GUIS //
	public static Texture main_menu;
	public static Texture about_menu;
	public static Texture settings_menu;
	public static Texture explanatory_screen;
	public static Texture lose_screen;
	public static Texture win_screen;
	
	public static Texture rgbHUD;
	
	public static Texture selector;
	
	public static void loadTextures()
	{
		// TILES //
		tile_floor_black = FileManager.loadFileAsTexture("tiles/tile_floor_black.png");
		tile_floor_rainbow = FileManager.loadFileAsTexture("tiles/tile_floor_rainbow.png");
		
		tile_wall_test = FileManager.loadFileAsTexture("tiles/tile_wall_black.png");

		// ENTITIES //
		player_1 = FileManager.loadFileAsTexture("entities/player_1.png");
		player_2 = FileManager.loadFileAsTexture("entities/player_2.png");
		player_3 = FileManager.loadFileAsTexture("entities/player_3.png");
		player_4 = FileManager.loadFileAsTexture("entities/player_4.png");
		player_5 = FileManager.loadFileAsTexture("entities/player_5.png");
		
		commoner_black = FileManager.loadFileAsTexture("entities/commoner_black.png");
		
		// PROJECTILES //
		projectile_player_bullet = FileManager.loadFileAsTexture("entities/projectile_player_bullet.png");
		
		// GUIS //
		main_menu = FileManager.loadFileAsTexture("menu/main_menu.png");
		about_menu = FileManager.loadFileAsTexture("menu/about_menu.png");
		settings_menu = FileManager.loadFileAsTexture("menu/settings_menu.png");
		explanatory_screen = FileManager.loadFileAsTexture("menu/explanatory_screen.png");
		lose_screen = FileManager.loadFileAsTexture("menu/lose_screen.png");
		win_screen = FileManager.loadFileAsTexture("menu/win_screen.png");

		rgbHUD = FileManager.loadFileAsTexture("menu/rgbHUD.png");
		
		selector = FileManager.loadFileAsTexture("menu/picker.png");
	}
}
