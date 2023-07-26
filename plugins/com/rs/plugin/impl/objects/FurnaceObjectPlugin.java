package com.rs.plugin.impl.objects;

import com.rs.constants.ItemNames;
import com.rs.game.item.Item;
import com.rs.game.map.GameObject;
import com.rs.game.player.Player;
import com.rs.plugin.listener.ObjectListener;
import com.rs.plugin.wrapper.ObjectSignature;

import skills.crafting.MoltenGlassCrafting;

@ObjectSignature(objectId = {45310}, name = {"Furnace"})
public class FurnaceObjectPlugin extends ObjectListener {

	@Override
	public void executeItemOnObject(Player player, GameObject object, Item item) throws Exception {
		if (item.getId() == ItemNames.BUCKET_OF_SAND_1783 || item.getId() == ItemNames.SODA_ASH_1781) {
			new MoltenGlassCrafting(player).start();
		}
	}
}