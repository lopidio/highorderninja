package br.com.guigasgame.scenery;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import com.google.gson.Gson;

public class SceneryJSonParser
{

	public static void main(String[] args) throws Exception 
	{
		// Configure Gson

		File file = new File("sceneryTest.json");
		List<String> jsonList = Files.readAllLines(file.toPath());

		String jsonExample = new String();
		for (String string : jsonList) 
		{
			jsonExample += string;
		}

		// System.out.println(jsonExample);

		Gson gsonSer = new Gson();
		SceneryFileJson joc = gsonSer.fromJson(jsonExample, SceneryFileJson.class);

		List<RigidBody> rigidBodiesList = joc.getRigidBodies();
		for (RigidBody rigidBody : rigidBodiesList) 
		{
			List<List<Polygon>> polygons = rigidBody.getPolygons();
			for (List<Polygon> polygon : polygons) 
			{
				for (Polygon polygon2 : polygon) 
				{
					System.out.println(polygon2.getX() + ", " + polygon2.getY());
				}
			}
		}

		// GsonBuilder gsonBuilder = new GsonBuilder();
		// // gsonBuilder.registerTypeAdapter(Book.class, new
		// BookDeserializer());
		// Gson gson = gsonBuilder.create();
		//
		// InputStream stream = new
		// ByteArrayInputStream(jsonExample.getBytes(StandardCharsets.UTF_8));
		//
		// // The JSON data
		// try(Reader reader = new InputStreamReader(stream))
		// {
		// // Parse JSON to Java
		// SceneryFileJson book = gson.fromJson(reader, SceneryFileJson.class);
		// System.out.println(book);
		// }
	}
}