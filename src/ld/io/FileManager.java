package ld.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class FileManager
{
	public static File loadFile(String path)
	{
		return new File(FileManager.class.getClassLoader().getResource(path).getFile());
	}
	
	public static String loadFileAsString(String path)
	{	
		try
		{
			File file = new File(FileManager.class.getClassLoader().getResource(path).getFile());
			
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			
			String line;
			String fileText = "";
			
			while((line = reader.readLine()) != null)
			{
				fileText += line;
			}
			
			fileReader.close();
			reader.close();
			return fileText;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Texture loadFileAsTexture(String path)
	{
		String extension = path.substring(path.length() - 3);
		
		try
		{
			return TextureLoader.getTexture(extension, FileManager.class.getClassLoader().getResourceAsStream(path));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
