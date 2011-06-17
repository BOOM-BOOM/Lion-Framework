package org.lion.rs2.net.packet.impl;

import org.lion.rs2.model.player.Player;
import org.lion.rs2.net.packet.Packet;
import org.lion.rs2.net.packet.PacketHandler;
import org.lion.rs2.util.Logger;
import org.lion.rs2.util.Logger.Level;

/**
 * Handles actions clicked in the client.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class ActionButtonPacket extends PacketHandler {

	/**
	 * Initializes a new Logger.
	 */
	private static final Logger logger = new Logger("ActionButtonPacket");

	@Override
	public void handle(Packet packet, Player player) {
		final int button = packet.readShort();//8654-8672 12162 13928 Skill information interface
		switch (button) {
		case 150://Auto retaliate ON
		case 151://Auto retaliate OFF
		case 152://Walk
		case 153://Run
		case 161://Cry emote
		case 162://Think emote
		case 163://Wave emote
		case 164://Bow emote
		case 165://Angry emote
		case 166://Dance emote
		case 167://Beckon emote
		case 168://Yes emote
		case 169://No emote
		case 170://Laugh emote
		case 171://Cheer emote
		case 172://Clap emote
		case 666://Glass wall emote
		case 667://Glass box emote
		case 683://Retribution
		case 684://Redemption
		case 685://Smite
		case 952://Split Private-chat ON
		case 953://Split Private-chat OFF
		case 1159://Bones to Bananas
		case 1164://Varrock Teleport
		case 1167://Lumbridge Teleport
		case 1170://Falador Teleport
		case 1174://Camelot Teleport
		case 1193://Charge MAGIC
		case 1540://Ardougne Teleport
		case 1541://Watchtower Teleport
		case 2429://Attack Style ACCURATE
		case 2430://Attack Style DEFENSIVE
		case 2431://Attack Style CONTROLLED
		case 2432://Attack Style AGGRESSIVE
			break;
		case 2458://Logout
			player.getActionSender().sendLogout();
			break;
		case 5386://Set to withdrawing notes
		case 5387://Set to not withdrawing notes
		case 5452://Screen Brightness DARK
		case 5609://Thick Skin
		case 5610://Burst of Strength
		case 5611://Clarity of Thought
		case 5612://Rock Skin
		case 5613://Superhuman Strength
		case 5614://Improved Reflexes
		case 5615://Rapid Restore
		case 5616://Rapid Heal
		case 5617://Protect Items
		case 5618://Steel Skin
		case 5619://Ultimate Strength
		case 5620://Incredible Reflexes
		case 5621://Protect from Magic
		case 5622://Protect from Missiles
		case 5623://Protect from Melee
		case 6157://Screen Brightness NORMAL
		case 6275://Screen Brightness BRIGHT
		case 6277://Screen Brightness V-BRIGHT
		case 6278://Mouse Buttons TWO
		case 6279://Mouse Buttons ONE
		case 6280://Chat Effects ON
		case 6281://Chat Effects OFF
		case 6503://Climb rope emote
		case 6506://Lean emote
		case 7455://Trollheim Telleport
		case 7537://Special bar click
		case 7562://Special bar click
		case 7587://Special bar click
		case 7687://Special bar click
		case 8130://Set to swapping
		case 8131://Set to not swapping
		case 11100://Blow kiss emote
		case 12311://Special bar click
		case 12590://Accept Aid ON
		case 12591://Accept Aid OFF
		case 13362://Panic emote
		case 13363://Jig emote
		case 13364://Spin emote
		case 13365://Headbang emote
		case 13366://Jump of joy emote
		case 13367://Raspberry emote
		case 13368://Yawn emote
		case 13383://Goblin bow emote
		case 13384://Goblin dance emote
		case 13369://Salute emote
		case 13370://Shrug emote
		case 15166://Scared emote
		case 15877://Bones to Peaches
		case 18464://Zombie walk emote
		case 18465://Zombie dance emote
		case 18470://Teleport to Ape Atoll
			break;
		default:
			logger.log(Level.NORMAL, "Unhandled button ID: " + button);
			break;
		}
	}

}
