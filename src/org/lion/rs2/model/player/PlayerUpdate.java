package org.lion.rs2.model.player;

import java.util.Iterator;

import org.lion.rs2.model.EntityList;
import org.lion.rs2.model.EntityUpdate;
import org.lion.rs2.model.UpdateFlags;
import org.lion.rs2.model.UpdateFlags.UpdateFlag;
import org.lion.rs2.net.packet.Packet.Size;
import org.lion.rs2.net.packet.PacketBuilder;
import org.lion.rs2.net.util.Utils;

/**
 * A class that handles player updating.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class PlayerUpdate extends EntityUpdate {

	/**
	 * The player update list.
	 */
	private static final EntityList<Player> updateList = new EntityList<Player>(2000);

	@Override
	public void process() {
		for (Iterator<Player> it = updateList.iterator(); it.hasNext();) {
			PacketBuilder builder = new PacketBuilder(81, Size.VARIABLE_SHORT);
			final Player player = it.next();

			if (player.getMapRegionChanged()) {
				player.getActionSender().sendMapRegion();
			}

			builder.startBitAccess();

			updateMovement(player, builder);
			//updatePlayer(player, builder);

			builder.addBits(8, 0);//Number of other players
			builder.addBits(11, 2047);//ID to indicate block follows
			builder.finishBitAccess();
			builder.addByte(0);//Empty mask
			player.getChannel().write(builder.build());
		}
	}

	/**
	 * Gets the player update list.
	 * 
	 * @return The player update list.
	 */
	public static EntityList<Player> getUpdateList() {
		return updateList;
	}

	/**
	 * Handles all the updating of a player.
	 * 
	 * @param player The player to update.
	 * @param builder The packet builder to use.
	 */
	@SuppressWarnings("unused")
	private void updatePlayer(final Player player, final PacketBuilder builder) {
		if (!player.getUpdateFlags().isUpdateRequired()) {
			return;
		}

		synchronized(player) {
			UpdateFlags flags = player.getUpdateFlags();
			int mask = 0;

			if (flags.get(UpdateFlag.GRAPHICS)) {
				mask |= 0x100;
			}
			if(flags.get(UpdateFlag.ANIMATION)) {
				mask |= 0x8;
			}
			if(flags.get(UpdateFlag.FORCED_CHAT)) {
				mask |= 0x4;
			}
			if(flags.get(UpdateFlag.CHAT)) {
				mask |= 0x80;
			}
			if(flags.get(UpdateFlag.FACE_ENTITY)) {
				mask |= 0x1;
			}
			if (flags.get(UpdateFlag.APPEARANCE)) {
				mask |= 0x10;
			}
			if(flags.get(UpdateFlag.FACE_COORDINATE)) {
				mask |= 0x2;
			}
			if(flags.get(UpdateFlag.HIT)) {
				mask |= 0x20;
			}
			if(flags.get(UpdateFlag.HIT_2)) {
				mask |= 0x200;
			}

			if (mask >= 0x100) {
				mask |= 0x40;
				builder.addByte(mask & 0xFF);
				builder.addByte(mask >> 8);
			} else {
				builder.addByte(mask);
			}

			if(flags.get(UpdateFlag.GRAPHICS)) {
				//TODO Graphics updating
			}
			if(flags.get(UpdateFlag.ANIMATION)) {
				//TODO Animation updating
			}
			if(flags.get(UpdateFlag.FORCED_CHAT)) {
				//TODO Forced chat updating
			}
			if(flags.get(UpdateFlag.CHAT)) {
				//TODO Chat updating
			}
			if(flags.get(UpdateFlag.FACE_ENTITY)) {
				//TODO Face entity updating
			}
			if (flags.get(UpdateFlag.APPEARANCE)) {
				updateAppearance(player, builder);
			}
			if(flags.get(UpdateFlag.FACE_COORDINATE)) {
				//TODO Face coordinate updating
			}
			if(flags.get(UpdateFlag.HIT)) {
				//TODO Hit 1 updating
			}
			if(flags.get(UpdateFlag.HIT_2)) {
				//TODO Hit 2 updating
			}
		}
	}

	/**
	 * Updates a player's movement.
	 * 
	 * @param player The player to update.
	 * @param builder The packet builder to use.
	 */
	private void updateMovement(final Player player, final PacketBuilder builder) {
		builder.addBits(1, 1);//Needs update
		builder.addBits(2, 3);//Teleported
		builder.addBits(2, player.getPosition().getZ());//Height level
		builder.addBits(1, 1);//Walking queue
		builder.addBits(1, 1);//Update block
		builder.addBits(7, player.getPosition().getLocalY(player.getPosition()));//The local y coordinate
		builder.addBits(7, player.getPosition().getLocalY(player.getPosition()));//The loacal x coordinate
	}

	/**
	 * Updates a player's appearance.
	 * 
	 * @param player The player to update.
	 * @param builder The packet builder to use.
	 */
	private void updateAppearance(final Player player, final PacketBuilder builder) {
		PacketBuilder block = new PacketBuilder();
		Appearance appearance = player.getAppearance();

		block.addByte(appearance.getGender());//Player gender
		block.addByte(0);//Head icon
		for (int i = 0; i < 4; i++)
			block.addByte(0);
		block.addShort(0x100 + appearance.getChest());//Chest
		block.addByte(0);//Shield
		block.addShort(0x100 + appearance.getArms());//Arms
		block.addShort(0x100 + appearance.getLegs());//Legs
		block.addShort(0x100 + appearance.getHead());//Head
		block.addShort(0x100 + appearance.getHands());//Hands
		block.addShort(0x100 + appearance.getFeet());//Feet
		block.addShort(0x100 + appearance.getBeard());//Beard
		block.addByte(appearance.getHairColor());//Hair color
		block.addByte(appearance.getChestColor());//Torso color
		block.addByte(appearance.getLegColor());//Legs color
		block.addByte(appearance.getFeetColor());//Feet color
		block.addByte(appearance.getSkinColor());//Skin color
		block.addShort(0x328);//Stand animation
		block.addShort(0x337);//Stand turn animation
		block.addShort(0x333);//Walk animation
		block.addShort(0x334);//Turn 180 animation
		block.addShort(0x335);//Turn 90 clockwise animation
		block.addShort(0x336);//Turn 90 counter-clockwise animation
		block.addShort(0x338);//Run animation
		block.addLong(Utils.NameUtils.nameToLong(player.getUsername()));//Player's name to long
		block.addByte(player.getSkills().getCombatLevel());//Player's combat level
		block.addShort(player.getSkills().getTotalLevel());//Player's total level

		builder.addBuffer(block.build().getBuffer());
	}

}