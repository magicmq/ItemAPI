package dev.magicmq.itemapi.metadata;

import dev.magicmq.itemapi.WrappedItem;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import dev.magicmq.itemapi.utils.Colors;
import dev.magicmq.itemapi.utils.exception.MapNotFoundException;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import java.io.File;
import java.io.Serializable;

/**
 * Wrapper class that contians all Metadata associated with Maps.
 * <b>Note:</b> This class does not support the new {@link org.bukkit.map.MapView MapView} system yet. That is a planned feature.
 */
public class MapMetadata extends Metadata implements Serializable {

    private static final long serialVersionUID = 1402169364005444321L;

    private MapMetaType type;

    private String color;
    private boolean scaling;
    private int mapId;

    /*private int centerX;
    private int centerZ;
    private String scale;
    private boolean unlimitedTracking;
    private String world;

    private int drawStartX;
    private int drawStartY;
    private String text;
    private String image;*/

    /**
     * Create a new MapMetadata class from scratch with default values.
     */
    public MapMetadata() {
        super();

        this.color = null;
        this.scaling = false;
        this.mapId = -1;

        /*this.centerX = -1;
        this.centerZ = -1;
        this.scale = null;
        this.unlimitedTracking = false;
        this.world = null;

        this.drawStartX = 0;
        this.drawStartY = 0;
        this.text = null;
        this.image = null;*/
    }

    /**
     * Create a new MapMetadata class with values parsed from a configuration section.
     * <b>Note:</b> This receives the configuration section of the item, not the "map-data" section.
     * @param section The section from which the data will be parsed
     */
    public MapMetadata(WrappedConfigurationSection section) {
        super(section);

        WrappedConfigurationSection mapSection = section.getConfigurationSection("map-data");
        this.type = MapMetaType.LEGACY;
        if (this.type == MapMetaType.LEGACY) {
            this.color = mapSection.getString("color");
            this.scaling = mapSection.getBoolean("scaling", false);
            this.mapId = mapSection.getInt("id", -1);
        } /*else if (this.type == MapMetaType.CURRENT) {
            this.centerX = mapSection.getInt("center-x", -1);
            this.centerZ = mapSection.getInt("center-z", -1);
            this.scale = mapSection.getString("scale");
            this.unlimitedTracking = mapSection.getBoolean("unlimited-tracking", false);
            this.world = mapSection.getString("world");
            this.drawStartX = mapSection.getInt("draw-start-x", 0);
            this.drawStartY = mapSection.getInt("draw-start-y", 0);
            this.text = mapSection.getString("text");
            this.image = mapSection.getString("image");
        }*/
    }

    /**
     * Create a new MapMetadata class with values parsed from an existing ItemStack.
     * @param item The item from which map metadata will be extracted
     */
    public MapMetadata(ItemStack item) {
        super(item);

        MapMeta meta = (MapMeta) item.getItemMeta();
        if (meta != null) {
            if (meta.getColor() != null)
                this.color = meta.getColor().getRed() + "," + meta.getColor().getGreen() + "," + meta.getColor().getBlue();
            else
                this.color = null;
            this.scaling = meta.isScaling();
            this.mapId = meta.getMapId();
        } else {
            this.color = null;
            this.scaling = false;
            this.mapId = -1;
        }
    }

    /**
     * Get the color of the map.
     * @return The color of the map, either as an enum of {@link Colors} or as a comma-separated RGB string
     */
    public String getColor() {
        return color;
    }

    /**
     * Set the color of the map.
     * @param color The color, either as an enum of {@link Colors} or as a comma-separated RGB string
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Get if the map has scaling.
     * @return True if the map has scaling, false otherwise
     */
    public boolean isScaling() {
        return scaling;
    }

    /**
     * Set if the map has scaling.
     * @param scaling True to scale the map, false otherwise
     */
    public void setScaling(boolean scaling) {
        this.scaling = scaling;
    }

    /**
     * Get the map ID of this map metadata.
     * @return The map ID associated with this metadata
     */
    public int getMapId() {
        return mapId;
    }

    /**
     * Set the ID of this map metadata.
     * @param mapId The ID to set
     */
    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    /**
     * Apply the map metadata as well as basic metadata in {@link Metadata Metadata} to an ItemStack.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#getAsItemStack() getAsItemStack} in the WrappedItem class!
     * @param item The item to which the metadata will be applied
     * @return The item, with metadata applied to it
     * @throws MapNotFoundException If the map ID was not found
     */
    @Override
    public ItemStack applyMetadata(ItemStack item) {
        item = super.applyMetadata(item);

        MapMeta meta = (MapMeta) item.getItemMeta();
        if (type == MapMetaType.LEGACY) {
            if (color != null) {
                try {
                    Colors color = Colors.valueOf(this.color);
                    meta.setColor(color.getBukkitColor());
                } catch (IllegalArgumentException ignored) {
                    String[] split = color.split(",");
                    meta.setColor(Color.fromRGB(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
                }
            }
            meta.setScaling(scaling);
            if (mapId > -1) {
                MapView view = Bukkit.getMap(mapId);
                if (view != null)
                    meta.setMapView(view);
                else
                    throw new MapNotFoundException("Map ID " + mapId + " was not found! Please make sure this is a valid map ID.");
            }
        } /*else if (type == MapMetaType.CURRENT) {
            meta.setMapId(0);

            MapView view = meta.getMapView();
            if (centerX > -1)
                view.setCenterX(centerX);
            if (centerZ > -1)
                view.setCenterZ(centerZ);
            if (scale != null)
                view.setScale(MapView.Scale.valueOf(scale));
            view.setUnlimitedTracking(unlimitedTracking);
            if (world != null)
                view.setWorld(Bukkit.getWorld(world));
            if (text != null) {
                view.getRenderers().forEach(view::removeRenderer);
                view.addRenderer(new MapRenderer() {
                    @Override
                    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
                        mapCanvas.drawText(drawStartX, drawStartY, MinecraftFont.Font, text);
                    }
                });
            }
            if (image != null) {
                view.getRenderers().forEach(view::removeRenderer);
                view.addRenderer(new MapRenderer() {
                    @Override
                    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
                        try {
                            BufferedImage image = ImageIO.read(new URL(MapMetadata.this.image));
                            mapCanvas.drawImage(drawStartX, drawStartY, image);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            meta.setMapView(view);
        }*/

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Serialize all the map metadata contained within this class as well as basic metadata in {@link Metadata Metadata} to a configuration section.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#saveToConfig(File, WrappedConfigurationSection) saveToConfig} in the WrappedItem class!
     * @param section The configuration section to which the data will be serialized
     */
    @Override
    public void saveToConfig(WrappedConfigurationSection section) {
        super.saveToConfig(section);

        WrappedConfigurationSection mapSection = section.createConfigurationSection("map-data");
        if (this.color != null)
            mapSection.set("color", this.color);
        if (this.scaling)
            mapSection.set("scaling", true);
        if (this.mapId > -1)
            mapSection.set("id", this.mapId);
    }

    /**
     * Currently unused class but will be implemented in the future.
     */
    enum MapMetaType {

        LEGACY,
        CURRENT

    }
}
