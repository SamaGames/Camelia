package eu.carrade.amaury.Camelia.drawing.whiteboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.drawing.colors.colors.ColorWhite;
import eu.carrade.amaury.Camelia.drawing.colors.core.ColorType;
import eu.carrade.amaury.Camelia.drawing.colors.core.ColorUtils;
import eu.carrade.amaury.Camelia.drawing.colors.core.PixelColor;
import eu.carrade.amaury.Camelia.utils.Utils;


public class Whiteboard {

	/**
	 * An angle under the whiteboard, with the smallest two coordinates (x and z).
	 */
	private Location bottomAngle = null;

	/**
	 * An angle above the whiteboard, opposed to the first one, so with the biggest
	 * two coordinates (x and z).
	 */
	private Location topAngle = null;
	
	private final int width;
	private final int height;
	
	private final PixelColor[][] board;

	private final WhiteboardOrientation orientation;
	
	private Random random = new Random();

	/**
	 * These blocs of the screen are on cooldown: they cannot be changed when in
	 * this list, to avoid fast color mixes when drawing.
	 */
	private final Set<WhiteboardLocation> onCooldownLocations = new CopyOnWriteArraySet<>();

	public Whiteboard() {
		try {

			Location configCorner1 = Utils.stringToLocation(Camelia.getInstance().getArenaConfig().getString("whiteboard.corner1"));
			Location configCorner2 = Utils.stringToLocation(Camelia.getInstance().getArenaConfig().getString("whiteboard.corner2"));

			bottomAngle = new Location(configCorner1.getWorld(),
					Math.min(configCorner1.getBlockX(), configCorner2.getBlockX()),
					Math.min(configCorner1.getBlockY(), configCorner2.getBlockY()),
					Math.min(configCorner1.getBlockZ(), configCorner2.getBlockZ()));

			topAngle = new Location(configCorner1.getWorld(),
					Math.max(configCorner1.getBlockX(), configCorner2.getBlockX()),
					Math.max(configCorner1.getBlockY(), configCorner2.getBlockY()),
					Math.max(configCorner1.getBlockZ(), configCorner2.getBlockZ()));


		} catch (IllegalArgumentException e) {
			Camelia.getInstance().getLogger().log(Level.SEVERE, "Invalid whiteboard configuration!!!", e);
			Camelia.getInstance().getLogger().log(Level.SEVERE, "Disabling the plugin.");
			Camelia.getInstance().disable();
		}


		if(bottomAngle != null && topAngle != null) {

			orientation = bottomAngle.getBlockX() == topAngle.getBlockX() ? WhiteboardOrientation.FOLLOWING_X_AXIS : WhiteboardOrientation.FOLLOWING_Z_AXIS;

			width = orientation == WhiteboardOrientation.FOLLOWING_X_AXIS ? topAngle.getBlockZ() - bottomAngle.getBlockZ() + 1 : topAngle.getBlockX() - bottomAngle.getBlockX() + 1;
			height = topAngle.getBlockY() - bottomAngle.getBlockY() + 1;

			board = new PixelColor[width][height];

			clearBoard(); // To remove all null values
		}
		else {
			orientation = null;
			width = height = 0;
			board = null;
		}
	}


	/**
	 * Returns true if this location is on the whiteboard
	 *
	 * @param location The location
	 *
	 * @return True if on the whiteboard.
	 */
	public boolean isOnTheWhiteboard(Location location) {

		return location != null
				&& location.getWorld().equals(getWhiteboardWorld())
				&& location.getBlockX() >= bottomAngle.getBlockX() && location.getBlockY() >= bottomAngle.getY() && location.getBlockZ() >= bottomAngle.getBlockZ()
				&& location.getBlockX() <= topAngle.getBlockX() && location.getBlockY() <= topAngle.getBlockY() && location.getBlockZ() <= topAngle.getBlockZ();

	}

	/**
	 * Returns true if this location is on the whiteboard
	 *
	 * @param location The location
	 *
	 * @return True if on the whiteboard.
	 */
	public boolean isOnTheWhiteboard(WhiteboardLocation location) {

		return location != null
				&& location.getWhiteboard().equals(this)
				&& location.getX() >= 0 && location.getY() >= 0
				&& location.getX() < width && location.getY() < height;

	}


	/**
	 * Sets a block of the whiteboard
	 *
	 * @param location The location
	 * @param color The block to set.
	 *
	 * @return True if the block was set (i.e. the location is in the whiteboard and not
	 * on a cooldown).
	 */
	public boolean setBlock(final Location location, final PixelColor color) {
		return setBlock(WhiteboardLocation.fromBukkitLocation(location), color, false);
	}

	/**
	 * Sets a block of the whiteboard
	 *
	 * @param location The location
	 * @param color The block to set.
	 * @param mix If true, mix this color with the old one.
	 *
	 * @return True if the block was set (i.e. the location is in the whiteboard and not
	 * on a cooldown).
	 */
	public boolean setBlock(final Location location, final PixelColor color, boolean mix) {
		return setBlock(WhiteboardLocation.fromBukkitLocation(location), color, mix);
	}

	/**
	 * Sets a block of the whiteboard
	 *
	 * @param location The location
	 * @param color The block to set.
	 *
	 * @return True if the block was set (i.e. the location is in the whiteboard and not
	 * on a cooldown).
	 */
	public boolean setBlock(final WhiteboardLocation location, final PixelColor color) {
		return setBlock(location, color, true);
	}

