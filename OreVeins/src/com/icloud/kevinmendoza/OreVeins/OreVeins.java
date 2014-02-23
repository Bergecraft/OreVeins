package com.icloud.kevinmendoza.OreVeins;
import geometryClasses.ThreePoint;
import geometryClasses.TwoPoint;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import mcListenersAndPopulators.EventListeners;
import oreClasses.MetamorphicSystem;
import oreClasses.SedimentHostedDepositSystem;
import oreClasses.VeinSystem;
import oreClasses.VolcanicSystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import defaultPackadge.Defaults;


public final class OreVeins extends JavaPlugin 
{
	File config;
	FileConfiguration newConfigs;
	@Override
	public void onEnable()
	{
		// TODO Insert logic to be performed when the plugin is enabled
		getLogger().info("onEnable has been invoked!");
		popFileTree();
		getServer().getPluginManager().registerEvents(new EventListeners(), this);
		PointMapping.populatePopList();
		newConfigs = YamlConfiguration.loadConfiguration(config);
		Defaults.popAndReadDefaults(newConfigs);
		saveNewConfig();
	}

	@Override
	public void onDisable() 
	{
		// TODO Insert logic to be performed when the plugin is disabled
		getLogger().info("onDisable has been invoked!");
		PointMapping.savePoints();
		PointMapping.savePopulatedList();
	}
	
	private void popFileTree()
	{
		File Ovein = new File("plugins/OreVeins");
		config = new File("plugins/OreVeins/config.yml");
		File popList = new File("plugins/OreVeins/popList.txt");
		File ChunkInfo = new File("plugins/OreVeins/ChunkInfo");
		try
		{
			Ovein.mkdir();
			popList.createNewFile();
			config.createNewFile();
			ChunkInfo.mkdir();
		}
		catch (IOException e)
		{ //Hooray for horrible programming practices!
			DebugLogger.console("Whoopsie! File creation failed!");
			e.printStackTrace();
			onDisable();
			//Also, fuck you checked exceptions!
		}		
	}
	
	public void saveNewConfig()
	{
		try
		{
			newConfigs.save(config);
		}
		catch(Exception e)
		{
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("testChunk"))
		{ // If the player typed /basic then do the following...
			Player thePlayer = (Player)sender;
			TwoPoint chunk = new 
					TwoPoint(thePlayer.getLocation().getBlockX(),thePlayer.getLocation().getBlockZ(),false);
			String thechunk = chunk.toChunkCoord();
			if(PointMapping.getPop(thechunk)!=null)
			{
				sender.sendMessage("chunkisPopulated");
			}
			else
			{
				sender.sendMessage("chunkisNotPopulated");
			}
			if(PointMapping.getLoaded(thechunk)!=null)
			{
				sender.sendMessage("chunkisLoaded");
			}
			else
			{
				sender.sendMessage("chunkisNotLoaded");
			}
			if(PointMapping.getPoints(thechunk)!=null)
			{
				sender.sendMessage("chunkHasPoints");
			}
			else
			{
				sender.sendMessage("chunkHasNoPoints");
			}
			return true;
		}
		/*
		else if(cmd.getName().equalsIgnoreCase("spawnGold"))
		{ // If the player typed /basic then do the following...
			DebugLogger.console("spawningGold");
			Player thePlayer = (Player)sender;
			int x =thePlayer.getLocation().getBlockX();
			int y = thePlayer.getLocation().getBlockY();
			int z = thePlayer.getLocation().getBlockZ(); int branchType=1;
			ThreePoint start = new ThreePoint(x,y,z);
			Random rand = new Random();
			double value = Defaults.iron.chooseType.getRVar(rand);
			if(value <1)
			{
				branchType = 1;
			}
			else if(value <2)
			{
				value = 2;
			}
			else
			{
				value = 3;
			}
			VeinSystem sed = new VeinSystem("EMERALD",branchType, start, 0);
			return true;//If this has happened the function will return true. 
			// If this hasn't happened the a value of false will be returned.
		}/*
		else if(cmd.getName().equalsIgnoreCase("spawnEmerald"))
		{
			DebugLogger.console("spawningEmerald");
			Player thePlayer = (Player)sender;
			int x =thePlayer.getLocation().getBlockX();
			int y = thePlayer.getLocation().getBlockY();
			int z = thePlayer.getLocation().getBlockZ();
			ThreePoint start = new ThreePoint(x,y,z);
			PegmatiteSystem peg = new PegmatiteSystem(start, Defaults.emerald.strike, Defaults.emerald.branch, 
					Defaults.emerald.bonanza, Defaults.emerald.grade ,"EMERALD");
			return true;
		}
		else
		{
			return false;
		}
		commands:
   testChunk:
      description: Shows you OreVein Mapping info
      usage: /<testChunk>
      permission: <OreVeins>.basic
      permission-message: You don't have <permission>
   spawnGold:
      description: spawns A New Veinlet
      usage: /<spawnGold>
      permission: <OreVeins>.spawnGold
      permission-message: You don't have <permission>
   spawnEmerald:
      description: spawns A New Veinlet
      usage: /<spawnEmerald>
      permission: <OreVeins>.spawnEmerald
      permission-message: You don't have <permission>
	}*/
		else
		{
			return false;
		}
	}
}
