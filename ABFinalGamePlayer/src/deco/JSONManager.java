package deco;

import java.lang.reflect.Type;
import java.util.concurrent.CopyOnWriteArrayList;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import models.Alien;
import models.Bullet;
import models.Player;

public class JSONManager {
	
	
	//============================================= DECODERS =============================================
	
	public double decodeDouble(String jsonNumber) {
		Gson gson = new Gson();
		double number = gson.fromJson(jsonNumber, double.class);
		return number;
	} 
	
	public Player decodePlayer(String playerAsJson) {
		Gson gson = new Gson();
		return gson.fromJson(playerAsJson, Player.class);
	}
	
	public CopyOnWriteArrayList<Alien> decodeAliensList(String aliensListAsJson) {
		CopyOnWriteArrayList<Alien> aliensList = new CopyOnWriteArrayList<>();
		Gson gson = new Gson();
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(aliensListAsJson);
		Type type = new TypeToken<CopyOnWriteArrayList<Alien>>(){}.getType();
		aliensList =  gson.fromJson(jsonElement, type);
		return aliensList;
	}
	
	public CopyOnWriteArrayList<Player> decodePlayersList(String playersListAsJson) {
		CopyOnWriteArrayList<Player> playersList = new CopyOnWriteArrayList<>();
		Gson gson = new Gson();
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(playersListAsJson);
		Type type = new TypeToken<CopyOnWriteArrayList<Player>>(){}.getType();
		playersList =  gson.fromJson(jsonElement, type);
		return playersList;
	}

	public CopyOnWriteArrayList<Bullet> decodeBulletsList(String bulletsListAsJson) {
		CopyOnWriteArrayList<Bullet> bulletsList = new CopyOnWriteArrayList<>();
		Gson gson = new Gson();
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(bulletsListAsJson);
		Type type = new TypeToken<CopyOnWriteArrayList<Bullet>>(){}.getType();
		bulletsList =  gson.fromJson(jsonElement, type);
		return bulletsList;
	}
	
	public String decodeNickAsJson(String nickAsJson) {
		Gson gson = new Gson();
		String nick = gson.fromJson(nickAsJson, String.class);
		return nick;
	}
	
	//============================================ ENCODERS =============================================
	
	public String encodeDoubleAsJson(double number) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(number);
	}
	
	public String encodeNick(String nick) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(nick);
	}
}