	public boolean setBlock(final WhiteboardLocation location, final PixelColor color, boolean mix) {
		return setBlock(location, color, mix, 10);
	}
	
	/**
	 * Sets a block of the whiteboard
	 *
	 * @param location The location
	 * @param color The block to set.
	 * @param mix If true, mix this color with the old one.
	 *
	 * @return True if the block was set (i.e. the location is in the whiteboard and not
	 * on a cooldown).
	 */
	public boolean setBlock(final WhiteboardLocation location, final PixelColor color, boolean mix, long cooldown) {
		if(!isOnTheWhiteboard(location)) {
			return false;
		}

		if(onCooldownLocations.contains(location) && mix) {
			return false;
		}

		PixelColor baseColor = board[location.getX()][location.getY()];
		PixelColor finalColor = mix ? ColorUtils.getMix(color, baseColor) : color;
		
		board[location.getX()][location.getY()] = finalColor;

		Block block = location.toBukkitLocation().getBlock();
		block.setType(finalColor.getBlock().getType());
		block.setData(finalColor.getBlock().getData());


		onCooldownLocations.add(location);

		Bukkit.getScheduler().runTaskLaterAsynchronously(Camelia.getInstance(), new Runnable() {
			@Override
			public void run() {
				onCooldownLocations.remove(location);
			}
		}, cooldown);

		return true;
	}
	
	public void drawCircle(final WhiteboardLocation center, int size, final PixelColor color, boolean mix) {
		for(int x = -size; x <= size; x++) {
			for(int y = -size; y <= size; y++) {
				if(x * x + y * y <= size * size / 4) {
					setBlock(new WhiteboardLocation(center.getX() + x, center.getY() + y), color, mix, 3 * size + 5);
				}
			}
		}
	}
	
	public void fillRandomly(final WhiteboardLocation center, int size, double probability, final PixelColor color, boolean mix) {
		for(int x = -size; x <= size; x++) {
			for(int y = -size; y <= size; y++) {
				if(x * x + y * y <= size * size / 4 && random.nextDouble() <= probability) {
					setBlock(new WhiteboardLocation(center.getX() + x, center.getY() + y), color, mix, 3 * size + 5);
				}
			}
		}
	}
	
	public void fillArea(final WhiteboardLocation begin, final PixelColor color) {
		if(!isOnTheWhiteboard(begin)) {
			return;
		}
		
		List<WhiteboardLocation> nexts = new ArrayList<WhiteboardLocation>();
		
		PixelColor toReplace = board[begin.getX()][begin.getY()];
		
		nexts.add(begin);
		
		setBlock(begin, color, false);

		while(nexts.size() != 0) {
			List<WhiteboardLocation> newLocations = new ArrayList<WhiteboardLocation>();
			
			for(WhiteboardLocation loc : nexts) {
				proceedLocation(loc, newLocations, toReplace, color);
			}
			
			nexts = newLocations;
		}
	}
	
	private void proceedLocation(WhiteboardLocation location, List<WhiteboardLocation> newLocations, PixelColor toReplace, PixelColor color) {
		List<WhiteboardLocation> locs = Arrays.asList(new WhiteboardLocation(this, location.getX() + 1, location.getY()), new WhiteboardLocation(this, location.getX(), location.getY() - 1), new WhiteboardLocation(this, location.getX() - 1, location.getY()), new WhiteboardLocation(this, location.getX(), location.getY() + 1));
		for(WhiteboardLocation p : locs) {
			if(isOnTheWhiteboard(p) && board[p.getX()][p.getY()].getDyeColor().equals(toReplace.getDyeColor()) && board[p.getX()][p.getY()].getType().equals(toReplace.getType()) && !(board[p.getX()][p.getY()].getDyeColor().equals(color.getDyeColor()) && board[p.getX()][p.getY()].getType().equals(color.getType()))) {
				setBlock(p, color, false);
				newLocations.add(p);
			}
		}
		
	}

	/**
	 * Returns the block of the whiteboard targeted by the given player.
	 *
	 * @param player The player.
	 *
	 * @return The block. {@code null} if the target is out of the screen, or too far (200 blocks).
	 */
	public Location getTargetBlock(Player player) {
		Block targetBlock = Utils.getTargetBlock(player, 200);

		if(targetBlock == null) return null;

		Location target = targetBlock.getLocation();

		if(isOnTheWhiteboard(target)) {
			return target;
		}

		return null;
	}


	public void clearBoard() {
		PixelColor color = new ColorWhite(ColorType.BASIC);

		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				setBlock(new WhiteboardLocation(this, x, y), color, false);
			}
		}
	}



	public World getWhiteboardWorld() {
		return bottomAngle.getWorld();
	}

	public Location getBottomAngle() {
		return bottomAngle;
	}

	public Location getTopAngle() {
		return topAngle;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public WhiteboardOrientation getOrientation() {
		return orientation;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Whiteboard)) return false;

		Whiteboard that = (Whiteboard) o;

		return !(bottomAngle != null ? !bottomAngle.equals(that.bottomAngle) : that.bottomAngle != null) && !(topAngle != null ? !topAngle.equals(that.topAngle) : that.topAngle != null);

	}

	@Override
	public int hashCode() {
		int result = bottomAngle != null ? bottomAngle.hashCode() : 0;
		result = 31 * result + (topAngle != null ? topAngle.hashCode() : 0);
		return result;
	}
}
