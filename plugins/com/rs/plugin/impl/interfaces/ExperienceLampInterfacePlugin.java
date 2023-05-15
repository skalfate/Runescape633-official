package com.rs.plugin.impl.interfaces;

import com.rs.game.player.Player;
import com.rs.plugin.listener.RSInterface;
import com.rs.plugin.wrapper.RSInterfaceSignature;

import lombok.Data;
import lombok.Setter;

@Data
@RSInterfaceSignature(interfaceId = { 134 })
public class ExperienceLampInterfacePlugin implements RSInterface {
	
	private int skill;
	private double experience;
	
	@Setter
	public static int lamp;

	private static final int[] SKILL_VALUES = {
			-1, 0, 2, 1, 4, 5, 3, 6, 16, 15, 17, 12, 9, 14, 13, 10, 11, 7, 8, 20, 18, 21, 19, 22, 23, 24 };
	
	@Override
	public void execute(Player player, int interfaceId, int componentId, int packetId, byte slotId, int slotId2)
	    throws Exception {
	    if (componentId == 2) {
	        if (skill >= 0 && skill <= 24) {
	        	player.getInterfaceManager().closeInterfaces();
	            player.getPackets().sendGameMessage("You gained some experience.");
	            player.getInventory().deleteItem(lamp, 1);
	            player.getSkills().addXpNormal(getSkill(), getExperience(player));
	            setSkill(-1);
	        }
	    } else {
	        int skillIndex = componentId - 29;
	        if (skillIndex >= 0 && skillIndex < SKILL_VALUES.length) 
	            setSkill(SKILL_VALUES[skillIndex]);
	    }
	}
	
	public double getExperience(Player player) {
		setExperience((lamp == 19775 ? 215.0 : 126.0) + (player.getSkills().getLevel(getSkill()) * 3));
		return getExperience();
	}
}